package myselfUtils.CalceAll;

import java.math.BigDecimal;

public class BigDecimalUtil {

    /**
     * 计算增幅 保留两位小数 百分数
     * @param oldBd
     * @param newBd
     * @return
     */
    public static BigDecimal calculationIncrease(BigDecimal oldBd, BigDecimal newBd){
        BigDecimal increase;
        if(newBd==null||oldBd==null) {
             increase = new BigDecimal("0");
        } else if(new BigDecimal("0").compareTo(oldBd)==0){
            increase=newBd.multiply(new BigDecimal("100.00"));
        }else{
            increase = newBd.subtract(oldBd).
                    multiply(new BigDecimal("100"))
                    .divide(oldBd,2,BigDecimal.ROUND_HALF_DOWN);
        }
        return increase;
    }

    /**
     * 计算增幅 保留四位小数
     * @param oldBd
     * @param newBd
     * @return
     */
    public static BigDecimal calculationIncrease1(BigDecimal oldBd, BigDecimal newBd){
        BigDecimal increase;
        if(newBd==null||oldBd==null||new BigDecimal("0").compareTo(oldBd)==0){
            increase = new BigDecimal("0");
        }else{
            increase = newBd.subtract(oldBd).divide(oldBd,4,BigDecimal.ROUND_HALF_DOWN);
        }
        return increase;
    }

    /**
     * 被除数为0或者存在null值返回0
     * @param divisor  除数，被除数
     * @param dividend
     * @return
     */
    public static BigDecimal calculationDivide(BigDecimal divisor, BigDecimal dividend){
        BigDecimal bd;
        if(divisor==null||dividend==null||new BigDecimal("0").compareTo(dividend)==0){
            bd = new BigDecimal("0");
        }else{
            bd = divisor.divide(dividend,2,BigDecimal.ROUND_HALF_UP);
        }
        return bd;
    }

    public static BigDecimal getBigDecimalValue(BigDecimal bigDecimal){
        if(bigDecimal==null){
            return new BigDecimal("0");
        }else{
           return bigDecimal;
        }
    }


    public static BigDecimal calculationAdd(BigDecimal... bigDecimals){
        BigDecimal bd = new BigDecimal("0");
        for (BigDecimal b : bigDecimals) {
            bd =  bd.add(b==null?new BigDecimal("0"):b);
        }
        return bd;
    }


    public static BigDecimal calculationMultiply(BigDecimal... bigDecimals){
        BigDecimal bd = new BigDecimal("1");
        for (BigDecimal b : bigDecimals) {
            bd =  bd.multiply(b==null?new BigDecimal("1"):b);
        }
        return bd;
    }

}
