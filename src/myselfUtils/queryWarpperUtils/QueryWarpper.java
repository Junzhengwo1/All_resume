//package myselfUtils.queryWarpperUtils;
//
//public class QueryWarpper {
//
//
//    ArrayListMultimap<String,ExpenseEntity> headerMultiMap = ArrayListMultimap.create();//合同id+项目+店铺+经办部门+币种+结算期间 --> 费用头
//    ArrayListMultimap<String,ExpenseLineEntity> lineMultiMap = ArrayListMultimap.create();//合同id+项目+店铺+经办部门+币种+结算期间 --> 费用行
//
//    LambdaQueryWrapper<WithdrawalDeductLine> lambdaQueryWrapper =  Wrappers.lambdaQuery(WithdrawalDeductLine.class);
//            lambdaQueryWrapper.eq(WithdrawalDeductLine::getClearingBillId,clearingBillId);
//            lambdaQueryWrapper.notIn(WithdrawalDeductLine::getOaApprovalStatusCode, Lists.newArrayList(Consants.Contract.StatusEnum.APPROVED.getCode(),Consants.Contract.StatusEnum.SUBMITTED.getCode()));
//}
