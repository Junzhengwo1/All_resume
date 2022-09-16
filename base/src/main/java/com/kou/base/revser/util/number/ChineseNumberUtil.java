package revser.util.number;


/**
 * 将数字转为大写
 * @author wasong
 */
public class ChineseNumberUtil {

    private static char[] cnArr = new char[]{'一', '二', '三', '四', '五', '六', '七', '八', '九'};

    /**
     * 将数字转换为中文数字， 这里只写到了万
     *
     * @param arabicNum 阿拉伯数字
     * @return 中文数字
     */
    public static String arabicNumToChineseNum(int arabicNum) {
        String si = String.valueOf(arabicNum);
        String sd = "";
        if (si.length() == 1) {
            if (arabicNum == 0) {
                return sd;
            }
            sd += cnArr[arabicNum - 1];
            return sd;
        } else if (si.length() == 2) {
            if (si.substring(0, 1).equals("1")) {
                sd += "十";
                if (arabicNum % 10 == 0) {
                    return sd;
                }
            } else {
                sd += (cnArr[arabicNum / 10 - 1] + "十");
            }
            sd += arabicNumToChineseNum(arabicNum % 10);
        } else if (si.length() == 3) {
            sd += (cnArr[arabicNum / 100 - 1] + "百");
            if (String.valueOf(arabicNum % 100).length() < 2) {
                if (arabicNum % 100 == 0) {
                    return sd;
                }
                sd += "零";
            }
            sd += arabicNumToChineseNum(arabicNum % 100);
        } else if (si.length() == 4) {
            sd += (cnArr[arabicNum / 1000 - 1] + "千");
            if (String.valueOf(arabicNum % 1000).length() < 3) {
                if (arabicNum % 1000 == 0) {
                    return sd;
                }
                sd += "零";
            }
            sd += arabicNumToChineseNum(arabicNum % 1000);
        } else if (si.length() == 5) {
            sd += (cnArr[arabicNum / 10000 - 1] + "万");
            if (String.valueOf(arabicNum % 10000).length() < 4) {
                if (arabicNum % 10000 == 0) {
                    return sd;
                }
                sd += "零";
            }
            sd += arabicNumToChineseNum(arabicNum % 10000);
        }
        return sd;
    }

    public static void main(String[] args) {
        System.out.println(arabicNumToChineseNum(11));
    }
}

