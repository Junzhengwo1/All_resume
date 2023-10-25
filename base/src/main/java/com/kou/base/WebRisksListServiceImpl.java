package com.csin.platform.business.web.risks.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csin.platform.auth.dto.UserDto;
import com.csin.platform.auth.mapper.WebAuthUserMapper;
import com.csin.platform.auth.model.WebAuthDict;
import com.csin.platform.auth.model.WebAuthOrganization;
import com.csin.platform.auth.model.WebAuthUser;
import com.csin.platform.auth.service.IAdminDictService;
import com.csin.platform.auth.service.IDictService;
import com.csin.platform.auth.service.IOrganizationService;
import com.csin.platform.auth.service.IUserService;
import com.csin.platform.business.open.domain.cypt.CYPageResponse;
import com.csin.platform.business.open.domain.cypt.vo.CYPageRisksVO;
import com.csin.platform.business.open.domain.cypt.vo.CYPageUnitRisksStatisticsVO;
import com.csin.platform.business.open.domain.cypt.vo.CYRisksStatisticsVO;
import com.csin.platform.business.open.dto.RisksHandleVO;
import com.csin.platform.business.open.handler.OpenChengYunHandle;
import com.csin.platform.business.web.alarm.model.WebEventDisposalTimeDto;
import com.csin.platform.business.web.alarm.model.WebEventHandleStatsVO;
import com.csin.platform.business.web.patrol.model.PatrolRisksBingVO;
import com.csin.platform.business.web.position.service.IWebBuildingFloorService;
import com.csin.platform.business.web.position.service.IWebBuildingService;
import com.csin.platform.business.web.position.service.IWebLocationService;
import com.csin.platform.business.web.risks.model.RisksExportDTO;
import com.csin.platform.business.web.risks.model.RisksTrendVO;
import com.csin.platform.business.web.risks.model.WebRiskListVo;
import com.csin.platform.business.web.equipment.service.IWebRepairService;
import com.csin.platform.business.web.risks.model.RisksDispatchDTO;
import com.csin.platform.business.web.risks.model.WebRisksGradingStatsVO;
import com.csin.platform.business.web.risks.model.WebRisksGradingVO;
import com.csin.platform.business.web.risks.model.WebRisksTypeStatsReqDto;
import com.csin.platform.business.web.risks.model.WebRisksTypeStatsVO;
import com.csin.platform.business.web.alarm.enums.WebAlarmRiskStatusEnum;
import com.csin.platform.business.web.risks.enums.WebRiskSourceEnum;
import com.csin.platform.business.web.risks.enums.WebRisksLevelEnum;
import com.csin.platform.business.web.risks.enums.WebRisksTypeEnum;
import com.csin.platform.core.enums.YesNoEnum;
import com.csin.platform.business.web.risks.mapper.WebRisksListMapper;
import com.csin.platform.business.web.equipment.entity.WebRepair;
import com.csin.platform.business.web.risks.entity.WebRisksList;
import com.csin.platform.business.web.risks.entity.WebRisksUser;
import com.csin.platform.business.web.risks.service.IWebRisksListService;
import com.csin.platform.business.web.risks.service.IWebRisksUserService;
import com.csin.platform.business.web.sms.enums.LianTongEventTypeEnum;
import com.csin.platform.business.web.sms.enums.PushConditionEnum;
import com.csin.platform.business.web.sms.service.IWebPushService;
import com.csin.platform.business.web.sms.service.LianTongEventService;
import com.csin.platform.conf.SystemConfig;
import com.csin.platform.core.common.ApiException;
import com.csin.platform.core.common.ApiResponseCode;
import com.csin.platform.core.common.Constants;
import com.csin.platform.core.common.CustomConstants;
import com.csin.platform.core.service.WebRisksListRedisService;
import com.csin.platform.core.service.WebUserRedisService;
import com.csin.platform.core.util.AssertUtils;
import com.csin.platform.core.util.BeanUtil;
import com.csin.platform.core.util.DataUtil;
import com.csin.platform.core.util.ExcelUtil;
import com.csin.platform.core.util.ImageUtil;
import com.csin.platform.core.util.TimeUtil;
import com.csin.platform.core.util.UserUtil;
import com.google.common.collect.Lists;
import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.csin.platform.core.common.Constants.TIME_RANGE_MONTH;
import static com.csin.platform.core.util.TimeUtil.DATE_FORMAT;
import static com.csin.platform.core.util.TimeUtil.DEFAULT_FORMAT;
import static com.csin.platform.core.util.TimeUtil.TIME_RANGE_BEFORE30DAY;
import static com.csin.platform.core.util.TimeUtil.TIME_RANGE_SEVEN;

/**
 * <p>
 * 隐患处理工单 服务实现类
 * </p>
 *
 * @author wuxh
 * @since 2022-06-16
 */
@Service
@Slf4j
public class WebRisksListServiceImpl extends ServiceImpl<WebRisksListMapper, WebRisksList> implements IWebRisksListService {

    @Autowired
    private WebAuthUserMapper userMapper;
    @Autowired
    private IWebRisksUserService risksUserService;
    @Autowired
    private WebRisksListRedisService risksListRedisService;
    @Autowired
    private WebUserRedisService userRedisService;
    @Autowired
    private IWebRepairService webRepairService;
    @Autowired
    private SystemConfig config;
    @Autowired
    private IOrganizationService organizationService;
    @Autowired
    private IDictService dictService;
    @Autowired
    private IAdminDictService adminDictService;
    @Autowired
    private IWebLocationService webLocationService;
    @Autowired
    private IWebPushService webPushService;
    @Autowired
    private LianTongEventService lianTongEventService;
    @Resource
    private IWebBuildingService webBuildingService;
    @Resource
    private IWebBuildingFloorService webBuildingFloorService;
    @Resource
    private IUserService userService;

    /**
     * 处理工单
     */
    private void handleRisks(String id, String deadlineAttach, List<String> userIds) {
        WebRisksList risks = baseMapper.selectById(id);
        AssertUtils.assertIsTrue(StringUtils.isBlank(risks.getDeadline()), "请先处理上报后再派单！");
        //保存用户和隐患单关系数据并返回处置人登录名
        List<String> handlers = userIds.stream().map(userId -> {
            risksUserService.saveRisksUser(id, userId);
            WebAuthUser user = userMapper.selectById(userId);
            return user.getLoginName();
        }).collect(Collectors.toList());
        //更新工单数据
        UserDto user = UserUtil.getUser();
        AssertUtils.assertIsTrue(Objects.isNull(user), "当前无用户登录");
        LambdaUpdateWrapper<WebRisksList> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.set(WebRisksList::getDispatcher, user.getLoginName())
                .set(WebRisksList::getDispatchTime, TimeUtil.formatToday())
                .set(WebRisksList::getState, WebAlarmRiskStatusEnum.TO_CONFIRMED.getCode())
                .set(WebRisksList::getHandler, Joiner.on(CustomConstants.SEMICOLON).join(handlers))
                .eq(WebRisksList::getId, id);
        if (StringUtils.equals(YesNoEnum.YES.getCode(), risks.getDeadline())) {
            updateWrapper.set(WebRisksList::getDeadlineAttach, ImageUtil.removeUrls(deadlineAttach, config.getAttachUrl()));
            //限期整改超时未处理 推送数据发送至MQ
            webPushService.sendRisksPushQueue(risks, PushConditionEnum.RISKS_EXPIRE_TIMEOUT, user.getLoginName(), user.getId());
        } else {
            //立即整改超时未处理
            webPushService.sendRisksPushQueue(risks, PushConditionEnum.RISKS_AT_ONCE_TIMEOUT, user.getLoginName(), user.getId());
        }
        update(updateWrapper);
    }

