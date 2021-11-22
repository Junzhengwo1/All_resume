package com.kou.eg.sensitive.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class SensitiveWordUtil {

    public static Map sensitiveWordMap = null;

    public static int minMatchType = 1;

    public static int maxMatchType = 2;

    public static int getWordSize() {
        if (SensitiveWordUtil.sensitiveWordMap == null) {
            return 0;
        }
        return SensitiveWordUtil.sensitiveWordMap.size();
    }

    /**
     * 是否包含敏感词
     *
     * @param txt
     * @param matchType
     * @return
     */
    public static boolean isContainSensitiveWord(String txt, int matchType) {

        boolean flag = false;
        for (int i = 0; i < txt.length(); i++) {
            int matchFlag = checkSensitiveWord(txt, i, matchType);
            if (matchFlag > 0) {
                flag = true;
            }
        }
        return flag;
    }


    /**
     * 获取敏感词内容
     *
     * @param txt
     * @param matchType
     * @return 敏感词内容
     */
    public static Set<String> getSensitiveWord(String txt, int matchType) {
        Set<String> sensitiveWordList = new HashSet<String>();
        for (int i = 0; i < txt.length(); i++) {
            int length = checkSensitiveWord(txt, i, matchType);
            if (length > 0) {
                // 将检测出的敏感词保存到集合中
                sensitiveWordList.add(txt.substring(i, i + length));
                i = i + length - 1;
            }
        }
        return sensitiveWordList;
    }

    /**
     * 替换敏感词
     *
     * @param txt
     * @param matchType
     * @param replaceChar
     * @return
     */
    public static String replaceSensitiveWord(String txt, int matchType, String replaceChar) {
        String resultTxt = txt;
        Set<String> set = getSensitiveWord(txt, matchType);
        Iterator<String> iterator = set.iterator();
        String word = null;
        String replaceString = null;
        while (iterator.hasNext()) {
            word = iterator.next();
            replaceString = getReplaceChars(replaceChar, word.length());
            resultTxt = resultTxt.replaceAll(word, replaceString);
        }
        return resultTxt;
    }


    /**
     * 替换敏感词内容
     *
     * @param replaceChar
     * @param length
     * @return
     */
    private static String getReplaceChars(String replaceChar, int length) {
        String resultReplace = replaceChar;
        for (int i = 1; i < length; i++) {
            resultReplace += replaceChar;
        }
        return resultReplace;
    }


    /**
     * 检查敏感词数量
     *
     * @param txt
     * @param beginIndex
     * @param matchType
     * @return
     */
    public static int checkSensitiveWord(String txt, int beginIndex, int matchType) {
        boolean flag = false;
        int matchFlag = 0; //记录敏感词数量
        char word = 0;
        Map nowMap = SensitiveWordUtil.sensitiveWordMap;
        for (int i = beginIndex; i < txt.length(); i++) {
            word = txt.charAt(i);
            nowMap = (Map) nowMap.get(word);
            if (nowMap != null) {
                matchFlag++;
                if ("1".equals(nowMap.get("isEnd"))) {
                    flag = true;
                    if (SensitiveWordUtil.minMatchType == matchType) {
                        break;
                    }
                }
            } else {
                break;
            }
        }
        if (!flag) {
            matchFlag = 0;
        }
        return matchFlag;
    }

}
