package builder;

/**
 * @author JIAJUN KOU
 */
public class Test {

    public static void main(String[] args) {
        //指挥
        Director director = new Director();
        //指挥，具体的工人完成

        House build = director.build(new Worker());
        System.out.println(build.toString());

    }
}
