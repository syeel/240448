/* 
 * Semester: A171
 * Course: STIW3054
 * Group: A
 * Task: Issue No.1, Week 2
 * Matric Num: 240448
 * Name: Lim Siang Yee
 *
 */

package Week02_01;

public class CountTo10500 extends Thread{

   int counter =0, target=51;
   
   public static void main(String[] args){
      new Thread(new CountTo10500()).start();
   }
   
   public void checkCounter(){
      if (counter == target){
         printString();
         target += 50; 
      }
   }
   
   public static void printString(){
      System.out.println("Lalala, this is a string.");
   }
  
   @Override
   public void run() {
      try {
         for (int x = 0; x <= 10500; x++) {
            System.out.println(x);
            sleep(5000);
            counter++;
            checkCounter(); 
         }
      }catch (Exception e){
         e.printStackTrace();
      }
   }
}