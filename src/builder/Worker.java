package builder;

/**
 * @author JIAJUN KOU
 *
 * 具体的建造者
 */
public class Worker extends Builder {

    House house;

    public Worker(){
        house=new House();
    }


    @Override
    void buildA() {
        house.setBuildA("地基");
        System.out.println("地基");
    }

    @Override
    void buildB() {
        house.setBuildB("钢筋工程");
        System.out.println("钢筋工程");
    }

    @Override
    void buildC() {
        house.setBuildC("铺线");
        System.out.println("铺线");
    }

    @Override
    void buildD() {
        house.setBuildD("粉刷");
        System.out.println("粉刷");
    }

    @Override
    House getHouse() {
        return house;
    }
}
