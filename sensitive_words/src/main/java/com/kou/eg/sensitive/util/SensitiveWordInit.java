package com.kou.eg.sensitive.util;

import com.kou.eg.sensitive.entity.SensitiveWordEntity;

import java.util.*;

public class SensitiveWordInit {

    public HashMap sensitiveWordMap;

    /**
     * 初始化敏感词
     *
     * @return 敏感词map
     */
    public Map initKeyWord(List<SensitiveWordEntity> sensitiveWords) {
        try {
            // 从敏感词集合对象中取出敏感词并封装到Set集合中
            Set<String> keyWordSet = new HashSet<String>();
            for (SensitiveWordEntity s : sensitiveWords) {
                keyWordSet.add(s.getContent().trim());
            }
            // 将敏感词库加入到HashMap中
            addSensitiveWordToHashMap(keyWordSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sensitiveWordMap;
    }


    private void addSensitiveWordToHashMap(Set<String> keyWordSet){
        sensitiveWordMap = new HashMap(keyWordSet.size());
        //敏感词
        String key = null;
        // 用来按照相应的格式保存敏感词库数据
        Map nowMap = null;
        // 用来辅助构建敏感词库
        Map<String,String> newWorMap = null;
        Iterator<String> iterator = keyWordSet.iterator();
        while (iterator.hasNext()){
            key = iterator.next();
            nowMap = sensitiveWordMap;
            for (int i = 0; i < key.length(); i++) {
                // 截取敏感词当中的字，在敏感词库中字为HashMap对象的Key键值
                char keyChar = key.charAt(i);
                // 判断这个字是否存在于敏感词库中
                Object wordMap = nowMap.get(keyChar);
                if(wordMap != null){
                    nowMap= (Map<Object, Object>) wordMap;
                }else {
                    newWorMap = new HashMap<String, String>();
                    newWorMap.put("isEnd","0");
                    nowMap.put(keyChar,newWorMap);
                    nowMap = newWorMap;
                }

                // 如果该字是当前敏感词的最后一个字，则标识为结尾字
                if(i==key.length()-1){
                    nowMap.put("isEnd","1");
                }
            }
        }
    }





}
