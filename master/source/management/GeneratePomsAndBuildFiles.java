package management;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static management.MakeLabs.*;
/**
 * Program to create the pom.xml and build.xml for exercises.
 */
public class GeneratePomsAndBuildFiles {
	/*
	 * symbols:  
	 * @ --> only in solution
	 * $ --> only in exercise
	 * # --> in both
	 */
	public static void main(String... args){
		new GeneratePomsAndBuildFiles().generate();
	}
	private void generate() {
		for (String project : demos) {
			generate(project);
		}
		for (String project : exercises) {
			generate(project);
		}
		
	}
	private String[] forSharedLib = new String[] { "junit-4.4.jar", "easymock-2.4.jar", "servlet-api-2.4.jar"};

	/**The demos. -- same as those in makelabs*/
	private static String[] demos = MakeLabs.demos;
	
	private static List<String> DEMO_LIST = Arrays.asList(demos);
	
	/**The exercises. The solutions will be the <ex**>_solution*/
	private static  String[] exercises = MakeLabs.exercises;
	
	private static List<String> EXERCISE_LIST = Arrays.asList(exercises);
	
	private String here;

	public List<String> sharedJars = Arrays.asList(forSharedLib);
	private Map<String, String> dependencyMap = new HashMap<String, String>();
	private Map<String, String> descriptionMap = new HashMap<String, String>();
	private Map<String, String> localRepositoryMap = new HashMap<String, String>();

