package factory.abstractFactory;

/**
 * @author JIAJUN KOU
 */
public class HuaWeiPhone implements IphoneProduct {
    @Override
    public void start() {
        System.out.println("开启华为手机");
    }

    @Override
    public void shutdown() {
        System.out.println("关闭华为手机");

    }

    @Override
    public void callup() {
        System.out.println("华为手机打电话");

    }
}
