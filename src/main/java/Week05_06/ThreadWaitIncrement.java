/* 
 * Semester: A171
 * Course: STIW3054
 * Group: A
 * Task: Issue No.3, Week 5
 * Matric Num: 240448
 * Name: Lim Siang Yee
 *
 */

package Week05_06;

import java.util.ArrayList;
import java.util.Random;

public class ThreadWaitIncrement {

    volatile int status=1, currentIndex=0;
    static ArrayList<Integer> randomNums = new ArrayList<>();
    
    volatile int currentT1Value=0, currentT2Value=0, totalT1Value=0, totalT2Value=0;
    volatile int t1Count=0, t2Count=0;

    public static void main(String[] args) {
        
        int min = 500, max = 3000;
        Random rn = new Random();
        for (int i=0; i<1000; i++){
            randomNums.add(rn.nextInt(max - min + 1) + min);
        }
        
        ThreadWaitIncrement twi = new ThreadWaitIncrement();

        ThreadOne t1 = new ThreadOne(twi, randomNums);
        ThreadTwo t2 = new ThreadTwo(twi, randomNums);

        t1.start();
        t2.start();
    }
}

class ThreadOne extends Thread{
    ThreadWaitIncrement twi;
    ArrayList<Integer> randomNums;

    ThreadOne(ThreadWaitIncrement twi, ArrayList<Integer> randomNums){
        this.twi = twi;
        this.randomNums = randomNums;
    }

    @Override
    public void run() {
        try{
            synchronized (twi) {
                for (int i=0; i<1000; i++) {
                    while(twi.status!=1){
                        twi.wait();
                    }
                    twi.currentT1Value = randomNums.get(twi.currentIndex);
                    twi.totalT1Value = twi.totalT1Value + twi.currentT1Value;
                    if (twi.totalT1Value > twi.totalT2Value){
                        twi.t2Count++;
                    }
                    if (i == 999){
                        System.out.println("Thread-1 = " +twi.t1Count);
                    }
                    if (twi.currentIndex != 999){
                        twi.currentIndex++;
                    }
                    twi.status = 2;
                    twi.notifyAll();
                }
            }
        }catch (Exception e) {
            System.out.println("Exception t1 :"+e.toString());
        }
    }
}

class ThreadTwo extends Thread{
    ThreadWaitIncrement twi;
    ArrayList<Integer> randomNums;

    ThreadTwo(ThreadWaitIncrement twi, ArrayList<Integer> randomNums){
        this.twi = twi;
        this.randomNums = randomNums;
    }

    @Override
    public void run() {
        try{
            synchronized (twi) {
                for (int i=0; i<1000; i++) {
                    while(twi.status!=2){
                        twi.wait();
                    }
                    twi.currentT2Value = randomNums.get(twi.currentIndex);
                    twi.totalT2Value = twi.totalT2Value + twi.currentT2Value;
                    if (twi.totalT2Value > twi.totalT1Value){
                        twi.t1Count++;
                    }
                    if (i == 999){
                        System.out.println("Thread-2 = " +twi.t2Count);
                        System.out.println("Total = " +(twi.t1Count + twi.t2Count));
                    }
                    if (twi.currentIndex != 999){
                        twi.currentIndex++;
                    }
                    twi.status = 1;
                    twi.notifyAll();
                }
            }
        }catch (Exception e) {
            System.out.println("Exception t2 :"+e.toString());
        }
    }
}
