package proxy;

/**
 * @author JIAJUN KOU
 */
public class Lenove implements SaleComputer {
    @Override
    public String sale(double money) {

        System.out.println("花了"+money+"元，买了一台联想电脑。");
        return "联想电脑";
    }

    @Override
    public void show() {
        System.out.println("展示电脑");
    }
}
