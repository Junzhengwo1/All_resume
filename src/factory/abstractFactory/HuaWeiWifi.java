package factory.abstractFactory;

/**
 * @author JIAJUN KOU
 */
public class HuaWeiWifi implements WIFiProduct {
    @Override
    public void openWifi() {
        System.out.println("开去华为Wifi");
    }

    @Override
    public void setting() {
        System.out.println("开启华为设置");
    }
}
