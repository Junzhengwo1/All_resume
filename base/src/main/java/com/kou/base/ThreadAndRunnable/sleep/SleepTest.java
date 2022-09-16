package ThreadAndRunnable.sleep;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author JIAJUN KOU
 *
 * 模拟一个倒计时
 */
public class SleepTest {

    public static void main(String[] args) throws InterruptedException {
        //tenDown();
        getTime();
    }

    /**
     * 模拟倒计时
     * @throws InterruptedException
     */
    public static void tenDown() throws InterruptedException {
        int num=10;
        while (true){
            Thread.sleep(1000);
            System.out.println(num--);
            if(num<=0){
                break;
            }
        }
    }

    /**
     * 打印系统的当前时间
     */
    public static void getTime() throws InterruptedException {
        Date sTime=new Date(System.currentTimeMillis());
        while (true){
            Thread.sleep(1000);
            System.out.println(new SimpleDateFormat("HH:mm:ss").format(sTime));
            sTime=new Date(System.currentTimeMillis());
        }

    }

}
