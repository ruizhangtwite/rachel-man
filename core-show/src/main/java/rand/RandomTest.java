package rand;

import java.util.Random;

public class RandomTest {

    public static void main(String[] args) {
        Random random = new Random();
        int nextInt = random.nextInt(2);
        System.out.println(nextInt);
    }
}
