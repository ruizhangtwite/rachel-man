package fork;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class ForkJoinMain {

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();

        Object o = pool.invoke(new ForkJoinTask<Object>() {

            @Override
            public Object getRawResult() {
                return "zhangrui";
            }

            @Override
            protected void setRawResult(Object value) {

            }

            @Override
            protected boolean exec() {
                return true;
            }
        });

        System.out.println(o);


        ForkJoinTask<String> task = new ForkJoinTask<String>(){

            @Override
            public String getRawResult() {
                return "你是小狗";
            }

            @Override
            protected void setRawResult(String value) {

            }

            @Override
            protected boolean exec() {
                return true;
            }
        };
        pool.execute(task);
        try {
            System.out.println(task.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        pool.shutdown();
    }
}
