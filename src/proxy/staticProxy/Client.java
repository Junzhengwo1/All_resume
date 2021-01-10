package proxy.staticProxy;

/**
 * @author JIAJUN KOU
 */
public class Client {
    public static void main(String[] args) {

        UserServiceImpl userService=new UserServiceImpl();

        UserServiceProxy userServiceProxy = new UserServiceProxy();
        userServiceProxy.setUserService(userService);
        userServiceProxy.add();

    }
}
