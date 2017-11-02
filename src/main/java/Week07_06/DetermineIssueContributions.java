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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
import org.kohsuke.github.GHPersonSet;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.PagedIterable;

public class DetermineIssueContributions {
    public static void main (String[] args) throws IOException{
        
        Map<String, Integer> individualContribution = new HashMap<>();
        Map<String, Integer> sortedContribution = new LinkedHashMap<>();
        ArrayList<String> inactiveUsers = new ArrayList<>();
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String dateTime = dateFormat.format(date);
        
        //Insert your github id into first parameter and password into the second
        GitHub github = GitHub.connectUsingPassword("syeel", "8384534aA*#"); 
        String gitId = "zhamri"; //Change value to any valid user ID you want to search
        GHUser user = github.getUser(gitId);
        GHPersonSet<GHUser> lectFollowing = user.getFollows();
        
        int repoCount = user.getPublicRepoCount();
        Map<String,GHRepository> map = user.getRepositories();  
        Set names = map.keySet();
        Object[] repoNames = names.toArray();
        
        System.out.println("\nGetting issues' contributors information for all issues on account with user ID: " +gitId +" ......");
        System.out.println("Please allow at least 45 seconds for the operation to complete......");
        
        individualContribution = getContributions(gitId, user, repoCount, repoNames);
        sortedContribution = sortByValue(individualContribution);
        inactiveUsers = getInactive(lectFollowing, sortedContribution);
        
        System.out.println("\nOperation completed, thank you for your patient! \nBelow are the results as of " +dateTime +"\n");
        
        int a=1;
        Iterator it = sortedContribution.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(a +". " +pair.getKey() +" (" +pair.getValue() +")");
            it.remove(); // avoids a ConcurrentModificationException
            a++;
        }
        
        System.out.println("\nBelow are users who didn't have any contribution on issues: ");        
        for (int b=0; b<inactiveUsers.size(); b++){
            System.out.println(b+1 +". " +inactiveUsers.get(b));
        }
    }
    
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
        
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            @Override
            // Put o1, then o2 for ascending sort
            public int compare(Map.Entry<K, V> o2, Map.Entry<K, V> o1) { 
                return (o1.getValue()).compareTo( o2.getValue() );
            }
        });

        Map<K, V> sortedResult = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            sortedResult.put(entry.getKey(), entry.getValue());
        }
        return sortedResult;
    }
    
    public static Map<String, Integer> getContributions(String gitId, GHUser user, int repoCount, Object[] repoNames) throws IOException{
        Map<String, Integer> individualContribution = new HashMap<>();
        
        for (int n=0; n<repoCount; n++){
            GHRepository repo = user.getRepository(repoNames[n].toString());
            
            if (repo.hasIssues()){
                PagedIterable<GHIssue> issues = repo.listIssues(GHIssueState.ALL);
                
                for (GHIssue issue: issues){
                    if(issue.getCommentsCount() > 0){
                        PagedIterable<GHIssueComment> commentsForIssue = issue.listComments();
                        
                        for (GHIssueComment comment: commentsForIssue){
                            String contributorId = comment.getUser().getLogin();
                            String excludeFromContributors = gitId;
                            
                            if(!contributorId.equals(excludeFromContributors)){
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
        }
        return individualContribution; 
    }
    
    public static ArrayList<String> getInactive(GHPersonSet<GHUser> students, Map sortedContributions){
        ArrayList<String> lectFollowing = new ArrayList<>();
        ArrayList<String> inactiveUsers = new ArrayList<>();
        
        for (GHUser user: students){
            lectFollowing.add(user.getLogin());
        }
        
        for (String user: lectFollowing){
            if(!sortedContributions.containsKey(user)) {
                inactiveUsers.add(user);
            } 
        }
        return inactiveUsers;
    }
}
