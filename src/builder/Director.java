package builder;

/**
 * @author JIAJUN KOU
 *
 * 指挥：核心，负责指挥构建一个工程，工程如何构建，有它决定
 */
public class Director {

    /**
     * 指挥工人按照顺序建造房子
     * @param builder
     * @return
     */
    public House build(Builder builder){
        builder.buildA();
        builder.buildB();
        builder.buildC();
        builder.buildD();
        return builder.getHouse();
    }


}
