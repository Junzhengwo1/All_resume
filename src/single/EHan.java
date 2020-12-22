package single;

/**
 * @author JIAJUN KOU
 * 饿汉方式
 */
public class EHan {

    private static EHan instance=new EHan();
    private EHan(){}
    public static EHan getInstance(){
        return instance;
    }
    public static void main(String[] args) {

        EHan eHan= EHan.getInstance();
        System.out.println(eHan);

    }
}
