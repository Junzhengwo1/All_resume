package ThreadAndRunnable.synchroniedStudy;

/**
 * @author JIAJUN KOU
 * 死锁演示
 */
public class DeadLock {

    public static void main(String[] args) {
        MakeUp girl=new MakeUp(0,"灰姑娘");
        MakeUp girl2=new MakeUp(1,"白雪公主");
        girl.start();
        girl2.start();
    }

}
/**
 * 口红
 */
class Lipstick{


}

/**
 * 镜子
 */
class Mirror{


}

/**
 * 化妆操作
 */
class MakeUp extends Thread {

    //需要的资源只有一份；用static保证只有一份

    static Lipstick lipstick=new Lipstick();
    static Mirror mirror=new Mirror();

    int choice;//选择
    String girlName;//使用化妆品的人

    MakeUp(int choice,String girlName){
        this.choice=choice;
        this.girlName=girlName;
    }

    @Override
    public void run() {
        //准备开始化妆了
        try {
            makeUp();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    //化妆，互相持有对方的锁，就是需要拿到对方的资源
    private void makeUp() throws InterruptedException {
        if(choice==0){
            synchronized (lipstick){
                System.out.println(this.girlName+"获得口红的锁");
                Thread.sleep(1000);
//                synchronized (mirror){
//                    System.out.println(this.girlName+"获取镜子的锁");
//                }
            }
            synchronized (mirror){
                System.out.println(this.girlName+"获取镜子的锁");
            }
        }else {
            synchronized (mirror){
                System.out.println(this.girlName+"获取镜子的锁");
                Thread.sleep(2000);

            }
            synchronized (lipstick){
                System.out.println(this.girlName+"获取口红的锁");
            }

        }
    }
}