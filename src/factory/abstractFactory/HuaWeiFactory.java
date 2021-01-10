package factory.abstractFactory;

/**
 * @author JIAJUN KOU
 */
public class HuaWeiFactory implements AbstractProductFactory {
    @Override
    public IphoneProduct iphoneProduct() {
        return new HuaWeiPhone();
    }

    @Override
    public WIFiProduct wifiProduct() {
        return new HuaWeiWifi();
    }
}
