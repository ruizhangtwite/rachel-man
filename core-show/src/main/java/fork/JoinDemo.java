package fork;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * 实例演示Fork/Join框架的使用
 * 计算1-100的和
 */
public class JoinDemo {

    private static final int SIZE = 2;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool pool = new ForkJoinPool();
        Integer[] data = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 45};

        MyTask myTask = new MyTask(data);
        ForkJoinTask<Integer> resultTask = pool.submit(myTask);
        System.out.println(resultTask.get());
        pool.shutdown();
    }


    static class MyTask extends RecursiveTask<Integer> {

        private Integer[] data;

        private Integer sum = 0;

        public MyTask(Integer[] data) {
            this.data = data;
        }

        @Override
        protected Integer compute() {

            if (data.length <= SIZE) {
                if (data.length == 0) return 0;
                for (Integer d : data) {
                    sum += d;
                }
            } else {
                int len = data.length / 2;
                Integer[] leftData = Arrays.copyOfRange(data, 0, len);
                Integer[] rightData = Arrays.copyOfRange(data, len, data.length);
                MyTask leftTask = new MyTask(leftData);
                MyTask rightTask = new MyTask(rightData);

                leftTask.fork();
                rightTask.fork();

                //invokeAll(leftTask, rightTask); //等价于Fork/Join

                sum += leftTask.join() + rightTask.join();
            }
            return sum;
        }
    }

}
