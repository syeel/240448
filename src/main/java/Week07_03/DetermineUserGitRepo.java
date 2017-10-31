/* 
 * Semester: A171
 * Course: STIW3054
 * Group: A
 * Task: Issue No.3, Week 7
 * Matric Num: 240448
 * Name: Lim Siang Yee
 *
 */

/* References
 *  1) JavaDoc for GitHub API: http://github-api.kohsuke.org/apidocs/index.html
 *  2) Map interface: https://docs.oracle.com/javase/1.5.0/docs/api/java/util/Map.html#get(java.lang.Object)
*/
package Week07_03;

import java.io.IOException;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;
import java.util.Map;
import java.util.Set;


public class DetermineUserGitRepo {
    public static void main (String[] args) throws IOException{
        
        GitHub github = GitHub.connectAnonymously();
        GHUser user = github.getUser("zhamri"); //Change parameter to any user ID
        String readableName = user.getName();
        
        int repoCount = user.getPublicRepoCount();
        Map<String,GHRepository> map = user.getRepositories();  
        Set repoNames = map.keySet();
        Object[] names = repoNames.toArray();
        
        System.out.println("Total repository number for " +readableName +" is: " +repoCount);
        System.out.println("\nBelow are all the user's repositories:");
        for (int i=0; i<repoCount; i++){
            System.out.println((i+1) +") " +names[i]);
        } 
    }
}