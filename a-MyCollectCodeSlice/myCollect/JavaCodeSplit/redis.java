


public static void main(String[]args){



/**
 * 获取指定前缀的值
 * @param prefix key前缀
 * @return
 */
public <T> List<T> getPrefixKeyValue(String prefix,Class<T> clz) {
        List<Object> values = null;
        // 获取所有的key
        Set<String> keys = redisTemplate.keys(prefix);
        if (null != keys){
        // 批量获取数据
        values = redisTemplate.opsForValue().multiGet(keys);
        }
        if (CollectionUtil.isNotEmpty(values)){
        return values.stream().map(clz::cast).collect(Collectors.toList());
        }
        return Collections.emptyList();
        }



}

