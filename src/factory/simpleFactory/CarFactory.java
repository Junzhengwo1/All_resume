package factory.simpleFactory;

/**
 * @author JIAJUN KOU
 *
 * 简单工厂模式实现
 */
public class CarFactory {

    public  static Car getCar(String car){
        if(car.equals("五菱")){
            return new Wuling();
        }else if(car.equals("特斯拉")){
            return new Tesla();
        }else {
            return null;
        }

    }
}
