package proxy.dynamicProxyUtil;


import proxy.staticProxy.UserServiceImpl;

/**
 * @author JIAJUN KOU
 */
public class Client {

    //真实的角色

    UserServiceImpl userService=new UserServiceImpl();
    //代理角色，不存在

    ProxyInvocationHandler pih = new ProxyInvocationHandler();


}
