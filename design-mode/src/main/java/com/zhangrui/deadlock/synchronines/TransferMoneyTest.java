package com.zhangrui.deadlock.synchronines;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author zr
 * @Date 2019-05-27
 **/
public class TransferMoneyTest {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {

        DefaultTransderMoney transderMoney = new DefaultTransderMoney();

        User mike = new User("mike", 20);
        User lucy = new User("lucy", 30);

        executorService.execute(() -> {
            transderMoney.transfer(mike, lucy);
        });

        executorService.execute(() -> {
            transderMoney.transfer(lucy, mike);
        });


        executorService.shutdown();
    }

}
