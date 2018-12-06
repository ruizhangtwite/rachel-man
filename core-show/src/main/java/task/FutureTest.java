package task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

public class FutureTest {


    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 20, 1000, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>());
    private static Logger logger = LoggerFactory.getLogger(FutureTest.class);


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        logger.info("now");
        FutureTask<Integer> task1 = new FutureTask<>(() -> {
            Thread.sleep(1000);
            return 1;
        });

        executor.execute(task1);

        FutureTask<Integer> task2 = new FutureTask<>(() -> {
            Thread.sleep(2000);
            return 2;
        });

        executor.execute(task2);

        Integer i = task1.get();
        logger.info(String.valueOf(i.intValue()));

        Integer b = task2.get();
        logger.info(String.valueOf(b));

        executor.shutdown();
    }
}
