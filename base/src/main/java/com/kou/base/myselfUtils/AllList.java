package com.kou.base.myselfUtils;

public class AllList {
//
//
//    private static List<StatisticResultDto> summeryList(List<AdminRunStatusRecordStatistics> list) {
//        List<StatisticResultDto> targetList = new ArrayList<>();
//        if (CollectionUtil.isEmpty(list)){
//            return targetList;
//        }
//        for (int i = 0; i < list.size(); i++) {
//            String tmp = list.get(i).getAlarmReason();
//            Integer k = list.get(i).getCount();
//            for (int j = i+1; j < list.size(); j++) {
//                if (tmp.equals(list.get(j).getAlarmReason())){
//                    k+= list.get(j).getCount();
//                    list.remove(list.get(j));
//                    j--;
//                }
//            }
//            StatisticResultDto statisticResultDto = new StatisticResultDto();
//            statisticResultDto.setDescName(EnumAlarmReason.getMaintenanceName(list.get(i).getAlarmReason()));
//            statisticResultDto.setStatisticNum(k.longValue());
//            targetList.add(statisticResultDto);
//        }
//        return targetList;
//    }


//    // 相同的 key value相加
//        return statisticsList.stream().distinct()
//                .collect(Collectors.toMap(AdminRunStatusRecordStatistics::getAlarmReason, a -> a, (o1,o2)-> {
//        o1.setCount(o1.getCount() + o2.getCount());
//        return o1;
//    })).values().stream().map(e->{
//        StatisticResultDto statisticResultDto = new StatisticResultDto();
//        statisticResultDto.setDescName(subName?EnumAlarmReason.getMaintenanceName(e.getAlarmReason()):EnumAlarmReason.getDescByCode(e.getAlarmReason()));
//        statisticResultDto.setStatisticNum(e.getCount().longValue());
//        return statisticResultDto;
//    }).collect(Collectors.toList());

}
