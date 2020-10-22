package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author JIAJUN KOU
 */
public class ProxyTest {
    public static void main(String[] args) {
        //1.创建真实对象
        Lenove lenove=new Lenove();
        /**
         * 动态代理增强lenovo对象
         */

        /**
         * 1.类加载器 真实对象.getClass().getClassLoader()
         * 2.接口数组 真实对象.getClass().getInterfaces()
         * 3.处理器： new InvocationHandler()
         *
         */
        SaleComputer proxy_lenovo = (SaleComputer) Proxy.newProxyInstance(lenove.getClass().getClassLoader(), lenove.getClass().getInterfaces(), new InvocationHandler() {
            /**
             *代理逻辑编写的方法，代理对象调用的所有方法都会触发该方法执行
             * @param proxy 代理对象
             * @param method 代理对象调用的方法，被封装为对象
             * @param args 代理对象调用的方法时，传递实际参数
             * @return
             * @throws Throwable
             */
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //Object obj = method.invoke(lenove, args);
                //1.增强参数
                //判断是否是sale（）方法才可以增强参数
                if(method.getName().equals("sale")){
                    double money=(double) args[0];
                    money=money*0.85;
                    Object object=method.invoke(lenove,money);
                    //增强返回参数
                    return object+"_鼠标垫";
                }else {
                    Object obj = method.invoke(lenove, args);
                    return obj;
                }

            }
        });


        String sale = lenove.sale(60000);

        String computer = proxy_lenovo.sale(8000);
        System.out.println(computer);
        proxy_lenovo.show();


    }
}
