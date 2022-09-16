package lambdaStreamOptional;

import java.util.Optional;
import java.util.function.Predicate;

public class OptionalTest {

    public static void main(String[] args) {

        //判断空
        String userNme="abc";
        Integer userAge=20;
        System.out.println(Optional.ofNullable(userNme));
        Optional<Integer> userAge1 = Optional.ofNullable(userAge);
        System.out.println(userAge1+"+"+Optional.ofNullable(userAge).get());

        System.out.println("===========================");
        //判断怎么知道是否为空呢
        System.out.println(Optional.ofNullable(userNme).isPresent());// false则表示为空

        // optional 过滤与设定默认值
        System.out.println(Optional.ofNullable(userNme).orElse("King"));

        Optional<String> queen = Optional
                .ofNullable("queen")
                .filter(s -> "queen".equals(s));
        System.out.println(queen.isPresent());

        //todo Optional实战案例
        System.out.println("----------------------案例------------------");
        String user=null;
        String uName="queen";
        Optional.ofNullable(uName).ifPresent(s-> System.out.println(uName));//如果不为空就去执行




    }



}
