package factory.abstractFactory;

/**
 * @author JIAJUN KOU
 *
 * 抽象产品工厂
 */
public interface AbstractProductFactory {

    //生产手机

    IphoneProduct iphoneProduct();


    //生产wifi

    WIFiProduct wifiProduct();

}
