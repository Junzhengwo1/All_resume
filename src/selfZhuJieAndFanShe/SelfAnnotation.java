package selfZhuJieAndFanShe;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class SelfAnnotation {

    @MyAnnotation(name = "king",likes = {"女人，女孩"})
    public String test(){
        return "kou";
    }
}

@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@interface MyAnnotation{


    String name() default "queen";  //这个格式并不是一个方法 定义了参数的话使用注解时 如果不传参就会报错
    int age() default 20;
    String[] likes();
}