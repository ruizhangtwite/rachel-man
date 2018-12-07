package pack;

import java.util.concurrent.locks.LockSupport;

/**
 * Desp:
 * @link {LockSupport}
 *
 * 2018-12-06 21:32
 * Created by zhangrui.
 */
public class PackTest {

    public static void main(String[] args) {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("开始");
                LockSupport.park();  //执行LockSupport.park()方法时不释放锁
                System.out.println("我执行了吗？");
            }
        });
        
        thread1.start();

        try {
            Thread.sleep(2000L);
            System.out.println("我在执行");
            LockSupport.unpark(thread1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
