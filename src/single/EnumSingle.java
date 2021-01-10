package single;

/**
 * @author JIAJUN KOU
 *
 * 枚举本身也是一个类
 */
public enum EnumSingle {

    INSTANCE;
    public EnumSingle getInstance(){
        return INSTANCE;
    }


}
