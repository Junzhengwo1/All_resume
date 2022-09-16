package ThreadAndRunnable.threadStudy;

/**
 * @author JIAJUN KOU
 *
 */
public class TestThread extends Thread {

    @Override
    public void run() {
        for (int i = 0; i <20 ; i++) {
            System.out.println("我是子线程"+i);
        }
    }


    public static void main(String[] args) {

        TestThread testThread = new TestThread();
        testThread.start();

        for (int i = 0; i <200 ; i++) {
            System.out.println("我是主线程"+i);
        }

    }
}
