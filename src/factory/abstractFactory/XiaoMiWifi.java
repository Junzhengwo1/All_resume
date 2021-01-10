package factory.abstractFactory;

/**
 * @author JIAJUN KOU
 */
public class XiaoMiWifi implements WIFiProduct {
    @Override
    public void openWifi() {
        System.out.println("开启小米Wifi");
    }

    @Override
    public void setting() {
        System.out.println("开启小米设置");
    }
}
