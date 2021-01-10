package factory.methodFactory;


/**
 * @author JIAJUN KOU
 *
 * 消费者买车
 */
public class Consumer {

    public static void main(String[] args) {

        TeslaFactory teslaFactory = new TeslaFactory();
        Car car = teslaFactory.getCar();
        car.name();

        MoBaiFactory moBaiFactory = new MoBaiFactory();
        Car car1 = moBaiFactory.getCar();
        car1.name();


    }
}
