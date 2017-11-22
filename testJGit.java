
package cloneRepo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.apache.commons.io.FileUtils;

public class testJGit {
    public static void main(String[] args) throws GitAPIException, IOException{

        String fileSeperator = System.getProperty("file.separator");        
        String reposMainFolder = System.getProperty("user.home") + fileSeperator +"Desktop" + fileSeperator +"STIW3054-A171";
        File mainDirectory = new File(reposMainFolder);
        
        ArrayList<String> repoURIs = new ArrayList();
        ArrayList<String> matricNums = new ArrayList();
        
        // Create STIW3054-A171 folder
        if (! mainDirectory.exists()){
            mainDirectory.mkdirs();
        }
        
        // Read GithubRepositories.txt to get matric numbers and repo URI
        String filePath = mainDirectory + fileSeperator +"GithubRepositories.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                int slash = sCurrentLine.lastIndexOf('/');
                matricNums.add(sCurrentLine.substring(slash+1, slash+7));
                repoURIs.add(sCurrentLine);
            }
        }catch (IOException e) {
            System.out.println("Error, file cannot be read.");
        }

        // Create folders for every matric number
        for (int a=0; a<matricNums.size(); a++){
            String tempMat = matricNums.get(a);
            File repoDir = new File (reposMainFolder + fileSeperator +tempMat);
            if (! repoDir.exists()){
                repoDir.mkdirs();
            }
        }
        
        // Clone github repositories into folders created
        FileUtils.cleanDirectory(mainDirectory); 
        for (int a=0; a<matricNums.size(); a++){
           String tempMat = matricNums.get(a);
           String tempURI = repoURIs.get(a);
           File tempDirectoy = new File(reposMainFolder + fileSeperator + tempMat);
           
           Git git = Git.cloneRepository()
            .setURI(tempURI)
            .setDirectory(tempDirectoy)
            .setCloneAllBranches(true)
            .call(); 
        }
        
        System.out.println("Cloning completed!");
    }
    
}
