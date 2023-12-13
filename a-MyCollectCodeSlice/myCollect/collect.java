public static void main(String[]args){

        List<String> duplicateElements = list.stream()
        .distinct()
        .filter(e -> Collections.frequency(list, e) > 1)
        .collect(Collectors.toList());


        statisticsList.stream().distinct()
        .collect(Collectors.toMap(AdminRunStatusRecordStatistics::getAlarmReason, a -> a,
        (o1, o2) -> {
        o1.setCount(o1.getCount() + o2.getCount());
        return o1;
        }))


        String o = '\''+localTime + '\'';
        String a = " LAST_UPDATE_TIME = "+o+",";


public static List<String> getDateFor7List() {
        List<String> dateList = new ArrayList<>();
        LocalDate today = LocalDate.now();
        LocalDate sevenDaysAgo = today.minusDays(7);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (LocalDate date = sevenDaysAgo; date.isBefore(today); date = date.plusDays(1)) {
        dateList.add(date.format(formatter));
        }
        return dateList;
        }

        }





    // 创建Excel文件
    Workbook workbook = new XSSFWorkbook();
    // 创建多个sheet页
    for (int i = 0; i < sheetCount; i++) {
        workbook.createSheet("Sheet" + i);
    }

    // 将要导入的数据分批，每批数据由一个线程处理
    int batchSize = 10000;
    int threadCount = data.size() / batchSize + 1;
    ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
    for (int i = 0; i < threadCount; i++) {
        int start = i * batchSize;
        int end = Math.min((i + 1) * batchSize, data.size());
        List<Data> batchData = data.subList(start, end);
        executorService.submit(new BatchImportTask(workbook, batchData, i));
    }

    // 等待所有线程处理完毕后，保存Excel文件
    executorService.shutdown();
    while (!executorService.isTerminated()) {
        Thread.sleep(1000);
    }
    FileOutputStream outputStream = new FileOutputStream("data.xlsx");
    workbook.write(outputStream);
    outputStream.close();





// 对集合进行逻辑分页处理
public static <T> PageResult<T> initLogicPage(IPage<?> page, List<T> rows) {
        List<T> collect = rows.stream()
        .skip(page.getSize() * (page.getCurrent() - 1))
        .limit(page.getSize())
        .collect(Collectors.toList());
        return new PageResult<>(page.getCurrent(), page.getSize(), rows.size(), collect);
        }

// 分数段查询
private List<SetSysScorePO> countScoreGrade(String id,Integer startScore, Integer endScore) {
        return lambdaQuery()
        .ne(StringUtils.isNotBlank(id), SetSysScorePO::getId, id)
        .and(lq->lq.lt(SetSysScorePO::getStartScore, startScore)
        .gt(SetSysScorePO::getEndScore, startScore))
        .or(lq->lq.gt(SetSysScorePO::getEndScore, endScore)
        .lt(SetSysScorePO::getStartScore,endScore))
        .or(lq->lq.gt(SetSysScorePO::getStartScore, startScore)
        .lt(SetSysScorePO::getStartScore, endScore))
        .or(lq->lq.gt(SetSysScorePO::getEndScore, startScore)
        .lt(SetSysScorePO::getEndScore, endScore))
        .list();
        }



