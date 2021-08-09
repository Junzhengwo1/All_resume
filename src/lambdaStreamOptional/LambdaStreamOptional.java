package lambdaStreamOptional;


import com.sun.media.jfxmediaimpl.HostUtils;

import java.util.*;
import java.util.function.BiConsumer;


import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class LambdaStreamOptional {
    public static void main(String[] args) {
        List<User> users=new ArrayList<User>();
        users.add(new User("king",1000));
        users.add(new User("king1",20));
        users.add(new User("king2",21));
        users.add(new User("king3",22));
        users.add(new User("king4",23));
//        User user5 = new User("king5", 24);
//        users.add(user5);
//        users.add(user5);

        //TODO 1、咱们使用lambda表达试来按照年龄排序
        users.sort((l1,l2)->{
            return -(l1.getAge()-l2.getAge());
        });
        users.forEach(user -> System.out.println(user));

        //TODO 2、转换成set集合
        System.out.println("转换成set集合的遍历结果------------------");
        users.stream().collect(toSet()).forEach(user -> System.out.println(user));
        //TODO 3、list集合转换成map集合Function<User, String>:其中User为val，String则是key
        Map<String, User> collect = users.stream().collect(toMap(user -> {
            return user.getName();//name作为key
        }, user -> {
            return user;//对象本身作为val
        }));

        System.out.println("转换成map集合之后的遍历结果------------------------");
        //TODO 4、map的遍历
        collect.forEach((BiConsumer)(k,v)->{
            System.out.println(k+","+v);
        });


        //TODO 5、使用stream来计算
        System.out.println("计算结果------------------------------------");
        Stream<Integer> integerStream = Stream.of(10, 20,30);
        System.out.println(integerStream.reduce((o, o2) -> o + o2).get());

        System.out.println("第一种求和写法-------------------");
        Optional<User> sum = users.stream().reduce((user, user2) -> new User("sum", user.getAge() + user2.getAge()));
        System.out.println("年龄求和结果：--》"+sum.get().getAge());
        System.out.println("第二种求和写法-------------------");
        int v = users.stream().mapToInt(user -> user.getAge()).sum();
        System.out.println(v);

        //TODO 6、使用stream找到最大值最小值
        System.out.println("use Stream ------------------------------->");
        Optional<User> max = users.stream().max((user, user2) -> user.getAge() - user2.getAge());
        System.out.println(max.get().getAge());
        System.out.println(users.stream().min((o1, o2) -> o1.getAge() - o2.getAge()).get().getAge());

        //TODO 7、stream的match用法
        //匹配一条name为King的
        System.out.println("匹配结果展示");
        System.out.println(users.stream().anyMatch(user -> "king".equals(user.getName())));//true

        //TODO 8、Stream的过滤器的使用
        List<Student> Students=new ArrayList<Student>();
        Students.add(new Student("kou",1000));
        Students.add(new Student("kou",1200));
        Students.add(new Student("kou",1300));
        Students.add(new Student("kou",1500));
        Students.add(new Student("kou1",20));
        Students.add(new Student("kou2",21));
        Students.add(new Student("kou3",22));
        Students.add(new Student("kou4",23));
        Students.add(new Student("kou4",25));
        Students.add(new Student("kou4",25));
        Students.add(new Student("kou5",25));
        Students.add(new Student("kou6",26));
        Students.add(new Student("kou7",27));
        Students.add(new Student("kou8",28));
        Students.add(new Student("kou9",29));
        Students.add(new Student("kou10",30));
        //使用stream来过滤
        System.out.println("使用stream来过滤结果---------------");
        System.out.println(users.stream().findFirst());
        List<User> filterUsers = users.stream().filter(user -> user.getAge() > 20).collect(toList());
        filterUsers.forEach(user -> System.out.println(user));

        System.out.println("按照名字过滤出来的集合-------------------");
        List<Student> studentList = Students.stream().filter(student -> "kou4".equals(student.getName())).collect(toList());
        studentList.forEach(student -> System.out.println(student));

        System.out.println("按照名字和年龄大于23的过滤出来的集合-------------------");
        Students.stream().filter(student -> "kou4".equals(student.getName()) && student.getAge()>23)
                .collect(toList())
                .forEach(student -> System.out.println(student));
        //TODO 9、stream实现limit(但是和我们的MySQL是不同的)
        System.out.println("stream实现limit就是取头两条----------------------");
        Students.stream().limit(2).forEach(student -> System.out.println(student));//相当于就是取头两条
        //按照指定位置取
        System.out.println("stream实现limit就是取指定两条----------------------");
        Students.stream().skip(2).limit(3).forEach(student -> System.out.println(student));//跳过前面两条
        //TODO 10.Stream实现集合的排序
        System.out.println("排序--------------------");
        Students.stream()
                .sorted(((o1, o2) -> -(o1.getAge()-o2.getAge())))
                .forEach(student -> System.out.println(student));

        //TODO Eg：综合案例（对数据降序排序并且包含kou的前两位）
        System.out.println("综合案例的结果--------------------");
        Students.stream()
                .sorted((o1,o2)->-(o1.getAge()-o2.getAge()))//降序
                .filter(student -> "kou".equals(student.getName()))
                .limit(2)
                .forEach(student -> System.out.println(student));




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

class Student{

    private String name;
    private Integer age;

    public Student(String name, Integer age) {
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
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}