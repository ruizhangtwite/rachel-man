package task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

public class TaskTest {

    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(20,50,1000, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>());

    private static Logger logger = LoggerFactory.getLogger(TaskTest.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer>[] tasks = new FutureTask[10];
        for (int i = 0; i < 10; i++){
            tasks[i] = new FutureTask<>(()-> {
                Thread.sleep(3000);
                return 0;
            });
            executor.execute(tasks[i]);
        }
        for (int i = 0; i < 10; i++){

            tasks[i].get();
            logger.info("运行结束了" + i);
        }
        executor.shutdown();
    }
}
