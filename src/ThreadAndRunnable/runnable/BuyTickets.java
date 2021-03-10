package ThreadAndRunnable.runnable;
/**
 * @author JIAJUN KOU
 * 使用线程实现，买票案例
 */
public class BuyTickets implements Runnable {

    private int tickets=10;

    @Override
    public void run() {
        while (true){
            if(tickets<=0){
                break;
            }
            System.out.println(Thread.currentThread().getName()+"拿到了第"+tickets--+"票");
        }
    }


    public static void main(String[] args) {
        BuyTickets buyTickets=new BuyTickets();

        new Thread(buyTickets,"小明").start();
        new Thread(buyTickets,"小王").start();
        new Thread(buyTickets,"Statham").start();

    }
}
