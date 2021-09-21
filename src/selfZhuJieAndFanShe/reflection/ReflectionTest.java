package selfZhuJieAndFanShe.reflection;


import java.lang.reflect.*;
import java.util.List;
import java.util.Map;

public class ReflectionTest {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        //通过反射获取类的Class对象
        Class<?> c1 = Class.forName("selfZhuJieAndFanShe.reflection.User");
        Class<?> c2 = Class.forName("selfZhuJieAndFanShe.reflection.User");
        Class<?> c3 = Class.forName("selfZhuJieAndFanShe.reflection.User");
        //一个类在内存中只有一个class对象
        //一个类在加载后，类的整个结构都会被封装在Class对像中
//        System.out.println(c1);
//        System.out.println(c2);
//        System.out.println(c3);
//        Class<? extends ReflectionTest> aClass = new ReflectionTest().getClass();
//        System.out.println(aClass);
        User user = new User("kou",23);
        Class<? extends User> userClass = user.getClass();


//        //获取类的信息
//        System.out.println(userClass.getName());
//        System.out.println(userClass.getSimpleName());
//        Field[] declaredFields = userClass.getDeclaredFields();
//        for (Field declaredField : declaredFields) {
//            System.out.println(declaredField);
//        }
//        System.out.println("-------------------------------");
//        Field name = userClass.getDeclaredField("name");
//        System.out.println(name);
//        String name1 = name.getName();
//        System.out.println(name1);
//        System.out.println("-------------------------------");
//        Method[] methods = userClass.getMethods(); //获取本类以及父类的所有方法
//        for (Method method : methods) {
//            System.out.println(method);
//        }
//        Method getName = userClass.getMethod("getName", null);
//        Method setName = userClass.getMethod("setName", String.class);
//        System.out.println(getName.getName());
//        System.out.println(setName.getName());
//        System.out.println("--------------------------------");
//        Constructor<?>[] constructors = userClass.getConstructors();
//        for (Constructor<?> constructor : constructors) {
//            System.out.println(constructor);
//        }


        System.out.println("=================使用场景=================");
        User user1 = (User) userClass.newInstance(); //本质是调用了无参构造器
        User user2 = (User)c1.newInstance();
        Constructor<?> constructor = c1.getDeclaredConstructor(String.class, Integer.class);
        User user3 = (User) constructor.newInstance("queen", 20);
        System.out.println(user1.getName());
        System.out.println(user2.getName());
        System.out.println(user3.getName());
        System.out.println("=================获得方法===================");
        Method add = c1.getDeclaredMethod("add", Integer.class);
        System.out.println("=================执行方法===================");
        Object invoke = add.invoke(user3, 30);
        System.out.println(invoke);
        System.out.println("=================操作属性====================");
        Field name = c2.getDeclaredField("name"); //不能操作私有属性
        name.setAccessible(true);
        name.set(user2,"king");
        System.out.println(user2.getName());
        System.out.println("=================反射获取泛型=====================");
        Method testMethod = User.class.getDeclaredMethod("test", Map.class, List.class);
        Type[] genericParameterTypes = testMethod.getGenericParameterTypes();
        for (Type genericParameterType : genericParameterTypes) {
            //System.out.println(genericParameterType);
            if (genericParameterType instanceof ParameterizedType) {
                Type[] types = ((ParameterizedType) genericParameterType).getActualTypeArguments();
                for (Type type : types) {
                    System.out.println(type);
                }
            }

        }




    }





}

class User{
    private String name;
    private Integer age;

    public User() {
    }

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
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



    public Integer add (Integer age){
        return age+10;
    }

    public void test(Map<String,User> map, List<User> users){
        System.out.println("执行方法");
    }

}