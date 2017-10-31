/* 
 * Semester: A171
 * Course: STIW3054
 * Group: A
 * Task: Issue No.4, Week 7
 * Matric Num: 240448
 * Name: Lim Siang Yee
 *
 */

/* References
 *  1) JavaDoc for GitHub API: http://github-api.kohsuke.org/apidocs/index.html
*/

package Week07_04;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;

public class DetermineRepoContents {
    
    static ArrayList<String> files = new ArrayList<>();
    static ArrayList<String> folders = new ArrayList<>();
        
    public static void main (String[] args) throws IOException{
        
        GitHub github = GitHub.connectAnonymously();
        String userID = "zhamri"; //Change value to any valid user ID
        GHUser user = github.getUser(userID); 
        
        String repoName = "STIW3054-RT-Programming";
        GHRepository repo = user.getRepository(repoName);
        getRepoContents(repo, "");
        
        int i=1, x=1;
        System.out.println("Below are folders in repository " +repoName);
        for (String folderEntry : folders){
            System.out.println(i +") " +folderEntry);
            i++;
        }
        
        System.out.println("\nBelow are files in repository " +repoName);
        for (String fileEntry : files){
            System.out.println(x +") " +fileEntry);
            x++;
        }
    }    
    
    public static void getRepoContents(GHRepository repo, String directoryName) throws IOException{
        List<GHContent> repoContent = repo.getDirectoryContent(directoryName);
        
        for (GHContent contentEntry : repoContent) {
            if (contentEntry.isFile()) {
                files.add(contentEntry.getName());
            } else if (contentEntry.isDirectory()) {
                folders.add(contentEntry.getName());
                getRepoContents(contentEntry.getOwner(), contentEntry.getPath());
            }
        }
    }
}  