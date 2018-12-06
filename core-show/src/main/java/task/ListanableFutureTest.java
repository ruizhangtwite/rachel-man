package task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.util.concurrent.ListenableFutureTask;
import task.domain.User;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ListanableFutureTest {

    private final static ThreadPoolExecutor EXE = new ThreadPoolExecutor(10, 20, 30, TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(), new ThreadPoolExecutor.DiscardPolicy());

    private final static Logger logger = LoggerFactory.getLogger(ListanableFutureTest.class);

    public static void main(String[] args) {
        logger.info("---开始----");

        for (int i = 0; i < 10; i++){
            ListenableFutureTask<User> futureTask = new ListenableFutureTask<User>( () -> {
                Thread.sleep(2000L);
                return new User(10L, "zhangrui");
            });


            futureTask.addCallback(new ListenableFutureCallback<User>() {
                @Override
                public void onFailure(Throwable throwable) {

                }

                @Override
                public void onSuccess(User user) {
                    logger.info(user.toString());
                }
            });

            EXE.execute(futureTask);
        }



        EXE.shutdown();
    }
}
