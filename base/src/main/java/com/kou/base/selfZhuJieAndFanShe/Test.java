package selfZhuJieAndFanShe;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//TODO 注解学习(自定义注解)
public class Test extends Object{


    @Override
    public String toString() {
        return "Test{}";
    }

    @MySelfAnnotation
    public String test(){

        return "ok";
    }


}


//todo  定义一个注解 (通过四个元注解 就可以自定义注解了。)

@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
@interface  MySelfAnnotation{


}






















