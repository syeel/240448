/* 
 * Semester: A171
 * Course: STIW3054
 * Group: A
 * Task: Issue No.4, Week 5
 * Matric Num: 240448
 * Name: Lim Siang Yee
 *
 */

package Week05_04;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CompareSequencialAndConcurrent {

    public static void main(String[] args) throws InterruptedException {
        
        CompareSequencialAndConcurrent countTime = new CompareSequencialAndConcurrent();
        ArrayList<Integer> numList = new ArrayList<>();
        ArrayList<Integer> shuffledNumList = new ArrayList<>(Collections.nCopies(1000000, 0));
        
        for (int i=1; i<=1000000; i++) {
            numList.add(i);
        }
        Collections.shuffle(numList);
        Collections.copy(shuffledNumList, numList);
        
        countTime.getSequencialTime(shuffledNumList);
        countTime.getConcurrentTime(shuffledNumList);
    }
    
    
    public void getSequencialTime(ArrayList<Integer> list){
        
        long sequencialStart = System.nanoTime();
        int max = Integer.MIN_VALUE;
        
        for(int i=0; i<list.size(); i++){
            if(list.get(i) > max){
                max = list.get(i);
            }
        }
        long sequencialTime = System.nanoTime() - sequencialStart;
        System.out.printf("Sequencial program = %f seconds%n", sequencialTime/1e9);
    }
    
    
    public void getConcurrentTime(ArrayList<Integer> numbers) throws InterruptedException {
        
        ExecutorService executor = Executors.newFixedThreadPool(100);
        Worker[] workers = new Worker[100];
        long concurrentStart = System.nanoTime();
        
        try {
            int range = numbers.size() / 100;
            for (int index = 0; index < 100; index++) {
                int startAt = index * range;
                int endAt = startAt + range;
                
                workers[index] = new Worker(startAt, endAt, numbers);
            }
        } finally {
            if (executor != null) {
                executor.shutdown();
                executor.awaitTermination(1, TimeUnit.SECONDS);
                long time = System.nanoTime() - concurrentStart;
                System.out.printf("Concurrent program = %f seconds%n", time/1e9);
            }
        }
    }
    

    public class Worker implements Callable<Integer> {

        private int startAt;
        private int endAt;
        private ArrayList<Integer> numbers;

        public Worker(int startAt, int endAt, ArrayList<Integer> numbers) {
            this.startAt = startAt;
            this.endAt = endAt;
            this.numbers = numbers;
        }

        @Override
        public Integer call() throws Exception {
            int max = Integer.MIN_VALUE;
            for (int index = startAt; index < endAt; index++) {
                max = Math.max(numbers.get(index), max);
            }
            return max;
        }
    }
}