    /**
     * 批量处理工单
     */
    @Override
    public void batchHandleList(RisksDispatchDTO params) {
        AssertUtils.assertIsTrue(StringUtils.isBlank(params.getIds()), "请选择工单！");
        AssertUtils.assertIsTrue(StringUtils.isBlank(params.getUserIds()), "请选择处置人员！");
        Arrays.stream(params.getIds().split(CustomConstants.COMMA)).forEach(id ->
                handleRisks(id, params.getDeadlineAttach(), Arrays.stream(params.getUserIds().split(CustomConstants.COMMA)).collect(Collectors.toList())));
    }

    @Override
    public void batchHandleReport(String ids, Map<String, Object> params) {
        String[] _ids = ids.split(",");
        String deadline = (String) params.get("deadline");
        String deadlineTime = (String) params.get("deadlineTime");
        String deadlineAttach = (String) params.get("deadlineAttach");
        Object deadlineDay1 = params.get("deadlineDay");
        Integer deadlineDay = deadlineDay1 == null ? null : Integer.parseInt(params.get("deadlineDay").toString());
        UserDto userDto = UserUtil.getUser();
        for (String id : _ids) {
            WebRisksList list = this.getById(id);
            list.setDeadline(deadline);
            list.setHandleReportTime(TimeUtil.formatToday());
            list.setHandleReportor(userDto.getLoginName());
            if (Constants.YES.equals(list.getDeadline())) {
                list.setDeadlineAttach(ImageUtil.removeUrls(deadlineAttach, config.getAttachUrl()));
                list.setDeadlineTime(deadlineTime);
                list.setDeadlineDay(deadlineDay);
            }
            this.updateById(list);
        }
    }

    @Override
    public List<Map<String, Object>> queryOrgRisksHandleSort(String state, String startTime, String endTime) {
        return this.baseMapper.queryOrgRisksHandleSort(state, startTime, endTime);
    }

    @Override
    public List<Map<String, Object>> queryOrgRisksHandleOpportunelySort(String state, String startTime,
                                                                        String endTime) {
        return this.baseMapper.queryOrgRisksHandleOpportunelySort(state, startTime, endTime);
    }

    @Override
    public Integer queryOrgRisksHandleNotOpportunelyNum() {
        //只要超时均为未及时整改
        return this.count(new LambdaQueryWrapper<WebRisksList>().eq(WebRisksList::getTimeOut, Constants.YES));
//        return this.baseMapper.queryOrgRisksHandleOpportunelyNum();
    }

    @Override
    public List<Map<String, Object>> sumDayEveryDay(WebRisksList list, String startDate, String endDate) {
        return this.baseMapper.sumDayEveryDay(list, startDate, endDate);
    }

    @Override
    public synchronized void batchAcceptList(String ids, UserDto userDto) {
        String[] _ids = ids.split(",");
        for (String id : _ids) {
            WebRisksList list = this.getById(id);
            if (!Constants.RISKS_LIST_CONFIRMING.equals(list.getState())) {
                throw new ApiException(ApiResponseCode.PARAM_ERROR.getCode(), "工单已被接收，不可再被接收！");
            }
            //接单
            UpdateWrapper<WebRisksUser> updateWrapper = new UpdateWrapper<>();
            updateWrapper.lambda().eq(WebRisksUser::getListId, id)
                    .eq(WebRisksUser::getUserId, userDto.getId())
                    .set(WebRisksUser::getState, Constants.ALARM_ACCEPT);
            risksUserService.update(updateWrapper);
            list.setState(Constants.ALARM_LIST_HANDLING);
            list.setTaker(userDto.getLoginName());
            list.setTakeTime(TimeUtil.formatToday());
            list.setFeedback(Constants.YES);
            risksListRedisService.removeZSet(list.getId());
            this.saveOrUpdate(list);
            //删除其他未处理的用户单子
            QueryWrapper<WebRisksUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(WebRisksUser::getListId, id)
                    .eq(WebRisksUser::getState, Constants.ALARM_READY);
            risksUserService.remove(queryWrapper);
        }
    }

    @Override
    public void handle(WebRisksList risksList) {
        UserDto userDto = UserUtil.getUser();
        WebRisksList list = this.getById(risksList.getId());
        list.setResult(risksList.getResult());
        list.setState(Constants.RISKS_LIST_OVER);
        list.setHandleImages(risksList.getHandleImages());
        //处置的时候不更新上报人员 list.setHandleReportor(userDto.getLoginName());
        list.setEliminateRisks(risksList.getEliminateRisks());
        list.setHandleSignature(risksList.getHandleSignature());
        list.setHandleTime(TimeUtil.formatToday());
        UpdateWrapper<WebRisksUser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(WebRisksUser::getListId, risksList.getId())
                .eq(WebRisksUser::getUserId, userDto.getId())
                .set(WebRisksUser::getState, Constants.RISKS_OVER);
        risksUserService.update(updateWrapper);
        //未消除隐患，流转到待维修列表
        if (!Constants.YES.equals(list.getEliminateRisks())) {
            WebRepair webRepair = new WebRepair();
            webRepair.setState("01");
            webRepair.setReportingStaff(list.getReporter());

            webRepair.setQr(list.getQrId());
            webRepair.setEquipmentId(list.getEquipmentId());
            webRepair.setEquipmentName(list.getEquipmentName());
            webRepair.setHazardType(list.getRisksType());
            webRepair.setBuild(list.getBuilding());
            webRepair.setFloor(list.getFloor());
            webRepair.setPosition(list.getAddress());
            webRepair.setRepairImages(list.getRisksImages());
            webRepair.setRemark(list.getResult());
            webRepairService.save(webRepair);
        }
        this.updateById(list);
    }

    @Override
    public void batchRejectList(String ids, UserDto userDto) {
        String[] _ids = ids.split(",");
        //拒绝
        for (String id : _ids) {
            WebRisksList list = this.getById(id);
            //拒绝接单
            UpdateWrapper<WebRisksUser> updateWrapper = new UpdateWrapper<>();
            updateWrapper.lambda().eq(WebRisksUser::getListId, id)
                    .eq(WebRisksUser::getUserId, userDto.getId())
                    .set(WebRisksUser::getState, Constants.ALARM_REJECT);
            risksUserService.update(updateWrapper);

            QueryWrapper<WebRisksUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(WebRisksUser::getListId, id)
                    .eq(WebRisksUser::getState, Constants.ALARM_READY);
            int count = risksUserService.count(queryWrapper);
            //检测单子是否均被拒绝
            if (Constants.RISKS_LIST_CONFIRMING.equals(list.getState()) && count < 1) {
                list.setFeedback(Constants.NO);
                this.saveOrUpdate(list);
            }
        }
    }

    @Override
    public List<Map<String, Object>> countEveryMonth(String taker, String state, String startDate, String endDate) {
        return getBaseMapper().countEveryMonth(taker, state, startDate, endDate);
    }

    @Override
    public List<Map<String, Object>> countEveryDay(String state, String startDate, String endDate, String organizationId) {
        return getBaseMapper().countEveryDay(state, startDate, endDate, organizationId);
    }

    @Override
    public List<Map<String, Object>> countEveryDayByList(WebRisksList list, String startDate, String endDate) {
        return getBaseMapper().countEveryDayByList(list, startDate, endDate);
    }

    @Override
    public List<Map<String, Object>> countEveryHour(String state, String startDate, String endDate) {
        return getBaseMapper().countEveryHour(state, startDate, endDate);
    }

    @Override
    public List<Map<String, Object>> countType(WebRisksList param, String streetId, List<String> orgIds, String start, String end) {
        return getBaseMapper().countType(param, streetId, orgIds, start, end);
    }

    @Override
    public List<Map<String, Object>> findRisksStreet(WebRisksList param, List<String> orgIds, String start, String end) {
        return getBaseMapper().findRisksStreet(param, orgIds, start, end);
    }

