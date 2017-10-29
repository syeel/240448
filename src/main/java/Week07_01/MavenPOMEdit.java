/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Week07_01;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.LinkedList;
import java.util.List;
import org.apache.maven.model.Dependency;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;

public class MavenPOMEdit {
    public static void main(String[] args) throws Exception {
        
        //Change baseDir to your project's root directory
        //Change all \\ in line 25, 30, 42 to / for MAC users
        String baseDir = "C:\\Users\\SiangYee\\Documents\\NetBeansProjects\\240448_Maven";
        List<Dependency> dependencies = new LinkedList<Dependency>();
            
        //Reading
        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model = reader.read(new FileInputStream(new File(baseDir, "\\pom.xml")));

        //Editing
        Dependency dep = new Dependency();
        dep.setGroupId("junit");
        dep.setArtifactId("junit");
        dep.setVersion("4.12");
        dependencies.add(dep);
        model.addDependency(dep);
      
        //Writing
        MavenXpp3Writer writer = new MavenXpp3Writer();
        writer.write(new FileOutputStream(new File(baseDir, "\\pom.xml")), model);
    }
}