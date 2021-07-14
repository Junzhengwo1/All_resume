package treeBuilder;

import java.util.List;
import java.util.Map;

/**
 * @author JIAJUN KOU
 * Map实现树结构展示
 */
public class MapTree {
    /**
     * 树结构展示
     * @return
     */
    //TODO 这个是在springBoot中实现的

//    public Map<Object, List<Object>> tree(){
//        //TODO Object这个参数对象要是 有父ID这样字段的自关联数据
//        List<Object> list = dao.selectList(new QueryWrapper<>());
//
//        Map<Object,List<Object>> map = Maps.newLinkedHashMap();
//        if (CollectionUtils.isNotEmpty(list)) {
//            list.forEach(currUnitEntity -> {
//                String parentId = currUnitEntity.getParentId();
//                UnitEntity parentUnitEntity = new UnitEntity();
//                parentUnitEntity.setId(parentId);
//
//                if (!map.containsKey(parentUnitEntity)) {
//                    List<UnitEntity> children = Lists.newArrayList();
//                    children.add(currUnitEntity);
//                    map.put(parentUnitEntity,children);
//                }else{
//                    List<UnitEntity> children = map.get(parentUnitEntity);
//                    children.add(currUnitEntity);
//                }
//
//                if (!map.containsKey(currUnitEntity)) {
//                    List<UnitEntity> children = Lists.newArrayList();
//                    map.put(currUnitEntity,children);
//                    currUnitEntity.setChildren(children);
//                }else{
//                    Optional<UnitEntity> optional = map.keySet().stream().filter(e -> e.getId().equals(currUnitEntity.getId())).findFirst();
//                    BeanUtils.copyProperties(currUnitEntity,optional.get());
//                    optional.get().setChildren(map.get(currUnitEntity));
//                }
//            });
//        }
//        return map;
//    }
}
