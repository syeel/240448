/* 
 * Semester: A171
 * Course: STIW3054
 * Group: A
 * Task: Issue No.6, Week 7
 * Matric Num: 240448
 * Name: Lim Siang Yee
 *
 */

/* References
 *  1) JavaDoc for GitHub API: http://github-api.kohsuke.org/apidocs/index.html
 *  2) Update Map value based on key: https://stackoverflow.com/a/22156743
 *  3) Sort a Map<Key, Value> by values: https://stackoverflow.com/a/2581754
 *  4) Iterate through a Map: https://stackoverflow.com/a/1066603
*/

package Week07_06;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHIssueComment;
import org.kohsuke.github.GHIssueState;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.PagedIterable;

public class DetermineIssueContributions {
    public static void main (String[] args) throws IOException{
        
        Map<String, Integer> individualContribution = new HashMap<String, Integer>();
        Map<String, Integer> sortedContribution = new LinkedHashMap<String, Integer>();
        
        //Insert your github id into first parameter and password into the second
        GitHub github = GitHub.connectUsingPassword("", ""); 
        String gitId = "zhamri"; //Change value to any valid user ID you want to search
        GHUser user = github.getUser(gitId); 
        
        int repoCount = user.getPublicRepoCount();
        Map<String,GHRepository> map = user.getRepositories();  
        Set repoNames = map.keySet();
        Object[] names = repoNames.toArray();
        
        System.out.println("\nGetting issues' contributors information for all issues on account with user ID: " +gitId +" ......");
        System.out.println("Please allow at least 45 seconds for the operation to complete......");
        
        for (int n=0; n<repoCount; n++){
            GHRepository repo = user.getRepository(names[n].toString());
            
            if (repo.hasIssues()){
                PagedIterable<GHIssue> issues = repo.listIssues(GHIssueState.ALL);
                
                for (GHIssue issue: issues){
                    if(issue.getCommentsCount() > 0){
                        PagedIterable<GHIssueComment> commentsForIssue = issue.listComments();
                        
                        for (GHIssueComment comment: commentsForIssue){
                            String contributorId = comment.getUser().getLogin();
                        
                            if(!individualContribution.containsKey(contributorId)) {
                                individualContribution.put(contributorId,1);
                            }else {
                                individualContribution.put(contributorId, individualContribution.get(contributorId)+1);
                            }
                        }
                    }
                }
            }
        }

        sortedContribution = sortByValue(individualContribution);
        System.out.println("\nOperation completed, thank you for your patient! \nBelow are the result: \n");
        
        int a=1;
        Iterator it = sortedContribution.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(a +". " +pair.getKey() +" (" +pair.getValue() +")");
            it.remove(); // avoids a ConcurrentModificationException
            a++;
        }
        
    }
    
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        
        List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
        
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> o2, Map.Entry<K, V> o1) { // Put o1, then o2 for ascending sort
                return (o1.getValue()).compareTo( o2.getValue() );
            }
        });

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }  
}
