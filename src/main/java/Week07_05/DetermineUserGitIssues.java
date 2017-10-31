/* 
 * Semester: A171
 * Course: STIW3054
 * Group: A
 * Task: Issue No.5, Week 7
 * Matric Num: 240448
 * Name: Lim Siang Yee
 *
 */

/* References
 *  1) JavaDoc for GitHub API: http://github-api.kohsuke.org/apidocs/index.html
 *  2) Iterable interface usage: https://stackoverflow.com/questions/1059127/what-is-the-iterable-interface-used-for
*/

package Week07_05;

import java.io.IOException;
import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHIssueState;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.PagedIterable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class DetermineUserGitIssues {
    
    public static void main (String[] args) throws IOException{
        
        int i=1, x=1, y=0;
        ArrayList<String> openTitle = new ArrayList<>();
        ArrayList<String> closedTitle = new ArrayList<>();
        ArrayList<Integer> totalIssueForRepo = new ArrayList<>();
        
        GitHub github = GitHub.connectAnonymously();
        GHUser user = github.getUser("zhamri"); //Change parameter to any valid user ID
        String readableName = user.getName();
        
        int repoCount = user.getPublicRepoCount();
        Map<String,GHRepository> map = user.getRepositories();  
        Set repoNames = map.keySet();
        Object[] names = repoNames.toArray();
        
        for (int n=0; n<repoCount; n++){
            int issueNum=0;
            GHRepository repo = user.getRepository(names[n].toString());
            
            if (repo.hasIssues()){
                PagedIterable<GHIssue> openIssues = repo.listIssues(GHIssueState.OPEN);
                PagedIterable<GHIssue> closedIssues = repo.listIssues(GHIssueState.CLOSED);
                
                for (GHIssue issue: openIssues){
                    openTitle.add(issue.getTitle());
                    issueNum++;
                }
                
                for (GHIssue issue: closedIssues){
                    closedTitle.add(issue.getTitle());
                    issueNum++;
                }
            }
            totalIssueForRepo.add(issueNum);
            System.out.println("Total issue(s) for repository " +names[n] +" is: " +totalIssueForRepo.get(n));
        }

        System.out.println("\nBelow are all the OPEN issues for user " +readableName +":");
        for (String title: openTitle){
            System.out.println(i +") " +title);
            i++;
        }
        
        System.out.println("\nBelow are all the CLOSED issues for user " +readableName +":");
        for (String title: closedTitle){
            System.out.println(x +") " +title);
            x++;
        } 
    } 
}
