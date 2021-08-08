package lambdaStreamOptional;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class LambdaStreamOptional {
    public static void main(String[] args) {
        List<User> users=new ArrayList<User>();
        users.add(new User("king1",20));
        users.add(new User("king2",21));
        users.add(new User("king3",22));
        users.add(new User("king4",23));
//        User user5 = new User("king5", 24);
//        users.add(user5);
//        users.add(user5);

        //1、咱们使用lambda表达试来按照年龄排序
        users.sort((l1,l2)->{
            return -(l1.getAge()-l2.getAge());
        });
        users.forEach(user -> System.out.println(user));

        //2、转换成set集合
        System.out.println("转换成set集合的遍历结果------------------");
        users.stream().collect(toSet()).forEach(user -> System.out.println(user));
        //3、list集合转换成map集合Function<User, String>:其中User为val，String则是key
        Map<String, User> collect = users.stream().collect(toMap(user -> {
            return user.getName();//name作为key
        }, user -> {
            return user;//对象本身作为val
        }));

        System.out.println("转换成map集合之后的遍历结果------------------------");
        //map的遍历
        collect.forEach((BiConsumer)(k,v)->{
            System.out.println(k+","+v);
        });


//        //使用stream来过滤
//        System.out.println(users.stream().findFirst());
//        Stream<User> userStream = users.stream().filter(user -> user.getAge() > 20);

    }


}

class User{

    private String name;
    private Integer age;

    public User(String name, Integer age) {
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
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}