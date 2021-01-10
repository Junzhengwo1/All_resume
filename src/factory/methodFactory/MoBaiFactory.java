package factory.methodFactory;

/**
 * @author JIAJUN KOU
 */
public class MoBaiFactory implements CarFactory {
    @Override
    public Car getCar() {
        return new MoBai();
    }
}
