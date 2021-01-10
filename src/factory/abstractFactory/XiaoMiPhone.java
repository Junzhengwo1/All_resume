package factory.abstractFactory;

/**
 * @author JIAJUN KOU
 */
public class XiaoMiPhone implements IphoneProduct {
    @Override
    public void start() {
        System.out.println("开启小米手机");
    }

    @Override
    public void shutdown() {
        System.out.println("关闭小米手机");

    }

    @Override
    public void callup() {
        System.out.println("使用小米手机打电话");

    }
}