	private String WORKSPACE = MakeLabs.workspace;
	private void generate(String project) {
			String exName = project;//will be a loop
			createDependenciesTable();
			createDescriptionsMap();
			createLocalRepositoryMap();
			String jarListFileLocation = WORKSPACE  + project+"\\WebContent\\WEB-INF\\lib\\";
			createjarList(jarListFileLocation);
			
			File jarListFile = new File(jarListFileLocation+"jars.txt");
			File file = new File("");
			here = file.getAbsolutePath();
			File toFile = new File(here+"\\"+exName+"to"+exName+"_pom.xml");
			try {
				BufferedReader reader = new BufferedReader(new FileReader(jarListFile));
				PrintWriter writer = new PrintWriter(new FileWriter(toFile));
				//topMatter
				
				writer.println("<project xmlns='http://maven.apache.org/POM/4.0.0' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' " +
						"xsi:schemaLocation='http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd'>" +
						"<modelVersion>4.0.0</modelVersion> " +
						"<repositories>" +
						"<repository>" +
						"<id>jboss</id>" +
						"<name>JBoss</name>" +
						"<layout>default</layout>" +
						"<url>http://repository.jboss.com/maven2</url>" +
						"<snapshots><enabled>false</enabled></snapshots>" +
						"</repository>" +
						"</repositories>"+
						"\n<!-- The Basics -->\n" +
						"<groupId>ltree.crs937</groupId>" +
						"<artifactId>"+exName+"</artifactId>" + //need an if here
						"<packaging>war</packaging>" +
						"<version>1.0</version>" +
						"<name>"+exName+"</name>" + //need an if here
						"<url>http://localhost:9090</url>" +
						"<description>"+descriptionMap.get(exName)+"</description>"+
						"<developers><developer>" +
						"<name>crs937 team</name><organization>Learning Tree International</organization>" +
						"</developer></developers>"+
						"<dependencies>"
						);
				ArrayList<String> jarList = new ArrayList<String>();
				String line;
				while ((line = reader.readLine()) != null) {
					String[] tokens = line.split(" ");
					String possibleJar = tokens[tokens.length-1];
					if(possibleJar.endsWith("jar")){
						//System.out.println(possibleJar+" "+dependencyMap.get(possibleJar));
					jarList.add(possibleJar); //for later use
					if(dependencyMap.get(possibleJar)==null){
						System.out.println("Warning: "+possibleJar +" is not mapped in dependencyMap");
						continue;
					}
					writer.println(dependencyMap.get(possibleJar));
					}
				}
				for (String jarName : sharedJars) {
					if(dependencyMap.get(jarName)==null){
						System.out.println("Warning: "+jarName +" is not mapped in dependencyMap");
						continue;
					}
					writer.println(dependencyMap.get(jarName));
				}

				writer.println("</dependencies>");
				writer.println("\n<!-- Build Settings -->\n" +
						"<build>" +
						"<defaultGoal>package</defaultGoal>" +
						"<outputDirectory>${basedir}/WebContent/WEB-INF/classes</outputDirectory>" +
						"<directory>${basedir}/target</directory>" +
						"<sourceDirectory>${basedir}/src</sourceDirectory>" +
						"<plugins>" +
						"<plugin>" +
						"<groupId>org.apache.maven.plugins</groupId>" +
						"<artifactId>maven-compiler-plugin</artifactId>" +
						"<configuration><source>1.5</source><target>1.5</target></configuration>" +
						"</plugin>" +
						"<plugin>" +
						"<artifactId>maven-antrun-plugin</artifactId>" +
						"<executions>" +
						"<execution>" +
						"<phase>compile</phase>" +
						"<goals><goal>run</goal></goals>" +
						"<configuration><tasks>");
				for (String jarName : jarList) {
					writer.println("<copy " +
							"file='"+localRepositoryMap.get(jarName)+
							"' todir='${basedir}/WebContent/WEB-INF/lib' />");
					if(localRepositoryMap.get(jarName)==null){
							System.out.println("Warning: "+jarName +" is not mapped in localRepositoryMap");
					}
				}
				for (String jarName : sharedJars) {
					writer.println("<copy " +
							"file='"+localRepositoryMap.get(jarName)+
							"' todir='${basedir}/../lib' />");
					if(localRepositoryMap.get(jarName)==null){
						System.out.println("Warning: "+jarName +" is not mapped in localRepositoryMap");
					}

				}

				writer.println("</tasks>" +
						"</configuration>" +
						"</execution>" +
						"</executions>" +
						"</plugin>" +
						"<plugin>" +
						"<groupId>org.apache.maven.plugins</groupId>" +
						"<artifactId>maven-war-plugin</artifactId>" +
						"<version>2.0</version>" +
						"<configuration>" +
						"<webappDirectory>${basedir}/WebContent</webappDirectory>" +
						"</configuration></plugin>" +
						"<plugin>" +
						"<groupId>org.apache.maven.plugins</groupId>" +
						"<artifactId>maven-surefire-plugin</artifactId>" +
						"<configuration>" +
						"<classesDirectory>${basedir}/WebContent/WEB-INF/classes</classesDirectory>" +
						"<testClassesDirectory>${basedir}/WebContent/WEB-INF/classes</testClassesDirectory>" +
						"<testSourceDirectory>${basedir}/src</testSourceDirectory>" +
						"</configuration>" +
						"</plugin>" +
						"</plugins>" +
						"</build>");
				writer.println("\n<!-- Reporting -->\n" +
						"<reporting>" +
						"<plugins>" +
						"<plugin>" +
						"<groupId>org.apache.maven.plugins</groupId>" +
						"<artifactId>maven-surefire-report-plugin</artifactId>" +
						"<version>2.4.2</version>" +
						"</plugin>" +
						"</plugins>" +
						"</reporting>"
);
				writer.print("</project>");
				reader.close();
				writer.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	private void createjarList(String libFolderName) {
		File exerciseJarsFolder = new File(libFolderName);
		String[] jarFiles = exerciseJarsFolder.list();
		File jarListFile = new File(libFolderName+"/jars.txt");
		System.out.println("\n"+jarListFile.getAbsolutePath());

//		BufferedReader reader = new BufferedReader(new FileReader(jarListFile));
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(jarListFile));
			for (String jarFile : jarFiles) {
				System.out.println(jarFile);
				writer.write(jarFile+"\n");
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// TODO Auto-generated method stub
		
	}
	private void createLocalRepositoryMap() {
		String baseLocation = "${user.home}/.m2/repository/";
//		localRepositoryMap.put("antlrworks-1.2.1.jar", "anon");
		localRepositoryMap.put("antlr-2.7.2.jar", baseLocation+"antlr/antlr/2.7.2/antlr-2.7.2.jar");
//		localRepositoryMap.put("antlrworks-1.2.1.jar", baseLocation+"antlr/antlr/2.7.2/antlr-2.7.2.jar");
		localRepositoryMap.put("commons-collections-3.2.1.jar", baseLocation+"commons-collections/commons-collections/3.2.1/commons-collections-3.2.1.jar");
		localRepositoryMap.put("commons-fileupload-1.2.1.jar", baseLocation+"commons-fileupload/commons-fileupload/1.2.1/commons-fileupload-1.2.1.jar");
		localRepositoryMap.put("commons-lang-2.3.jar", baseLocation+"commons-lang/commons-lang/2.3/commons-lang-2.3.jar");
		localRepositoryMap.put("commons-logging-1.1.1.jar", baseLocation+"commons-logging/commons-logging/1.1.1/commons-logging-1.1.1.jar");
		localRepositoryMap.put("dom4j-1.6.1.jar", baseLocation+"dom4j/dom4j/1.6.1/dom4j-1.6.1.jar");
		localRepositoryMap.put("ejb3-persistence-3.3.2.Beta1.jar", baseLocation+"org/hibernate/ejb3-persistence/3.3.2.Beta1/ejb3-persistence-3.3.2.Beta1.jar");
		
		localRepositoryMap.put("ejb3-persistence-3.3.2.GA.jar", baseLocation+"hibernate-entitymanager/ejb3-persistence/3.3.2.GA/ejb3-persistence-3.3.2.GA.jar");
		
		localRepositoryMap.put("freemarker-2.3.13.jar", baseLocation+"org/freemarker/freemarker/2.3.13/freemarker-2.3.13.jar");
		localRepositoryMap.put("hibernate-annotations-3.4.0.GA.jar", baseLocation+"org/hibernate/hibernate-annotations/3.4.0.GA/hibernate-annotations-3.4.0.GA.jar");
		localRepositoryMap.put("hibernate-commons-annotations-3.0.0.ga.jar", baseLocation+"org/hibernate/hibernate-commons-annotations/3.0.0.ga/hibernate-commons-annotations-3.0.0.ga.jar");
		localRepositoryMap.put("hibernate-core-3.3.1.GA.jar", baseLocation+"org/hibernate/hibernate-core/3.3.1.GA/hibernate-core-3.3.1.GA.jar");
		localRepositoryMap.put("hibernate-entitymanager-3.4.0.GA.jar", baseLocation+"org/hibernate/hibernate-entitymanager/3.4.0.GA/hibernate-entitymanager-3.4.0.GA.jar");
		localRepositoryMap.put("javassist-3.7.1.GA.jar", baseLocation+"javassist/javassist/3.7.1.GA/javassist-3.7.1.GA.jar");
		localRepositoryMap.put("jta-1.1.jar", baseLocation+"javax/transaction/jta/1.1/jta-1.1.jar");
		localRepositoryMap.put("log4j-1.2.15.jar", baseLocation+"log4j/log4j/1.2.15/log4j-1.2.15.jar");
		localRepositoryMap.put("ognl-2.6.11.jar", baseLocation+"opensymphony/ognl/2.6.11/ognl-2.6.11.jar");
		localRepositoryMap.put("serp-1.13.1.jar", baseLocation+"net/sourceforge/serp/serp/1.13.1/serp-1.13.1.jar");
		localRepositoryMap.put("slf4j-api-1.5.6.jar", baseLocation+"org/slf4j/slf4j-api/1.5.6/slf4j-api-1.5.6.jar");
		localRepositoryMap.put("slf4j-jdk14-1.5.6.jar", baseLocation+ "org/slf4j/slf4j-jdk14/1.5.6/slf4j-jdk14-1.5.6.jar");
		localRepositoryMap.put("spring-2.5.6.jar", baseLocation+"org/springframework/spring/2.5.6/spring-2.5.6.jar");
		localRepositoryMap.put("struts2-config-browser-plugin-2.1.6.jar", baseLocation+"org/apache/struts/struts2-config-browser-plugin/2.1.6/struts2-config-browser-plugin-2.1.6.jar");
		localRepositoryMap.put("struts2-convention-plugin-2.1.6.jar", baseLocation+"org/apache/struts/struts2-convention-plugin/2.1.6/struts2-convention-plugin-2.1.6.jar");
		localRepositoryMap.put("struts2-sitemesh-plugin-2.1.6.jar", baseLocation+"org/apache/struts/struts2-sitemesh-plugin/2.1.6/struts2-sitemesh-plugin-2.1.6.jar");

		localRepositoryMap.put("struts2-core-2.1.6.jar", baseLocation+"org/apache/struts/struts2-core/2.1.6/struts2-core-2.1.6.jar");
		localRepositoryMap.put("struts2-spring-plugin-2.1.6.jar", baseLocation+"org/apache/struts/struts2-spring-plugin/2.1.6/struts2-spring-plugin-2.1.6.jar");
		localRepositoryMap.put("xwork-2.1.2.jar", baseLocation+"com/opensymphony/xwork/2.1.2/xwork-2.1.2.jar");
		//<!-- Not in WEB-INF/lib but needed for testing -->
		localRepositoryMap.put("junit-4.4.jar", baseLocation+"junit/junit/4.4/junit-4.4.jar");
		localRepositoryMap.put("easymock-2.4.jar", baseLocation+"org/easymock/easymock/2.4/easymock-2.4.jar");
		localRepositoryMap.put("servlet-api-2.4.jar", baseLocation+"javax/servlet/servlet-api/2.4/servlet-api-2.4.jar");
	}
	private void createDependenciesTable() {
		//dependencyMap.put("antlrworks-1.2.1.jar", "<dependency><groupId>antlr</groupId><artifactId>antlr</artifactId><version>2.7.2</version></dependency>");
		//dependencyMap.put("antlr-2.7.2.jar", "<dependency><groupId>antlr</groupId><artifactId>antlr</artifactId><version>2.7.2</version></dependency>");
		dependencyMap.put("antlr-2.7.2.jar", "<dependency><groupId>antlr</groupId><artifactId>antlr</artifactId><version>2.7.2</version></dependency>");
		dependencyMap.put("commons-collections-3.2.1.jar", "<dependency><groupId>commons-collections</groupId><artifactId>commons-collections</artifactId><version>3.2.1</version></dependency>");
		dependencyMap.put("commons-fileupload-1.2.1.jar", "<dependency><groupId>commons-fileupload</groupId><artifactId>commons-fileupload</artifactId><version>1.2.1</version></dependency>");
		dependencyMap.put("commons-lang-2.3.jar", "<dependency><groupId>commons-lang</groupId><artifactId>commons-lang</artifactId><version>2.3</version></dependency>");
		dependencyMap.put("commons-logging-1.1.1.jar", "<dependency><groupId>commons-logging</groupId><artifactId>commons-logging</artifactId><version>1.1.1</version></dependency>");
		dependencyMap.put("dom4j-1.6.1.jar", "<dependency><groupId>dom4j</groupId><artifactId>dom4j</artifactId><version>1.6.1</version></dependency>");
		dependencyMap.put("ejb3-persistence-3.3.2.Beta1.jar", "<dependency><groupId>org.hibernate</groupId><artifactId>ejb3-persistence</artifactId><version>3.3.2.Beta1</version></dependency>");
		dependencyMap.put("ejb3-persistence-3.3.2.GA.jar", "<dependency><groupId>hibernate-entitymanager</groupId><artifactId>ejb3-persistence</artifactId><version>3.3.2.GA</version></dependency>");
		dependencyMap.put("freemarker-2.3.13.jar", "<dependency><groupId>org.freemarker</groupId><artifactId>freemarker</artifactId><version>2.3.13</version></dependency>");
		dependencyMap.put("hibernate-annotations-3.4.0.GA.jar", "<dependency><groupId>org.hibernate</groupId><artifactId>hibernate-annotations</artifactId><version>3.4.0.GA</version></dependency>");
		dependencyMap.put("hibernate-commons-annotations-3.0.0.ga.jar", "<dependency><groupId>org.hibernate</groupId><artifactId>hibernate-commons-annotations</artifactId><version>3.0.0.ga</version></dependency>");
		dependencyMap.put("hibernate-core-3.3.1.GA.jar", "<dependency><groupId>org.hibernate</groupId><artifactId>hibernate-core</artifactId><version>3.3.1.GA</version></dependency>");
		dependencyMap.put("hibernate-entitymanager-3.4.0.GA.jar", "<dependency><groupId>org.hibernate</groupId><artifactId>hibernate-entitymanager</artifactId><version>3.4.0.GA</version></dependency>");
		dependencyMap.put("javassist-3.7.1.GA.jar", "<dependency><groupId>javassist</groupId><artifactId>javassist</artifactId><version>3.7.1.GA</version></dependency>");
		dependencyMap.put("jta-1.1.jar", "<dependency><groupId>javax.transaction</groupId><artifactId>jta</artifactId><version>1.1</version></dependency>");
		dependencyMap.put("log4j-1.2.15.jar", "<dependency><groupId>log4j</groupId><artifactId>log4j</artifactId><version>1.2.15</version><exclusions><exclusion><groupId>javax.jms</groupId><artifactId>jms</artifactId></exclusion><exclusion><groupId>com.sun.jmx</groupId><artifactId>jmxri</artifactId></exclusion><exclusion><groupId>com.sun.jdmk</groupId><artifactId>jmxtools</artifactId></exclusion></exclusions></dependency>");
		dependencyMap.put("ognl-2.6.11.jar", "<dependency><groupId>opensymphony</groupId><artifactId>ognl</artifactId><version>2.6.11</version></dependency>");
		dependencyMap.put("serp-1.13.1.jar", "<dependency><groupId>net.sourceforge.serp</groupId><artifactId>serp</artifactId><version>1.13.1</version></dependency>");
		dependencyMap.put("slf4j-api-1.5.6.jar", "<dependency><groupId>org.slf4j</groupId><artifactId>slf4j-api</artifactId><version>1.5.6</version></dependency>  ");
		dependencyMap.put("slf4j-jdk14-1.5.6.jar", "<dependency><groupId>org.slf4j</groupId><artifactId>slf4j-jdk14</artifactId><version>1.5.6</version></dependency> ");
		dependencyMap.put("spring-2.5.6.jar", "<dependency><groupId>org.springframework</groupId><artifactId>spring</artifactId><version>2.5.6</version></dependency>");
		dependencyMap.put("struts2-config-browser-plugin-2.1.6.jar", "<dependency><groupId>org.apache.struts</groupId><artifactId>struts2-config-browser-plugin</artifactId><version>2.1.6</version></dependency>");
		dependencyMap.put("struts2-convention-plugin-2.1.6.jar", "<dependency><groupId>org.apache.struts</groupId><artifactId>struts2-convention-plugin</artifactId><version>2.1.6</version></dependency>");
		dependencyMap.put("struts2-sitemesh-plugin-2.1.6.jar", "<dependency><groupId>org.apache.struts</groupId><artifactId>struts2-sitemesh-plugin</artifactId><version>2.1.6</version></dependency>");
		
		dependencyMap.put("struts2-core-2.1.6.jar", "<dependency><groupId>org.apache.struts</groupId><artifactId>struts2-core</artifactId><version>2.1.6</version></dependency>");
		dependencyMap.put("struts2-spring-plugin-2.1.6.jar", "<dependency><groupId>org.apache.struts</groupId><artifactId>struts2-spring-plugin</artifactId><version>2.1.6</version></dependency>");
		dependencyMap.put("xwork-2.1.2.jar", "<dependency><groupId>com.opensymphony</groupId><artifactId>xwork</artifactId><version>2.1.2</version></dependency>");
		//<!-- Not in WEB-INF/lib but needed for testing -->
		dependencyMap.put("junit-4.4.jar", "<dependency><groupId>junit</groupId><artifactId>junit</artifactId><version>4.4</version></dependency>");
		dependencyMap.put("easymock-2.4.jar", "<dependency><groupId>org.easymock</groupId><artifactId>easymock</artifactId><version>2.4</version></dependency>");
		dependencyMap.put("servlet-api-2.4.jar", "<dependency><groupId>javax.servlet</groupId><artifactId>servlet-api</artifactId><version>2.4</version></dependency> ");
	}
	private void createDescriptionsMap(){
		descriptionMap.put("ex52","Running a Struts2 Project" );
	}
	
}
