package com.csin.campervan.iot.one.task;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.csin.campervan.core.util.TimeUtil;
import com.csin.campervan.iot.core.ApplicationConfig;
import com.csin.campervan.iot.mybatis.MyBatisManager;
import com.csin.campervan.iot.one.BaseMessage;
import com.csin.campervan.iot.one.MessageMap;
import com.csin.campervan.iot.one.OneClient;
import com.csin.campervan.iot.one.OneMessage;
import com.csin.campervan.iot.one.mapper.*;
import com.csin.campervan.iot.one.message.*;
import com.csin.campervan.iot.one.model.EsUploadError;
import com.csin.campervan.iot.one.model.EsUploadProgress;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * 客户端发送数据
 *
 * @Author wuxh
 * @Date 2023/10/20 9:25
 * @description
 */
@Slf4j
public class ClientSendDataTask implements Runnable {

    OneClient client;
    private volatile boolean running = false;

    public ClientSendDataTask(OneClient client) {
        this.client = client;
    }

    public void doTask() {
        if (client == null) {
            return;
        }
        Channel channel = client.getChannel();
        if (!channel.isActive()) {
            return;
        }

        long start = System.currentTimeMillis();
        MyBatisManager manager = client.getMyBatisManager();
        log.info("===========开始处理异常推送=================");
        List<EsUploadError> errors = manager.mapper(EsUploadErrorMapper.class, m -> m.selectList(Wrappers.lambdaQuery(EsUploadError.class)));
        for (EsUploadError error : errors) {
            try {
                this.sendErrorData(error);
            } catch (Exception e) {
                log.error("发送异常数据失败！", e);
            }
        }
        log.info("===========处理异常推送接收=================");
        log.info("===========开始执行推送=================");
        //获取每个表增量数据

        this.processOne(EsCamperVanMapper.class, EsCamperVanMsg.class);

        {
            //附件处理
            String formatToday = TimeUtil.formatToday();
            String entityClassName = EaAlarmImagesMsg.class.getName();
            EsUploadProgress progress = manager.mapper(EsUploadProgressMapper.class, m -> m.selectOne(Wrappers.lambdaQuery(EsUploadProgress.class).eq(EsUploadProgress::getDataClass, entityClassName)));
            String startTime = progress == null ? null : progress.getUploadDate();
            List<EaAlarmImagesMsg> imagesMsgs = this.list(EaAlarmImagesMapper.class, EaAlarmImagesMsg.class, startTime, formatToday);
            this.addMessageList(imagesMsgs, formatToday);
            sendAttachList(imagesMsgs);
            this.saveOrUpdateProgress(progress, formatToday, entityClassName);
        }

        this.processOne(EsAlarmListMapper.class, EsAlarmListMsg.class);

        this.processOne(EsDeviceMapper.class, EsDeviceMsg.class);

        this.processOne(EsMessageMapper.class, EsMessageMsg.class);

        this.processOne(EsPatrolPointMapper.class, EsPatrolPointMsg.class);

        this.processOne(EsPatrolListMapper.class, EsPatrolListMsg.class);

        this.processOne(EsPublicityPersonMapper.class, EsPublicityPersonMsg.class);

        this.processOne(EsRoomMapper.class, EsRoomMsg.class);

        this.processOne(EsVideoDeviceMapper.class, EsVideoDeviceMsg.class);

        this.processOne(EsVideoMapper.class, EsVideoMsg.class);

        this.processOne(EsSourceMapper.class, EsSourceMsg.class);
        long end = System.currentTimeMillis();
        log.info("===========执行推送完成，开始时间:{}, 结束时间{}，总消耗[{}]ms=================", start, end, end - start);
    }

    private void sendErrorData(EsUploadError error) throws Exception {
        MyBatisManager manager = client.getMyBatisManager();
        String dataClass = error.getDataClass();
        Class<? extends BaseMessage> aClass = (Class<? extends BaseMessage>) Class.forName(dataClass);
        if (aClass == EaAlarmImagesMsg.class) {
            EaAlarmImagesMsg imagesMsg = manager.mapper(EaAlarmImagesMapper.class, m -> m.selectById(error.getRowId()));
            sendAttach(imagesMsg);
        } else {
            byte messageByte = MessageMap.messageByte(aClass);
            Class<? extends BaseMapper<? extends BaseMessage>> mapper = MessageMap.mapperByDataType(messageByte);
            BaseMessage message = manager.mapper(mapper, m -> m.selectById(error.getRowId()));
            sendRow(message);
        }
    }

