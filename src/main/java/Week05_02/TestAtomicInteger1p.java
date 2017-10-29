/* 
 * Semester: A171
 * Course: STIW3054
 * Group: A
 * Task: Issue No.2, Week 5
 * Matric Num: 240448
 * Name: Lim Siang Yee
 *
 */

package Week05_02;

import java.util.concurrent.atomic.AtomicInteger;

public class TestAtomicInteger1p {

    volatile int status=1;
    public static void main(String[] args) throws Exception{

        Thread t1 = new Thread(new CountProblem());
        Thread t2 = new Thread(new CountProblem());

        t1.start();
        t1.join();
        
        t2.start();
        t2.join();
        
        System.out.println("Count=" + CountProblem.getCount()); 
    }
    
}
class CountProblem implements Runnable {
    private static AtomicInteger atomicCounter = new AtomicInteger(0);

    private static int count;

    public void run() {
        for (int i = 1; i <= 5; i++) {
            count = atomicCounter.incrementAndGet();
            processSomething(i);
        }   
    }

    public static int getCount() {
        return count;
    }
    
    private void processSomething(int i) {
        try {
            Thread.sleep(i * 10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}