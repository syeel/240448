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

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class PBuilder implements Runnable{
    
    String cmd;
    
    public PBuilder(String command){
        cmd = command;
    }
    
    @Override
    public void run(){
        builder(cmd);
    }
    
    public void builder(String cmd){
        try {
            ProcessBuilder builder = new ProcessBuilder(
                    "cmd.exe", "/c", "cd \"C:\\Users\\SiangYee\\Desktop\\STIW3054\" && " + cmd);
            builder.redirectErrorStream(true);
            Process p = builder.start();
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while (true) {
                line = r.readLine();
                if (line == null) {
                    break;
                }
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }   
}