/* 
 * Semester: A171
 * Course: STIW3054
 * Group: A
 * Task: Issue No.2, Week 7
 * Matric Num: 240448
 * Name: Lim Siang Yee
 *
 */

package Week07_02;

import java.io.File;
import java.util.ArrayList;

public class CountDotClass {
    public static void main(String[] args){
        String workingDirectory, absolutePath;
        File rootFolder;
        ArrayList<String> rootFileContents;
        ArrayList<String> classNames = new ArrayList<>();
        
        workingDirectory = System.getProperty("user.dir");
        rootFolder = new File(workingDirectory);
        rootFileContents = getRootContent(rootFolder); 
        
        absolutePath = determineIfIsMaven(rootFileContents, workingDirectory);

        classNames = getProjectClasses(absolutePath, classNames);
        
        System.out.println("Total number of class files are: " +classNames.size());
        System.out.println("\nBelow are all the class files for this project: ");
        for (int i=0; i< classNames.size(); i++){
            System.out.println(classNames.get(i));
        }   
    }
    
    public static String determineIfIsMaven(ArrayList<String> rootContents, String workingDir){
        boolean isMaven = false;
        String fileSeperator = System.getProperty("file.separator"), absolutePath="";
        
        for (int i=0; i<rootContents.size(); i++){
            if (rootContents.get(i).equals("pom.xml")){
                isMaven = true;
            }
        }
        
        if (isMaven == false){
            absolutePath = workingDir + fileSeperator + "build" + fileSeperator + "classes";
        }else{
            absolutePath = workingDir + fileSeperator + "target" + fileSeperator + "classes";
        }
        return absolutePath;
    }
    
    public static ArrayList<String> getRootContent(File rootFolder){
        ArrayList<String> rootFileContents = new ArrayList();
        for (final File fileEntry : rootFolder.listFiles()) {
            rootFileContents.add((fileEntry.getName())); 
        } 
        return rootFileContents;
    }
    
    public static ArrayList<String> getProjectClasses(String directoryName, ArrayList<String> files){
        File directory = new File(directoryName);
        File[] fList = directory.listFiles(); // get all the files from a directory
        
        for (File fileEntry : fList) {
            if (fileEntry.isFile() && fileEntry.getName().contains(".class")) {
                files.add(fileEntry.getName());
            } else if (fileEntry.isDirectory()) {
                getProjectClasses(fileEntry.getAbsolutePath(), files);
            }
        }
        return files;
    } 
}
