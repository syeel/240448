/* 
 * Semester: A171
 * Course: STIW3054
 * Group: A
 * Task: Issue No.1, Week 4
 * Matric Num: 240448
 * Name: Lim Siang Yee
 *
 */

package Week04_01;

public class CompileAndRun {

    public static void main(String[] args) throws Exception {
        
        String cmd1 = "javac -d . HelloWorld.java";
        String cmd2 = "java Test_files.Issue_01.HelloWorld";
        
        PBuilder pbuild = new PBuilder(cmd1);
        Thread pb1 = new Thread(pbuild);
        pb1.start();
        pb1.join(); 
        
        pbuild = new PBuilder(cmd2);
        Thread pb2 = new Thread(pbuild);
        pb2.start();
        pb2.join();

        System.out.println("Application completed.");
    }
}
