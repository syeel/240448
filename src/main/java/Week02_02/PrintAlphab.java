/* 
 * Semester: A171
 * Course: STIW3054
 * Group: A
 * Task: Issue No.2, Week 2
 * Matric Num: 240448
 * Name: Lim Siang Yee
 *
 */

package Week02_02;

public class PrintAlphab implements Runnable{

    @Override
    public void run() {
        for (int x=0; x<5; x++){
            System.out.print("A");
        }
        System.out.print(" ");
    }
}
