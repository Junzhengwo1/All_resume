package ThreadAndRunnable.runnable;

/**
 * @author JIAJUN KOU
 */
public class TestRunnable implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i <20 ; i++) {
            System.out.println("我是子线程"+i);
        }
    }

    public static void main(String[] args) {
        TestRunnable testRunnable = new TestRunnable();
        Thread thread = new Thread(testRunnable);
        thread.start();
        for (int i = 0; i <200 ; i++) {
            System.out.println("我是主线程"+i);
        }
    }
}
