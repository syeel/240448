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

public class TwoThreads{

    public static void main(String[] args) throws Exception {
        
        PrintNum pnum = new PrintNum();
        Thread pn1 = new Thread(pnum);
        pn1.start();
        
        PrintAlphab palb= new PrintAlphab();
        Thread pa1 = new Thread(palb);
        pa1.start();
    } 
}