    @Override
    public List<Map<String, Object>> sumDayEveryDay(WebRisksList list, List<String> orgIds, String streetId, String start, String end) {
        return getBaseMapper().sumDayEveryDay2(list, orgIds, streetId, start, end);
    }

    @Override
    public List<Map<String, Object>> countEveryDayByList(WebRisksList list, List<String> orgIds, String streetId, String start, String end) {
        return getBaseMapper().countEveryDayByList2(list, orgIds, streetId, start, end);
    }

    /**
     * 统计隐患个数
     */
    @Override
    public int countRiskNum(String organizationId, String likeAddTime, String level, boolean timeOut) {
        QueryWrapper<WebRisksList> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(WebRisksList::getOrganizationId, organizationId)
                .like(WebRisksList::getAddtime, likeAddTime)
                .eq(StringUtils.isNotEmpty(level), WebRisksList::getLevel, level)
                .eq(timeOut, WebRisksList::getTimeOut, Constants.YES);
        return baseMapper.selectCount(queryWrapper);
    }

    /**
     * 统计隐患未整改个数
     */
    @Override
    public int countRiskNoNum(String organizationId, String likeAddTime, String level, boolean timeOut) {
        QueryWrapper<WebRisksList> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(WebRisksList::getOrganizationId, organizationId)
                .like(StringUtils.isNotBlank(likeAddTime), WebRisksList::getAddtime, likeAddTime)
                .eq(StringUtils.isNotEmpty(level), WebRisksList::getLevel, level)
                .eq(timeOut, WebRisksList::getTimeOut, Constants.YES)
                .eq(WebRisksList::getEliminateRisks, Constants.NO);
        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public int countRiskNoNum(List<String> organizationIds, String likeAddTime, String level, boolean timeOut) {
        QueryWrapper<WebRisksList> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .in(CollectionUtils.isNotEmpty(organizationIds), WebRisksList::getOrganizationId, organizationIds)
                .like(StringUtils.isNotBlank(likeAddTime), WebRisksList::getAddtime, likeAddTime)
                .eq(StringUtils.isNotEmpty(level), WebRisksList::getLevel, level)
                .eq(timeOut, WebRisksList::getTimeOut, Constants.YES)
                .eq(WebRisksList::getEliminateRisks, Constants.NO);
        return baseMapper.selectCount(queryWrapper);
    }

    /**
     * 统计隐患未整改个数
     */
    @Override
    public int countRiskNoNum(List<String> organizationIds, String level) {
        QueryWrapper<WebRisksList> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .in(WebRisksList::getOrganizationId, organizationIds)
                .eq(StringUtils.isNotBlank(level), WebRisksList::getLevel, level)
                .eq(WebRisksList::getEliminateRisks, "NO");
        return baseMapper.selectCount(queryWrapper);
    }

    /**
     * 统计隐患已处理个数
     */
    @Override
    public int statsRiskDone() {
        QueryWrapper<WebRisksList> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(WebRisksList::getState, WebAlarmRiskStatusEnum.COMPLETE.getCode());
        return baseMapper.selectCount(queryWrapper);
    }

    /**
     * 统计隐患个数
     */
    @Override
    public int countRiskNum(String start, String end, String organizationId) {
        QueryWrapper<WebRisksList> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(StringUtils.isNotBlank(organizationId), WebRisksList::getOrganizationId, organizationId)
                .between(WebRisksList::getAddtime, start, end);
        return baseMapper.selectCount(queryWrapper);
    }

    /**
     * 统计隐患个数
     */
    @Override
    public int countRiskNum(String start, String end, boolean handle, String organizationId, String riskType) {
        QueryWrapper<WebRisksList> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(WebRisksList::getOrganizationId, organizationId)
                .eq(StringUtils.isNotBlank(riskType), WebRisksList::getRisksType, riskType)
                .in(WebRisksList::getState, WebAlarmRiskStatusEnum.getStatus(handle))
                .between(WebRisksList::getReportTime, start, end);
        return baseMapper.selectCount(queryWrapper);
    }

    /**
     * 日常隐患总数
     */
    @Override
    public int countRisk(List<String> organizationIds) {
        QueryWrapper<WebRisksList> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(WebRisksList::getOrganizationId, organizationIds);
        return baseMapper.selectCount(queryWrapper);
    }

    /**
     * 隐患列表查询
     */
    @Override
    public Page<WebRiskListVo> queryRiskList(String level, String option, Boolean handle, Long current, Long size) {
        Pair<String, String> time = TimeUtil.getTimeRange(option);
        QueryWrapper<WebRisksList> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(StringUtils.isNotEmpty(level), WebRisksList::getLevel, level)
                .eq(Objects.nonNull(handle) && handle, WebRisksList::getState, WebAlarmRiskStatusEnum.COMPLETE.getCode())
                .ne(Objects.nonNull(handle) && !handle, WebRisksList::getState, WebAlarmRiskStatusEnum.COMPLETE.getCode())
                .between(WebRisksList::getAddtime, time.getLeft(), time.getRight())
                .orderByDesc(WebRisksList::getReportTime);
        Page<WebRisksList> pages = baseMapper.selectPage(new Page<>(current, size), queryWrapper);
        Page<WebRiskListVo> vos = new Page<>(pages.getCurrent(), pages.getSize(), pages.getTotal());
        if (CollectionUtils.isEmpty(pages.getRecords())) {
            return vos;
        }
        List<WebRiskListVo> records = pages.getRecords().stream().map(risk -> {
            WebRiskListVo vo = WebRiskListVo.builder().build();
            BeanUtils.copyProperties(risk, vo);
            //现场照片返回隐患图片和处理图片
            vo.setHandleImages(getRisksImage(risk.getRisksImages(), risk.getHandleImages(), risk.getState()));
            vo.setArea(webLocationService.buildLocation(risk.getBuilding(), risk.getFloor()));
            vo.setReporter(userRedisService.getUserName(risk.getReporter()));
            String handler = risk.getHandler();
            if (StringUtils.isNotBlank(handler)) {
                List<String> handers = Arrays.stream(handler.split(CustomConstants.SEMICOLON)).map(o -> userRedisService.getUserName(o)).collect(Collectors.toList());
                vo.setHandler(Joiner.on(CustomConstants.SEMICOLON).join(handers));
            }
            return vo;
        }).collect(Collectors.toList());
        vos.setRecords(records);
        return vos;
    }

    private String getRisksImage(String risksImages, String handleImages, String state) {
        String images = WebAlarmRiskStatusEnum.hasDisposed().contains(state) ? handleImages : risksImages;
        if (StringUtils.isNotBlank(images)) {
            return Joiner.on(CustomConstants.SEMICOLON).join(
                    Arrays.stream(images.split(CustomConstants.SEMICOLON)).map(img -> config.getAttachUrl() + img).collect(Collectors.toList())
            );
        }
        return Strings.EMPTY;
    }

    /**
     * 统计隐患自查被查数量
     *
     * @param checked true 自查 false被查
     */
    @Override
    public int queryRisksByCheck(boolean checked, Pair<String, String> time) {
        return queryRisksByCheck(checked, time.getLeft(), time.getRight());
    }

    /**
     * 统计隐患自查被查数量
     *
     * @param checked true 自查 false被查
     */
    @Override
    public int queryRisksByCheck(boolean checked, String start, String end) {
        LambdaQueryWrapper<WebRisksList> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.between(WebRisksList::getAddtime, start, end)
                .eq(WebRisksList::getRiskSource, checked ? "01" : "02");
        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public CYPageResponse<CYPageRisksVO> pageRisks(Page<WebRisksList> page, LambdaQueryWrapper<WebRisksList> wrapper) {
        Page<WebRisksList> webRisksListPage = this.baseMapper.selectPage(page, wrapper);
        CYPageResponse<CYPageRisksVO> cyPageResponse = new CYPageResponse<>();

        List<CYPageRisksVO> cyPageAlarmVOs = new ArrayList<>();
        for (WebRisksList webRisksList : webRisksListPage.getRecords()) {
            CYPageRisksVO cyPageRisksVO = new CYPageRisksVO();
            WebAuthOrganization organization = organizationService.getById(webRisksList.getOrganizationId());
            cyPageRisksVO.setUnitName(organization.getOrgName());
            cyPageRisksVO.setHiddenTroubleId(webRisksList.getId());
            cyPageRisksVO.setNormalNum(null);
            cyPageRisksVO.setBigNum(null);
            cyPageRisksVO.setCreateTime(webRisksList.getAddtime());
            cyPageRisksVO.setCreateUser(webRisksList.getCreator());
            cyPageRisksVO.setTroubleName(webRisksList.getRisksRemark());
            String risksType = webRisksList.getRisksType();
            QueryWrapper<WebAuthDict> dictWrapper = new QueryWrapper<>();
            dictWrapper.lambda().eq(WebAuthDict::getDictCode, risksType);
            List<WebAuthDict> list = dictService.list(dictWrapper);
            if (Objects.nonNull(list) && !list.isEmpty()) {
                WebAuthDict webAuthDict = list.get(0);
                cyPageRisksVO.setTroubleType(webAuthDict.getDictValue());
                cyPageRisksVO.setTroubleLocation(webRisksList.getAddress());
                cyPageRisksVO.setTroubleDesc(webRisksList.getRisksRemark());

            }
            String status = null;
            if (StringUtils.equals(webRisksList.getState(), Constants.RISKS_LIST_OVER)) {
                status = "2";
            } else {
                status = "1";
            }
            cyPageRisksVO.setCompleteStatus(status);

            cyPageAlarmVOs.add(cyPageRisksVO);
        }
        cyPageResponse.setRecords(cyPageAlarmVOs);
        return cyPageResponse;
    }

    @Override
    public List<CYPageUnitRisksStatisticsVO> risksStatistics(Page<WebAuthOrganization> organizationPage, String beginTime) {

        List<CYPageUnitRisksStatisticsVO> cyPageUnitRisksStatisticsVOS = new ArrayList<>();
        for (WebAuthOrganization record : organizationPage.getRecords()) {

            CYPageUnitRisksStatisticsVO cyPageUnitRisksStatisticsVO = new CYPageUnitRisksStatisticsVO();
            cyPageUnitRisksStatisticsVO.setUnitName(record.getOrgName());
            cyPageUnitRisksStatisticsVO.setId(record.getId());

            // 普通隐患数量
            LambdaQueryWrapper<WebRisksList> normalNumWrapper = Wrappers.lambdaQuery();
            normalNumWrapper.ge(StringUtils.isNotEmpty(beginTime), WebRisksList::getAddtime, beginTime)
                    .in(WebRisksList::getOrganizationId, OpenChengYunHandle.CY_ORGANIZATION_IDS)
                    .eq(WebRisksList::getLevel, WebRisksLevelEnum.GENERAL)
                    .eq(WebRisksList::getOrganizationId, record.getId());
            Integer normalNum = count(normalNumWrapper);
            cyPageUnitRisksStatisticsVO.setNormalNum(normalNum);

            // 普通隐患处理数量
            LambdaQueryWrapper<WebRisksList> normalHandleNumWrapper = Wrappers.lambdaQuery();
            normalHandleNumWrapper.ge(StringUtils.isNotEmpty(beginTime), WebRisksList::getAddtime, beginTime)
                    .in(WebRisksList::getOrganizationId, OpenChengYunHandle.CY_ORGANIZATION_IDS)
                    .eq(WebRisksList::getLevel, WebRisksLevelEnum.GENERAL)
                    .eq(WebRisksList::getState, Constants.RISKS_LIST_OVER)
                    .eq(WebRisksList::getOrganizationId, record.getId());
            Integer normalHandleNum = count(normalHandleNumWrapper);
            cyPageUnitRisksStatisticsVO.setNormalHandleNum(normalHandleNum);

            // 重大隐患处理数量
            LambdaQueryWrapper<WebRisksList> bigHandleNumWrapper = Wrappers.lambdaQuery();
            bigHandleNumWrapper.ge(StringUtils.isNotEmpty(beginTime), WebRisksList::getAddtime, beginTime)
                    .in(WebRisksList::getOrganizationId, OpenChengYunHandle.CY_ORGANIZATION_IDS)
                    .eq(WebRisksList::getLevel, WebRisksLevelEnum.IMPORTANT)
                    .eq(WebRisksList::getState, Constants.RISKS_LIST_OVER)
                    .eq(WebRisksList::getOrganizationId, record.getId());
            Integer bigHandleNum = count(bigHandleNumWrapper);
            cyPageUnitRisksStatisticsVO.setBigHandleNum(bigHandleNum);

            // 重大隐患数量
            LambdaQueryWrapper<WebRisksList> bigNumWrapper = Wrappers.lambdaQuery();
            bigNumWrapper.ge(StringUtils.isNotEmpty(beginTime), WebRisksList::getAddtime, beginTime)
                    .in(WebRisksList::getOrganizationId, OpenChengYunHandle.CY_ORGANIZATION_IDS)
                    .eq(WebRisksList::getLevel, WebRisksLevelEnum.IMPORTANT)
                    .eq(WebRisksList::getState, Constants.RISKS_LIST_OVER)
                    .eq(WebRisksList::getOrganizationId, record.getId());
            Integer bigNum = count(bigNumWrapper);
            cyPageUnitRisksStatisticsVO.setBigNum(bigNum);

            cyPageUnitRisksStatisticsVOS.add(cyPageUnitRisksStatisticsVO);

        }

        return cyPageUnitRisksStatisticsVOS;
    }

    @Override
    public CYRisksStatisticsVO risksAllStatistics(String beginTime) {

        CYRisksStatisticsVO cyRisksStatisticsVO = new CYRisksStatisticsVO();

        // 普通隐患数量
        LambdaQueryWrapper<WebRisksList> normalNumWrapper = Wrappers.lambdaQuery();
        normalNumWrapper.ge(StringUtils.isNotEmpty(beginTime), WebRisksList::getAddtime, beginTime)
                .in(WebRisksList::getOrganizationId, OpenChengYunHandle.CY_ORGANIZATION_IDS)
                .eq(WebRisksList::getLevel, WebRisksLevelEnum.GENERAL);
        Integer normalNum = count(normalNumWrapper);
        cyRisksStatisticsVO.setNormalNum(normalNum);

        // 普通隐患处理数量
        LambdaQueryWrapper<WebRisksList> normalHandleNumWrapper = Wrappers.lambdaQuery();
        normalHandleNumWrapper.ge(StringUtils.isNotEmpty(beginTime), WebRisksList::getAddtime, beginTime)
                .in(WebRisksList::getOrganizationId, OpenChengYunHandle.CY_ORGANIZATION_IDS)
                .eq(WebRisksList::getLevel, WebRisksLevelEnum.GENERAL)
                .eq(WebRisksList::getState, Constants.RISKS_LIST_OVER);
        Integer normalHandleNum = count(normalHandleNumWrapper);
        cyRisksStatisticsVO.setNormalHandleNum(normalHandleNum);

        // 重大隐患处理数量
        LambdaQueryWrapper<WebRisksList> bigHandleNumWrapper = Wrappers.lambdaQuery();
        bigHandleNumWrapper.ge(StringUtils.isNotEmpty(beginTime), WebRisksList::getAddtime, beginTime)
                .in(WebRisksList::getOrganizationId, OpenChengYunHandle.CY_ORGANIZATION_IDS)
                .eq(WebRisksList::getLevel, WebRisksLevelEnum.IMPORTANT)
                .eq(WebRisksList::getState, Constants.RISKS_LIST_OVER);
        Integer bigHandleNum = count(bigHandleNumWrapper);
        cyRisksStatisticsVO.setBigHandleNum(bigHandleNum);

        // 重大隐患数量
        LambdaQueryWrapper<WebRisksList> bigNumWrapper = Wrappers.lambdaQuery();
        bigNumWrapper.ge(StringUtils.isNotEmpty(beginTime), WebRisksList::getAddtime, beginTime)
                .in(WebRisksList::getOrganizationId, OpenChengYunHandle.CY_ORGANIZATION_IDS)
                .eq(WebRisksList::getLevel, WebRisksLevelEnum.IMPORTANT)
                .eq(WebRisksList::getState, Constants.RISKS_LIST_OVER);
        Integer bigNum = count(bigNumWrapper);
        cyRisksStatisticsVO.setBigNum(bigNum);

        return cyRisksStatisticsVO;
    }

    /**
     * 查询隐患处置时间数据
     */
    @Override
    public List<WebEventDisposalTimeDto> queryRisksDisposalTime(String start, String end, List<String> organizationIds) {
        LambdaQueryWrapper<WebRisksList> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(WebRisksList::getReportTime, WebRisksList::getHandleTime)
                .in(CollectionUtils.isNotEmpty(organizationIds), WebRisksList::getOrganizationId, organizationIds)
                .eq(WebRisksList::getState, WebAlarmRiskStatusEnum.COMPLETE.getCode())
                .between(WebRisksList::getReportTime, start, end);
        return baseMapper.selectList(queryWrapper).stream().map(info ->
                        WebEventDisposalTimeDto.builder().reportTime(info.getReportTime()).disposalTime(Objects.isNull(info.getHandleTime()) ? info.getReportTime() : info.getHandleTime()).build())
                .collect(Collectors.toList());
    }

    /**
     * 查询隐患数据
     */
    @Override
    public List<WebRisksList> queryRisksHandleList(String start, String end) {
        LambdaQueryWrapper<WebRisksList> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(WebRisksList::getReportTime, WebRisksList::getState)
                .ge(WebRisksList::getReportTime, start)
                .le(WebRisksList::getReportTime, end);
        return baseMapper.selectList(queryWrapper);
    }

    /**
     * 统计每天隐患数
     */
    @Override
    public Map<String, Integer> statsDayRisks(String start, String end) {
        QueryWrapper<WebRisksList> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("DATE_FORMAT(report_time,\"%Y-%m-%d\") as time,count(*) as num")
                .groupBy("DATE_FORMAT(report_time,\"%Y-%m-%d\")")
                .lambda().ge(WebRisksList::getReportTime, start).le(WebRisksList::getReportTime, end);
        Map<String, Integer> risks = Maps.newHashMap();
        baseMapper.selectMaps(queryWrapper).forEach(o -> risks.put(o.get("time").toString(), Integer.parseInt(o.get("num").toString())));
        return risks;
    }

    /**
     * 隐患统计
     */
    @Override
    public int countRiskData(String start, String end, List<String> organizationId, boolean finish) {
        LambdaQueryWrapper<WebRisksList> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper
                .in(CollectionUtils.isNotEmpty(organizationId), WebRisksList::getOrganizationId, organizationId)
                .in(finish, WebRisksList::getState, WebAlarmRiskStatusEnum.hasDisposed())
                .ge(StringUtils.isNotBlank(start), WebRisksList::getReportTime, start)
                .le(StringUtils.isNotBlank(end), WebRisksList::getReportTime, end);
        return baseMapper.selectCount(queryWrapper);
    }

    /**
     * 隐患分级统计
     */
    @Override
    public WebRisksGradingStatsVO risksGradingStats(String option) {
        Pair<String, String> time = TimeUtil.getTimeRange(option);
        //一般隐患
        int generalRisks = countRisks(time.getLeft(), time.getRight(), WebRisksLevelEnum.GENERAL);
        //重大隐患
        int majorRisks = countRisks(time.getLeft(), time.getRight(), WebRisksLevelEnum.IMPORTANT);
        //隐患次数和按时整改数
        List<WebRisksGradingVO> data = Lists.newArrayList();
        List<WebRisksList> risks = queryRisksHandleList(time.getLeft(), time.getRight());
        risks.stream().collect(Collectors.groupingBy(o -> TimeUtil.formatDay(o.getReportTime())))
                .forEach((date, list) ->
                        data.add(WebRisksGradingVO.builder()
                                .time(date).risksNum(list.size()).onTimeNum(countOnTimeRisk(list))
                                .build()));
        return WebRisksGradingStatsVO.builder()
                .risksTotal(generalRisks + majorRisks)
                .generalRisks(generalRisks).majorRisks(majorRisks)
                .data(data).build();
    }

    /**
     * 按时整改的隐患数
     */
    private int countOnTimeRisk(List<WebRisksList> risks) {
        return (int) risks.stream().filter(o ->
                WebAlarmRiskStatusEnum.hasDisposed().contains(o.getState()) && !StringUtils.equals(o.getTimeOut(), YesNoEnum.YES.getCode())).count();
    }

    /**
     * 统计隐患数
     */
    private int countRisks(String startTime, String endTime, WebRisksLevelEnum level) {
        return countRisks(startTime, endTime, level, Strings.EMPTY);
    }

    /**
     * 隐患统计
     */
    @Override
    public int countRisks(String startTime, String endTime, WebRisksLevelEnum level, String organizationId) {
        LambdaQueryWrapper<WebRisksList> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper
                .eq(StringUtils.isNotBlank(organizationId), WebRisksList::getOrganizationId, organizationId)
                .eq(WebRisksList::getLevel, level.getCode())
                .ge(WebRisksList::getReportTime, startTime)
                .le(WebRisksList::getReportTime, endTime);
        return baseMapper.selectCount(queryWrapper);
    }

    /**
     * 隐患统计-逾期处置数量
     */
    @Override
    public int overdueHandle(String startTime, String endTime, String organizationId) {
        LambdaQueryWrapper<WebRisksList> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(WebRisksList::getHandleTime, WebRisksList::getDeadlineTime)
                .eq(WebRisksList::getOrganizationId, organizationId)
                .in(WebRisksList::getState, WebAlarmRiskStatusEnum.getStatus(true))
                .eq(WebRisksList::getDeadline, YesNoEnum.YES.getCode())
                .between(WebRisksList::getReportTime, startTime, endTime);
        return (int) baseMapper.selectList(queryWrapper).stream().filter(info -> {
            LocalDateTime handleTime = TimeUtil.parse(info.getHandleTime(), DEFAULT_FORMAT);
            LocalDateTime deadlineTime = TimeUtil.parse(info.getHandleTime(), DATE_FORMAT);
            return handleTime.isAfter(deadlineTime);
        }).count();
    }

    /**
     * 隐患统计-逾期未处置数量,逾期：限期整改时间小于当前时间
     */
    @Override
    public int overdueUnHandle(String startTime, String endTime, String organizationId) {
        LambdaQueryWrapper<WebRisksList> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(WebRisksList::getOrganizationId, organizationId)
                .in(WebRisksList::getState, WebAlarmRiskStatusEnum.getStatus(false))
                .between(WebRisksList::getReportTime, startTime, endTime)
                .lt(WebRisksList::getDeadlineTime, TimeUtil.format(LocalDateTime.now(), TimeUtil.DEFAULT_FORMAT));
        return baseMapper.selectCount(queryWrapper);
    }

    /**
     * 根据状态统计隐患
     */
    @Override
    public int statsRisks(List<String> state) {
        LambdaQueryWrapper<WebRisksList> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(CollectionUtils.isNotEmpty(state), WebRisksList::getState, state);
        return baseMapper.selectCount(queryWrapper);
    }

    /**
     * 隐患类型统计
     */
    @Override
    public Page<WebRisksTypeStatsVO> risksTypeStats(WebRisksTypeStatsReqDto params) {
        if (StringUtils.isEmpty(params.getStartTime())) {
            Pair<String, String> time = TimeUtil.getTimeRange(TIME_RANGE_MONTH);
            params.setStartTime(time.getLeft());
            params.setEndTime(time.getRight());
        }
        QueryWrapper<WebRisksList> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("risks_type as risksType ", "count(1) as number");
        queryWrapper.lambda()
                .between(WebRisksList::getReportTime, params.getStartTime(), params.getEndTime())
                .groupBy(WebRisksList::getRisksType);
        queryWrapper.orderByDesc("number");
        Page<Map<String, Object>> page = baseMapper.selectMapsPage(new Page<>(params.getCurrent(), params.getSize()), queryWrapper);
        BigDecimal total = page.getRecords().stream().map(o -> new BigDecimal(o.get("number").toString())).reduce(BigDecimal.ZERO, BigDecimal::add);
        List<WebRisksTypeStatsVO> records = page.getRecords().stream().map(o -> {
            String risksType = (String) o.get("risksType");
            int number = Integer.parseInt(o.get("number").toString());
            BigDecimal percent = BigDecimal.valueOf(number).multiply(BigDecimal.valueOf(100)).divide(total, 2, RoundingMode.HALF_DOWN);
            return WebRisksTypeStatsVO.builder()
                    .riskType(adminDictService.queryDictValue("riskType", risksType))
                    .risksNum(number).percent(percent).build();
        }).collect(Collectors.toList());
        Page<WebRisksTypeStatsVO> vos = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        vos.setRecords(records);
        return vos;
    }

    /**
     * 查询隐患次数 以用户维度分组
     */
    @Override
    public List<WebEventHandleStatsVO> queryRisksGroupUser(String startTime, String endTime) {
        QueryWrapper<WebRisksList> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("taker", "count(1) as number");
        queryWrapper.lambda().ge(WebRisksList::getHandleTime, startTime)
                .le(WebRisksList::getHandleTime, endTime).in(WebRisksList::getState, WebAlarmRiskStatusEnum.hasDisposed())
                .groupBy(WebRisksList::getTaker);
        return baseMapper.selectMaps(queryWrapper).stream().map(o ->
                        WebEventHandleStatsVO.builder().user((String) o.get("taker")).times(Integer.valueOf(o.get("number").toString())).build())
                .collect(Collectors.toList());
    }

    /**
     * 已处置隐患消息 近30天：BEFORE30DAY
     */
    @Override
    public Page<RisksHandleVO> queryRisksHandle(List<String> organizationId, Long current, Long size) {
        Pair<String, String> time = TimeUtil.getTimeRange(TIME_RANGE_BEFORE30DAY);
        LambdaQueryWrapper<WebRisksList> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper
                .ge(WebRisksList::getLastUpdatetime, time.getLeft()).le(WebRisksList::getLastUpdatetime, time.getRight())
                .in(CollectionUtils.isNotEmpty(organizationId), WebRisksList::getOrganizationId, organizationId)
                .in(WebRisksList::getState, WebAlarmRiskStatusEnum.hasDisposed())
                .orderByDesc(WebRisksList::getLastUpdatetime);
        Page<WebRisksList> page = baseMapper.selectPage(new Page<>(current, size), queryWrapper);
        List<RisksHandleVO> records = page.getRecords().stream().map(record -> {
            RisksHandleVO vo = BeanUtil.copyProperties(record, RisksHandleVO.class);
            vo.setRisksTime(record.getReportTime());
            vo.setRisksType(WebRisksTypeEnum.getDesc(record.getRisksType()));
            vo.setReporter(userRedisService.getUserName(record.getReporter()));
            vo.setRiskSource(WebRiskSourceEnum.getDesc(record.getRiskSource()));
            vo.setArea(webLocationService.buildLocation(record.getBuilding(), record.getFloor()));
            vo.setRisksImages(buildRisksImages(record.getRisksImages()));
            vo.setCompleteTime(record.getLastUpdatetime());
            return vo;
        }).collect(Collectors.toList());
        Page<RisksHandleVO> vos = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        vos.setRecords(records);
        return vos;
    }

    /**
     * 按隐患类型统计数据
     */
    @Override
    public List<PatrolRisksBingVO> patrolRisksBing(String start, String end, boolean disposed, String level) {
        QueryWrapper<WebRisksList> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().between(WebRisksList::getAddtime, start, end)
                .eq(StringUtils.isNotBlank(level), WebRisksList::getLevel, level)
                .in(WebRisksList::getState, WebAlarmRiskStatusEnum.getStatus(disposed))
                .groupBy(WebRisksList::getRisksType);
        queryWrapper.select("risks_type as risksType,count(*) as num");
        List<Map<String, Object>> maps = baseMapper.selectMaps(queryWrapper);
        return maps.stream().map(map -> {
            String code = (String) map.get("risksType");
            Integer num = Integer.parseInt(map.get("num").toString());
            return PatrolRisksBingVO.builder().risksType(WebRisksTypeEnum.getDesc(code))
                    .risksTypeCode(code).number(num).build();
        }).collect(Collectors.toList());
    }

    private String buildRisksImages(String risksImages) {
        if (StringUtils.isBlank(risksImages)) {
            return Strings.EMPTY;
        }
        List<String> urls = Arrays.stream(risksImages.split(CustomConstants.COMMA_SEMICOLON)).map(img -> config.getAttachUrl() + img).collect(Collectors.toList());
        return Joiner.on(CustomConstants.COMMA).join(urls);
    }

    /**
     * 日常隐患当月新增数
     */
    @Override
    public int countRiskAdd(List<String> organizationIds, String likeAddTime) {
        QueryWrapper<WebRisksList> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(WebRisksList::getOrganizationId, organizationIds)
                .like(WebRisksList::getAddtime, likeAddTime);
        return baseMapper.selectCount(queryWrapper);
    }

    /**
     * 日常隐患待处理
     *
     * @param organizationIds
     * @param no
     * @return
     */
    @Override
    public int countRiskNo(List<String> organizationIds, String no) {
        QueryWrapper<WebRisksList> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(WebRisksList::getOrganizationId, organizationIds)
                .eq(WebRisksList::getEliminateRisks, no);
        return baseMapper.selectCount(queryWrapper);
    }


    @Override
    public void add(WebRisksList webRisksList) {
        if (Constants.YES.equals(webRisksList.getEliminateRisks())) {
            webRisksList.setState(Constants.RISKS_LIST_OVER);
        } else {
            webRisksList.setState(Constants.RISKS_LIST_READY);
        }
        this.save(webRisksList);
    }

    /**
     * 推送结束消息到联通事件中枢
     */
    @Override
    public void pushEndEvent(String id) {
        WebRisksList risks = baseMapper.selectById(id);
        WebAuthOrganization organization = organizationService.getById(risks.getOrganizationId());
        lianTongEventService.pushEndEvent(LianTongEventTypeEnum.RISKS_WARN_END, risks.getId(), organization, UserUtil.getUser());
    }

    /**
     * 隐患时处理数量
     */
    @Override
    public int statsRisksTimelyRectify(String start, String end) {
        return statsRisksTimelyRectify(start, end, Strings.EMPTY);
    }

    /**
     * 隐患时处理数量
     */
    @Override
    public int statsRisksTimelyRectify(String start, String end, String organizationId) {
        List<WebRisksList> list = queryRisksList(start, end, true, organizationId);
        if (CollectionUtils.isEmpty(list)) {
            return 0;
        }
        return (int) list.stream().filter(risks -> {
            if (StringUtils.isBlank(risks.getHandleTime())) {
                return false;
            }
            LocalDateTime handleTime = TimeUtil.parse(risks.getHandleTime(), TimeUtil.DEFAULT_FORMAT);
            if (StringUtils.isBlank(risks.getDeadline()) || StringUtils.equals(risks.getDeadline(), YesNoEnum.NO.getCode())) {
                //是否超过24小时
                LocalDateTime reportTime = TimeUtil.parse(risks.getReportTime(), TimeUtil.DEFAULT_FORMAT);
                return Duration.between(reportTime, handleTime).toHours() <= 24;
            }
            //是否超过限期整改时间
            LocalDateTime deadlineTime = TimeUtil.parse(risks.getDeadlineTime(), DATE_FORMAT);
            return handleTime.isBefore(deadlineTime);
        }).count();
    }

    private List<WebRisksList> queryRisksList(String start, String end, boolean disposed, String organizationId) {
        LambdaQueryWrapper<WebRisksList> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper
                .select(WebRisksList::getDeadline, WebRisksList::getReportTime, WebRisksList::getDeadlineTime, WebRisksList::getHandleTime)
                .ge(StringUtils.isNotBlank(start), WebRisksList::getReportTime, start)
                .le(StringUtils.isNotBlank(end), WebRisksList::getReportTime, end)
                .eq(StringUtils.isNotBlank(organizationId), WebRisksList::getOrganizationId, organizationId)
                .in(WebRisksList::getState, WebAlarmRiskStatusEnum.getStatus(disposed));
        return baseMapper.selectList(queryWrapper);
    }

    /**
     * 规定时间内制定整改计划数量 / 未制定计划数量
     */
    @Override
    public int statsRisksMakePlan(String start, String end, boolean make) {
        LambdaQueryWrapper<WebRisksList> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.ge(WebRisksList::getReportTime, start)
                .le(WebRisksList::getReportTime, end)
                .isNull(!make, WebRisksList::getDeadlineAttach)
                .isNotNull(make, WebRisksList::getDeadlineAttach)
                .eq(WebRisksList::getDeadline, YesNoEnum.YES.getCode());
        return baseMapper.selectCount(queryWrapper);
    }

    /**
     * 隐患及时处理数量
     */
    @Override
    public int statsRisksTimelyHandle(String organizationId) {
        LambdaQueryWrapper<WebRisksList> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(WebRisksList::getOrganizationId, organizationId)
                .in(WebRisksList::getState, WebAlarmRiskStatusEnum.hasDisposed())
                .and(la -> la.ne(WebRisksList::getTimeOut, YesNoEnum.YES.getCode()).or().isNull(WebRisksList::getTimeOut));
        return baseMapper.selectCount(queryWrapper);
    }

    /**
     * 统计派单数量
     */
    @Override
    public int statsDispatchOrder(boolean handle, String organizationId) {
        LambdaQueryWrapper<WebRisksList> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(WebRisksList::getOrganizationId, organizationId)
                .eq(handle, WebRisksList::getState, WebAlarmRiskStatusEnum.COMPLETE.getCode())
                .isNotNull(WebRisksList::getDispatchTime);
        return baseMapper.selectCount(queryWrapper);
    }

    /**
     * 查询隐患工单
     */
    @Override
    public WebRisksList queryRisksById(String risksId) {
        WebRisksList risks = baseMapper.selectById(risksId);
        AssertUtils.assertIsTrue(Objects.isNull(risks), "隐患工单不存在");
        return risks;
    }

    /**
     * 隐患上传方案超时 - 查询限期时间为当日且未完成和无附件的数据
     *
     * @return ：
     */
    @Override
    public List<WebRisksList> queryNeedPushScheme(String organizationId) {
        LambdaQueryWrapper<WebRisksList> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(WebRisksList::getOrganizationId, WebRisksList::getId, WebRisksList::getDeadlineTime, WebRisksList::getReporter)
                .ne(WebRisksList::getState, WebAlarmRiskStatusEnum.COMPLETE.getCode())
                .eq(WebRisksList::getOrganizationId, organizationId)
                .eq(WebRisksList::getDeadlineTime, TimeUtil.format(LocalDate.now()))
                .eq(WebRisksList::getDeadline, YesNoEnum.YES.getCode())
                .isNull(WebRisksList::getDeadlineAttach);
        return baseMapper.selectList(queryWrapper);
    }

    /**
     * 查询限期前几天需要提醒的数据 - 限期时间大于今日且未完成的数据
     */
    @Override
    public List<WebRisksList> queryNeedPushExpire(String organizationId) {
        LambdaQueryWrapper<WebRisksList> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(WebRisksList::getOrganizationId, WebRisksList::getId, WebRisksList::getDeadlineTime, WebRisksList::getReporter,
                        WebRisksList::getDeadlineDay)
                .ne(WebRisksList::getState, WebAlarmRiskStatusEnum.COMPLETE.getCode())
                .eq(WebRisksList::getDeadline, YesNoEnum.YES.getCode())
                .gt(WebRisksList::getDeadlineTime, TimeUtil.format(LocalDate.now()))
                .eq(WebRisksList::getOrganizationId, organizationId);
        return baseMapper.selectList(queryWrapper);
    }


    /**
     * 统计隐患数
     */
    @Override
    public Map<String, Integer> statsRisks(List<String> organizationIds, YesNoEnum eliminateRisks, String likeTime, YesNoEnum timeout, WebRisksLevelEnum level) {
        QueryWrapper<WebRisksList> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .in(WebRisksList::getOrganizationId, organizationIds)
                .like(StringUtils.isNotBlank(likeTime), WebRisksList::getReportTime, likeTime)
                .eq(Objects.nonNull(eliminateRisks), WebRisksList::getEliminateRisks, Objects.nonNull(eliminateRisks) ? eliminateRisks.getCode() : Strings.EMPTY)
                .eq(Objects.nonNull(timeout), WebRisksList::getTimeOut, Objects.nonNull(timeout) ? timeout.getCode() : Strings.EMPTY)
                .eq(Objects.nonNull(level), WebRisksList::getLevel, Objects.nonNull(level) ? level.getCode() : Strings.EMPTY)
                .groupBy(WebRisksList::getOrganizationId);
        queryWrapper.select(DataUtil.STATS_NUM_SELECT_SQL);
        return DataUtil.buildStatsNumByOrganizationId(baseMapper.selectMaps(queryWrapper), organizationIds);
    }

    /**
     * 统计派单数量
     */
    @Override
    public Map<String, Integer> statsDispatchOrder(boolean handle, List<String> organizationIds) {
        QueryWrapper<WebRisksList> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(WebRisksList::getOrganizationId, organizationIds)
                .eq(handle, WebRisksList::getState, WebAlarmRiskStatusEnum.COMPLETE.getCode())
                .isNotNull(WebRisksList::getDispatchTime)
                .groupBy(WebRisksList::getOrganizationId);
        queryWrapper.select(DataUtil.STATS_NUM_SELECT_SQL);
        return DataUtil.buildStatsNumByOrganizationId(baseMapper.selectMaps(queryWrapper), organizationIds);
    }

    /**
     * 隐患数据统计
     */
    @Override
    public Map<String, Integer> statisticsRisks(String startTime, String risksSource, boolean finish, List<String> organizationIds) {
        QueryWrapper<WebRisksList> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().ge(WebRisksList::getReportTime, startTime)
                .eq(StringUtils.isNotBlank(risksSource), WebRisksList::getRiskSource, risksSource)
                .in(finish, WebRisksList::getState, WebAlarmRiskStatusEnum.hasDisposed())
                .groupBy(WebRisksList::getOrganizationId);
        queryWrapper.select(DataUtil.STATS_NUM_SELECT_SQL);
        return DataUtil.buildStatsNumByOrganizationId(baseMapper.selectMaps(queryWrapper), organizationIds);
    }

    /**
     * 隐患及时处理数量
     */
    @Override
    public Map<String, Integer> statsRisksTimelyRectify(String startTime, List<String> organizationIds) {
        QueryWrapper<WebRisksList> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().ge(WebRisksList::getReportTime, startTime)
                .and(la -> la.isNull(WebRisksList::getTimeOut).or().eq(WebRisksList::getTimeOut, YesNoEnum.NO.getCode()))
                .in(WebRisksList::getState, WebAlarmRiskStatusEnum.hasDisposed())
                .groupBy(WebRisksList::getOrganizationId);
        queryWrapper.select(DataUtil.STATS_NUM_SELECT_SQL);
        return DataUtil.buildStatsNumByOrganizationId(baseMapper.selectMaps(queryWrapper), organizationIds);
    }

    /**
     * 设置隐患单超时
     */
    @Override
    public void updateTimeout(String risksId) {
        LambdaUpdateWrapper<WebRisksList> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.set(WebRisksList::getTimeOut, YesNoEnum.YES.getCode())
                .eq(WebRisksList::getId, risksId);
        update(updateWrapper);
    }

    /**
     * 隐患趋势
     */
    @Override
    public List<RisksTrendVO> risksTrend(String option) {
        Pair<String, String> time = TimeUtil.getTimeRange(option);
        List<WebRisksList> risks = queryRisksHandleList(time.getLeft(), time.getRight());
        //根据时间分组
        Map<String, List<Pair<String, String>>> map = risks.stream().map(o -> Pair.of(o.getReportTime().substring(0, 10), o.getState())).collect(Collectors.groupingBy(Pair::getLeft));
        boolean beforeSeven = StringUtils.equals(option, TIME_RANGE_SEVEN);
        AtomicInteger index = new AtomicInteger(beforeSeven ? 7 : 30);
        LocalDateTime now = LocalDateTime.now();
        List<RisksTrendVO> vos = Lists.newArrayList();
        while (index.get() > 0) {
            int i = index.getAndDecrement();
            String risksTime = TimeUtil.format(now.minusDays(i - 1)).substring(0, 10);
            List<Pair<String, String>> info = map.get(risksTime);
            int risksNum = CollectionUtils.isEmpty(info) ? 0 : info.size();
            int handle = CollectionUtils.isEmpty(info) ? 0 : (int) info.stream().filter(o -> StringUtils.equals(o.getRight(), WebAlarmRiskStatusEnum.COMPLETE.getCode())).count();
            vos.add(RisksTrendVO.builder().time(risksTime).risks(risksNum).handle(handle).build());
        }
        return vos;
    }

    /**
     * 导出隐患数据
     */
    @Override
    public void exportExcel(RisksExportDTO params, HttpServletResponse response) {
        LambdaQueryWrapper<WebRisksList> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(StringUtils.isNotBlank(params.getRiskSource()), WebRisksList::getRiskSource, params.getRiskSource())
                .eq(StringUtils.isNotBlank(params.getLevel()), WebRisksList::getLevel, params.getLevel())
                .eq(StringUtils.isNotBlank(params.getRisksType()), WebRisksList::getRisksType, params.getRisksType())
                .eq(StringUtils.isNotBlank(params.getBuilding()), WebRisksList::getBuilding, params.getBuilding())
                .eq(StringUtils.isNotBlank(params.getFloor()), WebRisksList::getFloor, params.getFloor())
                .eq(StringUtils.isNotBlank(params.getReporter()), WebRisksList::getReporter, params.getReporter())
                .in(WebRisksList::getState, WebAlarmRiskStatusEnum.getStatus(params.getHandle()))
                .orderByDesc(WebRisksList::getReportTime);
        Page<WebRisksList> page = baseMapper.selectPage(new Page<>(params.getCurrent(), params.getSize()), queryWrapper);
        Set<String> building = page.getRecords().stream().map(WebRisksList::getBuilding).collect(Collectors.toSet());
        Map<String, String> buildingName = webBuildingService.queryBuildingName(building);
        Set<String> floor = page.getRecords().stream().map(WebRisksList::getFloor).collect(Collectors.toSet());
        Map<String, String> floorName = webBuildingFloorService.queryFloorName(floor);
        Set<String> loginNames = Sets.newHashSet();
        page.getRecords().forEach(info -> {
            if (StringUtils.isNotBlank(info.getReporter())) {
                loginNames.add(info.getReporter());
            }
            if (StringUtils.isNotBlank(info.getHandler())) {
                loginNames.addAll(Arrays.stream(info.getHandler().split(CustomConstants.SEMICOLON)).collect(Collectors.toList()));
            }
        });
        Map<String, String> usernames = userService.queryUsername(loginNames);
        AtomicInteger order = new AtomicInteger(1);
        AtomicInteger risksImageSize = new AtomicInteger(1);
        AtomicInteger handleImageSize = new AtomicInteger(1);
        List<Map<String, Object>> rows = page.getRecords().stream().map(info -> {
            Map<String, Object> row = Maps.newHashMap();
            row.put("order", order.getAndIncrement());
            row.put("riskSource", WebRiskSourceEnum.getDesc(info.getRiskSource()));
            row.put("level", WebRisksLevelEnum.getRisksLevel(info.getLevel()));
            row.put("risksType", WebRisksTypeEnum.getDesc(info.getRisksType()));
            row.put("location", getLocation(buildingName.get(info.getBuilding()), floorName.get(info.getFloor())));
            row.put("address", info.getAddress());
            ExcelUtil.setImageSize(info.getRisksImages(), risksImageSize);
            row.put("risksImages", info.getRisksImages());
            row.put("reporter", usernames.get(info.getReporter()));
            row.put("reportTime", info.getReportTime());
            row.put("deadline", StringUtils.equals(info.getDeadline(), YesNoEnum.YES.getCode()) ? "限期整改" : "立即整改");
            row.put("state", StringUtils.equals(info.getState(), WebAlarmRiskStatusEnum.COMPLETE.getCode()) ? "已处置" : "未处置");
            ExcelUtil.setImageSize(info.getHandleImages(), handleImageSize);
            row.put("handleImages", info.getHandleImages());
            row.put("handler", usernames.get(info.getHandler()));
            row.put("handleTime", info.getHandleTime());
            return row;
        }).collect(Collectors.toList());
        ExcelUtil.ExcelTitle[] titles = {
                ExcelUtil.title("序号", "order"),
                ExcelUtil.title("隐患来源", "riskSource"),
                ExcelUtil.title("隐患级别", "level"),
                ExcelUtil.title("隐患类型", "risksType"),
                ExcelUtil.title("所属区域", "location"),
                ExcelUtil.title("详细地址", "address"),
                ExcelUtil.title("整改前照片", "risksImages", risksImageSize.get()),
                ExcelUtil.title("上报人员", "reporter"),
                ExcelUtil.title("上报时间", "reportTime"),
                ExcelUtil.title("整改类型", "deadline"),
                ExcelUtil.title("处置结果", "state"),
                ExcelUtil.title("整改后照片", "handleImages", handleImageSize.get()),
                ExcelUtil.title("处置人员", "handler"),
                ExcelUtil.title("处置时间", "handleTime")
        };
        ExcelUtil.write(titles, rows, response, "risks", Lists.newArrayList("risksImages", "handleImages"));
    }

    private String getLocation(String building, String floor) {
        return (org.apache.commons.lang3.StringUtils.isBlank(building) ? Strings.EMPTY : building) + (org.apache.commons.lang3.StringUtils.isBlank(floor) ? Strings.EMPTY : floor);
    }


}
