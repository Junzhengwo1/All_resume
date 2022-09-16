//package revser.util.tree;
//
//
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.List;
//
///**
// * 树形结构构造器类，用于列表转树形
// *
// * @param <T> 树节点的实体类
// */
//public class TreeBuilder<T extends Comparable> {
//
//    private Collection<T> nodeList;
//
//    private Method idGetter;
//
//    private Method parentGetter;
//
//    private Method childrenSetter;
//
//    private T root;
//
//    /**
//     * 列表转换树形实例构造
//     *
//     * @param list        用于构造树形的列表数据
//     * @throws NoSuchMethodException 异常
//     * @throws InvocationTargetException 异常
//     * @throws IllegalAccessException    异常
//     */
//    public TreeBuilder(Collection<T> list) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        if (CollectionUtil.isEmpty(list)) {
//            return;
//        }
//        nodeList = list;
//
//        Class clazz = nodeList.iterator().next().getClass();
//
//        idGetter = clazz.getMethod("getId");
//        parentGetter = clazz.getMethod("getParentId");
//        childrenSetter = clazz.getMethod("setChildren", List.class);
//
//        for (T item : nodeList) {
//            Object id = idGetter.invoke(item);
//            Object parent = parentGetter.invoke(item);
//            if (parent == null || "0".equals(parent)) {
//                childrenSetter.invoke(item, findChildren(id));
//                root = item;
//                break;
//            }
//        }
//    }
//
//    /**
//     * 获取构造后的树形
//     *
//     * @return 树形的根节点
//     */
//    public T getTree() {
//        return root;
//    }
//
//    private Collection<T> findChildren(Object pid) throws InvocationTargetException, IllegalAccessException {
//        List<T> children = new ArrayList<>();
//        for (T item : nodeList) {
//            Object id = idGetter.invoke(item);
//            Object parent = parentGetter.invoke(item);
//            if (pid.equals(parent)) {
//                children.add(item);
//                childrenSetter.invoke(item, findChildren(id));
//            }
//        }
//        Collections.sort(children);
//        return children;
//    }
//}
