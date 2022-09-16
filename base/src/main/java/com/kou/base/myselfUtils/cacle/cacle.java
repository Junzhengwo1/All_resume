//package myselfUtils.cacle;
//
//import java.math.BigDecimal;
//import java.util.Optional;
//
//public class cacle {
//    //计算小数位
//        this.taxAmount=Optional.ofNullable(this.taxAmount).map(s->s.setScale(2,BigDecimal.ROUND_HALF_UP)).orElse(new BigDecimal("0.00"));
//        this.deductionAmount=Optional.ofNullable(this.deductionAmount).map(s->s.setScale(2,BigDecimal.ROUND_HALF_UP)).orElse(new BigDecimal("0.00"));
//        this.appliedAmount=Optional.ofNullable(this.appliedAmount).map(s->s.setScale(2,BigDecimal.ROUND_HALF_UP)).orElse(new BigDecimal("0.00"));
//}

//
//public BigDecimal sumList(List<BigDecimal> list){
//        if(CollectionUtil.isEmpty(list))return BigDecimal.ZERO;
//        return scale2(list.stream().filter(ObjectUtil::isNotNull).reduce(BigDecimal.ZERO,BigDecimal::add));
//        }
//public BigDecimal scale2(BigDecimal value){
//        if(ObjectUtil.isNull(value))return null;
//        return  value.setScale(2,BigDecimal.ROUND_HALF_UP);
//        }
