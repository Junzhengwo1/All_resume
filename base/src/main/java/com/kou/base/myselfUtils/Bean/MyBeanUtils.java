//package myselfUtils.Bean;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.function.Supplier;
//
//import static org.springframework.beans.BeanUtils.copyProperties;
//
///**
// * 优雅的转换数据
// * @author wangsong
// */
//public class MyBeanUtils {
//
//    /**
//     * 正常
//     */
//    public static <S, T> List<T> copyListProperties(List<S> sources, Supplier<T> target, Object o) {
//        return copyListProperties(sources, target, null);
//    }
//
//
//     * 无回调函数
//     * 使用场景：Entity、DTo、Vo层数据的复制，因为BeanUtils.copyProperties只能给目标对象的属性赋值，却不能在List集合下循环赋值，因此添加该方法
//     * 如：List<EmdmBrandHisEntity> 赋值到 List<BrandSelectListVo> ，List<BrandSelectListVo>中的 BrandSelectListVo 属性都会被赋予到值
//     * S: 数据源类 ，T: 目标类::new(eg: BrandSelectListVo::new)
//     */
//    public static <S, T> List<T> copyListProperties(List<S> sources, Supplier<T> target) {
//        List<T> list = new ArrayList<>(sources.size());
//        for (S source : sources) {
//            T t = target.get();
//            copyProperties(source, t);
//
//            list.add(t);
//        }
//        return list;
//    }
//
//    /**
//     * @author wangsong
//     * 有回调函数
//     * 使用场景：Entity、DTo、Vo层数据的复制，因为BeanUtils.copyProperties只能给目标对象的属性赋值，却不能在List集合下循环赋值，因此添加该方法
//     * 如：List<EmdmBrandHisEntity> 赋值到 List<BrandSelectListVo> ，List<BrandSelectListVo>中的 BrandSelectListVo 属性都会被赋予到值
//     * S: 数据源类 ，T: 目标类::new(eg: BrandSelectListVo::new)
//     */
//    public static <S, T> List<T> copyListProperties(List<S> sources, Supplier<T> target, BrandBeanUtilsCallBack<S, T> callBack) {
//        List<T> list = new ArrayList<>(sources.size());
//        for (S source : sources) {
//            T t = target.get();
//            copyProperties(source, t);
//            if (callBack != null) {
//                // 回调
//                callBack.callBack(source, t);
//            }
//            list.add(t);
//        }
//        return list;
//    }
//
//    @FunctionalInterface
//    public interface BrandBeanUtilsCallBack<S, T> {
//
//        void callBack(S t, T s);
//    }
//}
