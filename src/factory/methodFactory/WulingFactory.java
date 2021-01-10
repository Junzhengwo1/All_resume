package factory.methodFactory;

/**
 * @author JIAJUN KOU
 */
public class WulingFactory implements CarFactory {
    @Override
    public Car getCar() {
        return new Wuling();
    }
}
