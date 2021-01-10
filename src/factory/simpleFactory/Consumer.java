package factory.simpleFactory;



/**
 * @author JIAJUN KOU
 *
 * 消费者买车
 */
public class Consumer {

    public static void main(String[] args) {

        //使用工厂来拿车
        Car car = CarFactory.getCar("特斯拉");

        car.name();

    }
}
