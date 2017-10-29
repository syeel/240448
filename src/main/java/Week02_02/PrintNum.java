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

public class PrintNum implements Runnable{
    
    @Override
    public void run() {
        try {
            for (int x=1; x<=10; x++){
                System.out.print(x +" ");
                if (x==5){
                    Thread.sleep(100);
                }         
            }  
        }catch (Exception e){
            e.printStackTrace();
        }  
    }
}