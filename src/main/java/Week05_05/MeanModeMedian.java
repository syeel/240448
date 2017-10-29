/* 
 * Semester: A171
 * Course: STIW3054
 * Group: A
 * Task: Issue No.5, Week 5
 * Matric Num: 240448
 * Name: Lim Siang Yee
 *
 */

package Week05_05;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class MeanModeMedian extends Thread{

    public static void main(String[] args) {
        ArrayList<Integer> randomNums = new ArrayList<>();
 
        // Generating 10k numbers between range 1 - 10000 (both inclusive)
        for(int i =0; i < 10000; i++){
            randomNums.add((int)(Math.round(Math.random()*10000)));
        }
        
        new Thread("Find_Mean") {
            double sum = 0.0, mean = 0.0;

            @Override
            public void run() {
                for (int i=0; i<randomNums.size();i++) {
                    sum += randomNums.get(i);
                }
                mean = sum / (double) randomNums.size();
                System.out.printf("Mean = %.2f %n", mean);
            }
        }.start();
        
        new Thread("Find_Mode") {
            int mod = 0;
            int maxValue, maxCount;

            @Override
            public void run() {
                for (int i = 0; i < randomNums.size(); ++i) {
                    int count = 0;
                    for (int j = 0; j < randomNums.size(); ++j) {
                        if (Objects.equals(randomNums.get(j), randomNums.get(i))){
                            ++count;
                        }
                    }
                    if (count > maxCount) {
                        maxCount = count;
                        maxValue = randomNums.get(i);
                    }
                }
                System.out.println("Mode = " + maxValue);
            }
        }.start();
        
        new Thread("Find_Median") {
            int median = 0;

            @Override
            public void run() {
                Collections.sort(randomNums);
                int middle = randomNums.size()/2;
                if (randomNums.size()%2 == 0) {
                    median = (int)(((randomNums.get(middle-1)) + (randomNums.get(middle)))/2.0);
                } else {
                    median = randomNums.get(middle);
                }
                System.out.println("Median = " + median);
            }
        }.start();
    }
}