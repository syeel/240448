/* 
 * Semester: A171
 * Course: STIW3054
 * Group: A
 * Task: Issue No.3, Week 5
 * Matric Num: 240448
 * Name: Lim Siang Yee
 *
 */

package Week05_03;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ProcessTime {
    
    public static void main(String[] args) throws InterruptedException {
        ProcessTime processThreadTime = new ProcessTime();
        processThreadTime.calcNonSynchonized();
        processThreadTime.calcSynchonized();  
    }
    
    public void calcNonSynchonized() throws InterruptedException{
        long start = System.nanoTime();
        AtomicInteger nonSyncCount = new AtomicInteger(0);
        ExecutorService service = null;
        try {
            service = Executors.newFixedThreadPool(10000);
            for (int i = 0; i < 10000; i++) {
                service.submit(() -> {
                    try {
                        nonSyncCount.incrementAndGet();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        } finally {
            if (service != null) {
                service.shutdown();
                service.awaitTermination(1, TimeUnit.SECONDS);
                long time = System.nanoTime() - start;
                System.out.printf("Normal threads = %f seconds%n", time/1e9);

            }
        }
    }  
 
    public void calcSynchonized() throws InterruptedException{
        long start = System.nanoTime();
        AtomicInteger syncCount = new AtomicInteger(0);
        ExecutorService service = null;
        try {
            service = Executors.newFixedThreadPool(10000);
            synchronized(this){
                for (int i = 0; i < 10000; i++) {
                    service.submit(() -> {
                        try {
                            syncCount.incrementAndGet();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        } finally {
            if (service != null) {
                service.shutdown();
                service.awaitTermination(1, TimeUnit.SECONDS);
                long time = System.nanoTime() - start;
                System.out.printf("Synchronized threads = %f seconds%n", time/1e9);

            }
        }
    }    
}