    private <M extends BaseMapper<T>, T extends BaseMessage> void processOne(Class<M> mapperClz, Class<T> entityClass) {
        //获取之前的时间
        MyBatisManager manager = client.getMyBatisManager();
        String entityClassName = entityClass.getName();
        EsUploadProgress progress = manager.mapper(EsUploadProgressMapper.class,
                m -> m.selectOne(Wrappers.lambdaQuery(EsUploadProgress.class)
                        .eq(EsUploadProgress::getDataClass, entityClassName)));
        String formatToday = TimeUtil.formatToday();
        String startTime = progress == null ? null : progress.getUploadDate();
        List<T> rows = this.list(mapperClz, entityClass, startTime, formatToday);
        this.addMessageList(rows, formatToday);
        sendData(rows);
        this.saveOrUpdateProgress(progress, formatToday, entityClassName);
    }

    /**
     * 更新进度
     *
     * @param progress
     * @param uploadDate
     * @param entityClassName
     */
    private void saveOrUpdateProgress(EsUploadProgress progress, String uploadDate, String entityClassName) {
        MyBatisManager manager = client.getMyBatisManager();
        if (progress == null) {
            EsUploadProgress newProgress = new EsUploadProgress();
            newProgress.setUploadDate(uploadDate);
            newProgress.setDataClass(entityClassName);
            newProgress.setVanId(client.getVanId());
            newProgress.setAddTime(uploadDate);
            manager.mapper(EsUploadProgressMapper.class, m -> m.insert(newProgress));
        } else {
            progress.setUploadDate(uploadDate);
            progress.setLastUpdateTime(uploadDate);
            manager.mapper(EsUploadProgressMapper.class, m -> m.updateById(progress));
        }
    }

    private <M extends BaseMapper<T>, T extends BaseMessage> List<T> list(Class<M> mapperClz, Class<T> entityClass, String startTime, String endTime) {
        MyBatisManager manager = client.getMyBatisManager();
        LambdaQueryWrapper<T> query = Wrappers.lambdaQuery(entityClass);
        if (StringUtils.isNotBlank(startTime)) {
            query.ge(BaseMessage::getLastUpdateTime, startTime);
        }
        query.le(BaseMessage::getLastUpdateTime, endTime);
        List<T> rows = manager.list(mapperClz, query);
        return rows;
    }

    public void sendData(List<? extends BaseMessage> messages) {
        //推送数据
        for (BaseMessage message : messages) {
            this.sendRow(message);
        }
    }

    /**
     * 推送单条
     *
     * @param message
     */
    public void sendRow(BaseMessage message) {
        Channel channel = client.getChannel();
        channel.writeAndFlush(OneMessage.dataMessage(message));
    }

    public void sendAttachList(List<EaAlarmImagesMsg> messages) {
        //推送数据
        for (EaAlarmImagesMsg message : messages) {
            sendAttach(message);
        }
    }

    /**
     * 推送单个图片
     *
     * @param message
     */
    public void sendAttach(EaAlarmImagesMsg message) {
        //推送数据
        Channel channel = client.getChannel();
        ApplicationConfig config = client.getConfig();
        OneMessage<BaseMessage> oneMessage = OneMessage.attachMessage(message);
        String f = config.getAttachPath() + message.getImageUrl();
        File file = new File(f);
        if (file.isFile() && file.exists()) {
            try {
                Path path = Paths.get(f);
                byte[] fileBytes = Files.readAllBytes(path);
                oneMessage.setAttaches(fileBytes);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        channel.writeAndFlush(oneMessage);
    }

    private void addMessageList(List<? extends BaseMessage> message, String uploadTime) {
        MyBatisManager manager = client.getMyBatisManager();
        manager.mapperBatch(EsUploadErrorMapper.class, m -> {
            for (BaseMessage baseMessage : message) {
                EsUploadError error = new EsUploadError();
                error.setDataClass(baseMessage.getClass().getName());
                error.setUploadDate(uploadTime);
                error.setVanId(baseMessage.getVanId());
                error.setAddTime(uploadTime);
                error.setRowId(baseMessage.getId());
                m.insert(error);
            }
            return message.size();
        });
    }

    /**
     * 删除成功的记录
     *
     * @param rowId
     */
    private void deleteMessage(String rowId) {
        MyBatisManager manager = client.getMyBatisManager();
        manager.mapper(EsUploadErrorMapper.class, m -> m.delete(Wrappers.lambdaQuery(EsUploadError.class).eq(EsUploadError::getRowId, rowId)));
    }

    @Override
    public void run() {
        try {
            if (running) {
                return;
            }
            running = true;
            this.doTask();
        } catch (Throwable e) {
            log.error("发送任务出错！", e);
        } finally {
            running = false;
        }
    }

    /**
     * 处理返回结果
     *
     * @param ctx
     * @param message
     */
    public void handleResponse(ChannelHandlerContext ctx, OneMessage message) {
        if (message.isOk()) {
            this.deleteMessage(message.getRowId());
        }
    }
}
