package factory.abstractFactory;

/**
 * @author JIAJUN KOU
 */
public class Client {
    public static void main(String[] args) {

        System.out.println("-----小米系列的产品------");
        XiaoMiFactory xiaoMiFactory = new XiaoMiFactory();
        IphoneProduct iphoneProduct = xiaoMiFactory.iphoneProduct();
        iphoneProduct.callup();
        WIFiProduct wiFiProduct = xiaoMiFactory.wifiProduct();
        wiFiProduct.openWifi();

        System.out.println("------华为系列产品------");
        HuaWeiFactory huaWeiFactory = new HuaWeiFactory();
        IphoneProduct iphoneProduct1 = huaWeiFactory.iphoneProduct();
        iphoneProduct1.callup();
        WIFiProduct wiFiProduct1 = huaWeiFactory.wifiProduct();
        wiFiProduct1.openWifi();

    }
}
