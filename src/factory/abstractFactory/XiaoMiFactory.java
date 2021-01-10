package factory.abstractFactory;

/**
 * @author JIAJUN KOU
 */
public class XiaoMiFactory implements AbstractProductFactory {
    @Override
    public IphoneProduct iphoneProduct() {
        return new XiaoMiPhone();
    }

    @Override
    public WIFiProduct wifiProduct() {
        return new XiaoMiWifi();
    }
}
