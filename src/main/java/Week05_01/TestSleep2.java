/* 
 * Semester: A171
 * Course: STIW3054
 * Group: A
 * Task: Issue No.1, Week 5
 * Matric Num: 240448
 * Name: Lim Siang Yee
 *
 */

package Week05_01;

public class TestSleep2 {

    volatile int status=1;
    public static void main(String[] args) {

        TestSleep2 ts2 = new TestSleep2();

        PrintOne t1 = new PrintOne(ts2);
        PrintTwo t2 = new PrintTwo(ts2);
        PrintThree t3 = new PrintThree(ts2);

        t1.start();
        t2.start();
        t3.start();
    }
}

class PrintOne extends Thread{
    TestSleep2 ts2;

    PrintOne(TestSleep2 ts2){
        this.ts2 = ts2;
    }

    @Override
    public void run() {
        try{
            synchronized (ts2) {
                for (int i = 0; i < 3; i++) {
                    while(ts2.status!=1){
                        ts2.wait();
                    }
                    System.out.println("One");
                    ts2.status = 2;
                    ts2.notifyAll();
                }
            }
        }catch (Exception e) {
            System.out.println("Exception 1 :"+e.getMessage());
        }
    }
}

class PrintTwo extends Thread{
    TestSleep2 ts2;

    PrintTwo(TestSleep2 ts2){
        this.ts2 = ts2;
    }

    @Override
    public void run() {
        try{
            synchronized (ts2) {
                for (int i = 0; i < 3; i++) {
                    while(ts2.status!=2){
                        ts2.wait();
                    }
                    System.out.println("Two");
                    ts2.status = 3;
                    ts2.notifyAll();
                }
            }
        }catch (Exception e) {
            System.out.println("Exception 1 :"+e.getMessage());
        }
    }
}

class PrintThree extends Thread{
    TestSleep2 ts2;

    PrintThree(TestSleep2 ts2){
        this.ts2 = ts2;
    }

    @Override
    public void run() {
        try{
            synchronized (ts2) {
                for (int i = 0; i < 3; i++) {
                    while(ts2.status!=3){
                        ts2.wait();
                    }
                    System.out.println("Three");
                    System.out.println("----------");
                    ts2.status = 1;
                    ts2.notifyAll();
                }
            }
        }catch (Exception e) {
            System.out.println("Exception 1 :"+e.getMessage());
        }
    }
}