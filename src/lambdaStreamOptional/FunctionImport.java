package lambdaStreamOptional;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * TODO 方法引入相关(需要结合lambda表达式)
 * 1、静态方法： 类名：：静态方法
 * 2、对象：     类名：：实例方法
 * 3、实例：     new 对象 对象实例：：方法引入
 * 4、构造函数： 类名：：new
 */
public class FunctionImport {

    public static void main(String[] args) {
        //最原始的调用函数式接口的方式 采取匿名内部内类的方式
        MyFunctionInterface myFunctionInterface = new MyFunctionInterface() {
            @Override
            public void get(Integer a) {
                System.out.println("get-->" + a);
            }
        };
        myFunctionInterface.get(1);

        //TODO 1、改用成lambda表达式 静态方法引入
        MyFunctionInterface staticGet = FunctionImport::staticGet;
        staticGet.get(2);
        //TODO 2、实例方法引入
        FunctionImport functionImport = new FunctionImport();
        MyFunctionInterface2 myFunctionInterface2 = functionImport::objGet;
        System.out.println(myFunctionInterface2.getMeg("王queen"));

    }

    //静态方法的方法引入
    public static void staticGet(Integer a){
        System.out.println("staticGet-->"+ a );
    }

    //实例方法的引入
    public String objGet(String s){
        return "寇king"+s;
    }

}
class You{

    private String name;
    private Integer age;

    public You(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "You{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}