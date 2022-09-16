package selfZhuJieAndFanShe.ReflectionForMapping;


import java.lang.annotation.*;
import java.lang.reflect.Field;

/**
 * todo 通过反射来实现属性与表结构对应
 */
public class ReflectionForMapping {


    public static void main(String[] args) throws NoSuchFieldException {
        //todo 通过反射来操作注解
        User user = new User();
        Annotation[] annotations = user.getClass().getAnnotations();
//        for (Annotation annotation : annotations) {
//            System.out.println(annotation);
//        }
        Class<? extends User> userClass = user.getClass();
        TableKou annotation = userClass.getAnnotation(TableKou.class);
        System.out.println(annotation);
        String value = annotation.value();
        System.out.println(annotation.value());
        //TODO 获得类指定的注解
        Field name = userClass.getDeclaredField("name");
        FieldKou fieldAnnotation = name.getAnnotation(FieldKou.class);
        System.out.println(fieldAnnotation.columnName());
        System.out.println(fieldAnnotation.type());
        System.out.println(fieldAnnotation.length());
    }
}

@TableKou("db_User")
class User{


    @FieldKou(columnName = "id",type = "int" , length = 20)
    private Integer id;
    @FieldKou(columnName = "name",type = "varchar" , length = 20)
    private String name;
    @FieldKou(columnName = "age",type = "int" , length = 20)
    private Integer age;

    public User() {
    }

    public User(Integer id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface TableKou{

    String value();

}

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface FieldKou{

    String columnName();
    String type();
    int length();
}