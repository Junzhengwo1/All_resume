package factory.methodFactory;

/**
 * @author JIAJUN KOU
 */
public class TeslaFactory implements CarFactory {
    @Override
    public Car getCar() {
        return new Tesla();
    }
}
