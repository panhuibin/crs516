package management;

/**
 * Program to copy the various files to their exercise folders.
 * You might have to define WORKSPACE below to indicate where the files are
 * Each file is in the appropriate folder
 * If the final name is to be name then the file should be called
 * e11toe31_e11_d11tod31_d21_name
 * will be copied into ex11 to ex31
 * will be solved in ex11
 * will be copied into d11 to d31
 * will be solved in d21
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/*
 * symbols:  
 * @ --> only in solution
 * ^ --> only in reduced hints file
 * & --> only in the normal exercise file
 * $ --> only in normal exercise and reduced hints
 * # --> in both exercise and solution but not reduced hints
 * % --> hint for html file
 */
public class MakeLabs{
	
	/**
	 * Used to allow one to run it on different drives
	 */
	private static String drive;
	
	/**
	 * Course number, e.g. 516. Has to be hard coded as it is used
	 * before the property file is read.
	 */
	private static String crs="516";

	private Logger logger = LoggerFactory.getLogger(MakeLabs.class.getName());
	
	private char fileSeperator = File.separatorChar;

	public static void main(String[] args) {
		new MakeLabs().makeLabs();
	}

	private MakeLabs(){
		String here = new File("").getAbsolutePath();
		
		workspace=here.substring(0,here.lastIndexOf("\\"))+"\\";
		logger.info("Workspace is "+workspace);
		Properties props = new Properties();
		try {
			props.load(getClass().getResourceAsStream("labs.props"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		drive = new File("").getAbsolutePath();
		drive = drive.substring(0,drive.lastIndexOf("\\"));
		drive = drive.substring(0,drive.lastIndexOf("\\"));
		
		logger.info("Drive is "+ drive);
		docs = fixDrive(props.getProperty("docs"));
		if(props.get("demos")!=null){
		demos = ((String)props.get("demos")).split(",");
		}
		demoList = Arrays.asList(demos);
		docLinks = props.getProperty("documentationLinks").split(",");
		exercises=((String)props.get("exercises")).split(",");
		exerciseList = Arrays.asList(exercises);
		deletes=((String)props.get("deletes")).split(",");
		for(int i=0; i< deletes.length;i++){
			deletes[i]=workspace+deletes[i];
		}
		TOMCAT_HOME=(String)props.get("TOMCAT_HOME");
		verbatimFolders=((String)props.get("verbatimFolders")).split(",");
		verbatimExercises=((String)props.get("verbatimExercises")).split(",");
		version=(String)props.get("version");
	}
	
	/**
	 * Main method that runs the others -- really a dispatcher*/
	public void makeLabs() {
		doVersion();
		//get the paths and location
		File file = new File("");
		here = file.getAbsolutePath();
		logger.info("here is {}",here);
		process(here);
		//doVerbatim();
		doDeletes();
		doWebPages();
		System.out.println("Done!!");
		System.out.println("Remove the JMeter recent files using "
				+ "regedit HKEY_CURRENT_USER\\SoftWare\\JavaSoft\\Prefs\\org\\apache\\jmeter\\gui\\action");
	}
	
	/**
	 * Write a version file into the workspace folder.
	 */
	private void doVersion() {
		File versionFile = new File(workspace+"..\\version.txt");
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new FileWriter(versionFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		writer.println("Version: "+ version);
		writer.println("Build date: "+ new Date());
		writer.println("Chris Mawata");
		writer.close();
	}

	/**
	 *  Main processing routine. Uses recursion through the folders.
	 * At the beginning, location is 'here', the working directory. 
	 * */
	private void process(String location) {
		logger.info("-----process-----{}",location);
		/* These folders are not touched */
		if (location.contains("management") || location.contains("settings")
				|| location.contains("classes") || location.contains("CVS")
				|| location.contains("verbatim") 
				|| location.contains("Application Data")
				|| location.contains("changes")|| location.contains("bin")
				||location.contains("logs")
				|| location.contains("master\\source")){
			return;
		}

		File[] fileList = new File(location).listFiles();
		logger.info("In {} there are {} files to process", location, fileList.length);
		for (File aFile : fileList) {
			if(location.contains("src")){
				if(aFile.getName().contains("InstallFilesWithReducedHints"))
				logger.info(aFile.getName());
			}
			/* Recurse into aFile if it is a sub-folder */
			if (aFile.isDirectory()) {
				process(aFile.getAbsolutePath());
				continue;
			}

			/* Project files should not be processed */
			String fileName = aFile.getName();
			if (aFile.getName().contains(".classpath")
					|| fileName.contains(".project")) {
				continue;
			}
			/* cvs files should not be processed */
			if (aFile.getName().contains("cvs")) {
				continue;
			}

			/* Examine the filename for hints */
			//The last '_' character is the marker for the beginning of the real 
			//file name
			String[] tokens = fileName.split("_");
			String shortName = tokens[tokens.length - 1]; // actual file name in
			// exercises
			logger.debug("/n===========================/n");
			logger.debug("Processing " + shortName);
			if (tokens.length == 1) {
				
				logger.debug("There were no prefixes, just the file name");
				noPrefix(location, aFile, fileName, shortName);
				
			}

			/* Now we deal with those that have prefixes */
			for (int i = 0; i < tokens.length - 1; i++) {
				String token = tokens[i];
				if (token.contains("@")) {
					String[] subtokens = token.split("@");
					// Goes into several exercises or demos with no changes
					if (subtokens[0].startsWith("e")) {
						extoex(location, aFile, shortName, subtokens);
					}
					
					if (subtokens[0].startsWith("d")) {
						logger.debug("This will be in demos "
								+ subtokens[0] + " to " + subtokens[1]);
						demotodemo(location, aFile, shortName, subtokens);
					}
				}
				else {
					// just one demo or exercise but has todos
					if (token.startsWith("e")) {
						// an exercise
						exwithtodos(location, aFile, shortName, token);
					}
					if (token.startsWith("d")) {
						exwithtodos(location, aFile, shortName, token);
					}
				}
			}
			if (fileName.length() > 4) {
				String last4 = fileName.substring(aFile.getName().length() - 4);
				if (last4.equals("java")) {
					logger.debug("Java file" + fileName);
				} else if (last4.equals(".xml")) {
					if (fileName.contains("context")) {
						logger.debug("Context file");
					}
					logger.debug("xml file");
				} else if (last4.equals(".jsp")) {
					logger.debug("jsp file");
				}
				else {
					logger.debug("not a special file");
				}
			}
			logger.debug(""+aFile);
		}
	}



	/**Uses NIO to quickly copy files*/
	private static void copyFile(File in, File out) throws IOException {
		out.getParentFile().mkdirs();
		try (FileInputStream inStream = new FileInputStream(in);
				FileChannel inChannel = inStream.getChannel();
				FileOutputStream outStream = new FileOutputStream(out);
				FileChannel outChannel = outStream.getChannel();){
			inChannel.transferTo(0, inChannel.size(), outChannel);
		} 
	}
	
	/**
	 * Files that need to be deleted after makelabs runs.
	 */
	private String[] deletes;

	/**
	 * Names of demos as a list.
	 */
	private List<String> demoList;

	/**
	 * Names of demos.
	 */
	static String[] demos=new String[]{};
	
	
	/**
	 * path to docs folder
	 */
	static String docs;
	static String[] docLinks;
	/**
	 * Names of exercises as a list
	 */
	List<String> exerciseList;
	
	/**The exercises. The solutions will be the <ex**>_solution*/
	static String[] exercises;
	
	/**Holds the name of the current directory*/
	private String here;
	
	private String TOMCAT_HOME;

	/**
	 * Exercises that need the verbatim files (music and video gifs)
	 */
	private String[] verbatimExercises;

	/**
	 * Folders with files that need to be copied verbatim
	 */
	private String[] verbatimFolders;
	
	private String version;
	
	static String workspace;
	
	/**
	 * In the props file the drive is represented by @drive@ and we replace it
	 * here with the actual drive letter.
	 * @param property the property from the props file
	 * @return a String with @drive@ replaced by the drive letter.
	 */
	private String fixDrive(String property) {
		return property.replace("@drive@", drive);
	}

	private void doWebPages() {
		new File(docs).mkdirs();
		try(PrintWriter writer = new PrintWriter(new FileWriter(docs+"\\index.html"))) {
			String header = "Course "+crs+" Help Files";
			StringBuilder contentBuilder = new StringBuilder("<h3>Reference Documentation for Course "+crs+"</h3>");
//			contentBuilder.append("<ul>");
			contentBuilder.append("<table id=documentation>");
			for (String linkStr : docLinks) {//documentation links
				linkStr = fixDrive(linkStr);
				String[] linkParts = linkStr.split("\\|\\|");
				if(linkParts.length==2){
				contentBuilder.append("\n<tr><td>")
				.append("<a href='")
				.append(linkParts[1])
				.append("'>")
				.append(linkParts[0])
				.append("</a>")
				.append("</td></tr>");
				}
				else{
					contentBuilder.append("<tr><td class='blank'> </td></tr>");
				}
			}
			contentBuilder.append("</table>");
			String content = contentBuilder.toString();
			String sidebarTitle = "Help with Exercises"; //Menu
			ArrayList<String> linksList = new ArrayList<>();
			
			for(String demo : demoList){
				makeIndexInHintsFolders(sidebarTitle, linksList, demo);
			}
			Collections.sort(exerciseList);
			for (String exercise : exerciseList) {
				makeIndexInHintsFolders(sidebarTitle, linksList, exercise);
			}
			
			String[] links = linksList.toArray(new String[0]);
			String webPage = createWebPage(header, content, sidebarTitle, links);
			writer.println(webPage);			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private void makeIndexInHintsFolders(String sidebarTitle,
			ArrayList<String> linksList, String exercise) throws IOException {
		File hintsFolder = new File(workspace+"\\"+exercise+"\\"+"hints");
		if(hintsFolder.exists()){
			//create index.html in the hints folder
			try(PrintWriter writer2 = new PrintWriter(new FileWriter(hintsFolder.getAbsolutePath()+"\\index.html"))){
				ArrayList<String> hintsFilesList = new ArrayList<>();
				File[] hintsFiles = hintsFolder.listFiles();
				for (File file : hintsFiles) {
					if(file.getName().startsWith("index.html")||
							file.getName().startsWith("readme")){
						continue;
					}
					hintsFilesList.add("<a href='"+file.getAbsolutePath()+"'>" +
							file.getName()+"</a>");
				}
				hintsFilesList.add("<a href='"+docs+"\\index.html'>" +
						"Back"+"</a>");
				String[] hintsLinks = hintsFilesList.toArray(new String[0]);
				String exerciseHeader = "Help files for "+exercise;
				String exerciseContent = "<h2>Click on the menu link for the file you need help with.</h2>";
				String webPage = createWebPage(exerciseHeader,exerciseContent , sidebarTitle, hintsLinks);
				writer2.println(webPage);
			}
			linksList.add("<a href='"+hintsFolder.getAbsolutePath()+"\\index.html'>" +
					exercise+" hints</a>");

			//logger.debug(exercise);					
		}
	}
	
	/**
	 * Creates the final web page
	 * @return
	 */
	/*called by doWebPages()*/
	private String createWebPage(String header, String content, String sidebarTitle, String[] links){
		String finalPage =
				htmlTop
				+createHeader(header)
				+createMain(content, sidebarTitle, links)
				+htmlBottom;
		return finalPage;
	}

	private String createMain(String content, String sidebarTitle, String[] links) {
		return "<div id='main'>"
				+createContent(content)
				+createSideBar(sidebarTitle,links)
				+"</div><!-- main -->";
	}

	private String createSideBar(String sidebarTitle, String[] anchors) {
		String top = "<div id='sidebar'>"+
		"<div class='inner'>"+
		"<h3>"+sidebarTitle+"</h3>"+
		"<ul>";
		StringBuilder middleBuilder = new StringBuilder();
		for (String anchor : anchors) {
			middleBuilder.append("<li><p>");
			middleBuilder.append(anchor);
			middleBuilder.append("</p></li>");
		}
		String bottom = "</ul>"+
	"</div>"+
	"<div id='sidebar2'></div>"+
"</div>";
		return top+middleBuilder+bottom;
	}

	private String createContent(String content) {
		return "<div id='content'>"+
		"<div id='inner'>"+
				content+
		"</div><!-- inner -->"+
	"</div><!-- content -->";
	}

	private String createHeader(String header) {
		if(header == null){header = "Crs"+crs+"";}
		return "<div id='header'>"+
		"&#160;<img src='C:\\crs"+crs+"\\docs\\images\\logo.jpg' alt='logo' height='26' width='143' />"+
		"<div id='header-bottom'>"+
			"<p id='tagline'>"+header+"</p>"+
			"<ul>"+
				"<li><a href='C:\\crs"+crs+"\\docs\\index.html'>Contact Us</a> |</li>"+
				"<li><a href='C:\\crs"+crs+"\\docs\\index.html'>About Us</a> |</li>"+
				"<li><a href='C:\\crs"+crs+"\\docs\\index.html'>Privacy Policy</a> |</li>"+
			"</ul>"+
		"</div>"+
		"<!-- header-bottom -->"+
	"</div>";
	}

	/*
	 * No todos in file.
	 */
	private void demotodemo(String location, File afile, String shortName,
			String[] subtokens) {
		logger.debug("This will be in demos " + subtokens[0] + " to "
				+ subtokens[1]);
		int start = demoList.indexOf(subtokens[0]);
		int end = demoList.indexOf(subtokens[1]);
		for (int j = start; j <= end; j++) {
			/* destination */
			String destination = workspace + demoList.get(j)
					+ location.substring(here.length()) + "\\" + shortName;
			logger.debug(afile.getAbsolutePath());
			logger.debug(destination);
			File fromFile = new File(afile.getAbsolutePath());

			File toFile = new File(destination);
			toFile.getParentFile().mkdirs();
			if (afile.getName().endsWith("java")
					|| afile.getName().endsWith("xml")) {
				writeSolved(fromFile, toFile);
			} else {
				try {
					copyFile(fromFile, toFile);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	
	private void doDeletes() {
		for (String filename : deletes) {
			File toDelete = new File(filename);
			logger.debug("deleteing "+filename+" "+ toDelete.exists());
			toDelete.delete();
		}
	}

	/* Created to put the music files into ex82 and 102 */
	private void doVerbatim() {
		for (String folder : verbatimFolders) {
			File[] fileList = new File(here + "/verbatim/"+folder).listFiles();
			//String imagesDestinationFolder = "\\src\\main\\webapp\\images\\";
			String imagesDestinationFolder = "\\WebContent\\images\\";
			for (String exercise : verbatimExercises) {
				/* Create folders */
				new File(here + "\\..\\" + exercise
						+ imagesDestinationFolder+folder+"\\").mkdir();
				new File(here + "\\..\\" + "solution-"+exercise
						+imagesDestinationFolder+folder+"\\").mkdir();

				for (File afile : fileList) {
					try {
						File fromFile = new File(afile.getAbsolutePath());
						String destination = here + "\\..\\" + exercise
								+ imagesDestinationFolder+folder+"\\"
								+ afile.getName();
						File toFile = new File(destination);
						copyFile(fromFile, toFile);

						// Do the solution
						fromFile = new File(afile.getAbsolutePath());
						destination = here + "\\..\\"+"solution-" + exercise
								+imagesDestinationFolder+folder+"\\"
								+ afile.getName();
						toFile = new File(destination);
						copyFile(fromFile, toFile);
					} catch (IOException e) {
						logger.debug("Problem in doVerbatim");
						e.printStackTrace();
					}
				}
			}
		}
	}

	private void extoex(String location, File afile, String shortName,
			String[] subtokens) {
		logger.debug("This will be in exercise " + subtokens[0] + " to "
				+ subtokens[1]);
		int start = exerciseList.indexOf(subtokens[0]);
		int end = exerciseList.indexOf(subtokens[1]);
		if(start>end){
			logger.error(subtokens[1]+" should come before "+subtokens[0]+ " in "+afile.getAbsolutePath() );
			throw new IllegalStateException("Bad file name because exa@exb are our of sequence ");
		}
		for (int j = start; j <= end; j++) {
			/* destination */
			String destination = "To be set";
			try {
				destination = workspace + exerciseList.get(j)
						+ location.substring(here.length()) + "\\" + shortName;
			} catch (Exception e1) {
				System.out.println("Usually you either did not put this project in " +
						"the list of classes -- or you forgot to save the props file!");
				e1.printStackTrace();
			}
			logger.debug(afile.getAbsolutePath());
			logger.debug(destination);
			File fromFile = new File(afile.getAbsolutePath());

			File toFile = new File(destination);
			
			toFile.getParentFile().mkdirs();
			if (afile.getName().endsWith("java")
					|| afile.getName().endsWith("xml")) {
				writeSolvedNoHash(fromFile, toFile);
			} else {
				try {
					copyFile(fromFile, toFile);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			// Do the solution one
			destination = workspace +"solution-" + exerciseList.get(j)
					+ location.substring(here.length()) + "\\" + shortName;
			toFile = new File(destination);
			toFile.getParentFile().mkdirs();
			if (afile.getName().endsWith("java")
					|| afile.getName().endsWith("xml")) {
				writeSolved(fromFile, toFile);
			} else {
				try {
					copyFile(fromFile, toFile);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}

	/**
	 * An exercise with todos
	 * @param location
	 * @param afile
	 * @param shortName
	 * @param token
	 */
	private void exwithtodos(String location, File afile, String shortName,
			String token) {
		logger.debug(token + " has todos");
		String destination = workspace + token
				+ location.substring(here.length()) + "\\" + shortName;
		logger.debug(afile.getAbsolutePath());
		logger.debug("Will go to ");
		logger.debug(destination);

		File fromFile = new File(afile.getAbsolutePath());
		File toFile = new File(destination);
		toFile.getParentFile().mkdirs();
		writeNormalHints(fromFile, toFile);

//		try {
//			BufferedReader reader = new BufferedReader(new FileReader(fromFile));
//			PrintWriter writer = new PrintWriter(new FileWriter(toFile));
//			String line;
//			while ((line = reader.readLine()) != null) {
//				// solution lines start with @ and will not appear in exercise
//				if (line.startsWith("@")) {
//					continue;
//				}
//				
//				// hint lines start with % and will not appear in exercise
//				if (line.startsWith("%")) {
//					continue;
//				}
//
//				
//				// Question lines start with # and will appear in both
//				if (line.startsWith("#")) {
//					line = line.substring(1);// lope it off
//				}
//				// $ means only appear in the exercise and reduced hints and not in the solution
//				if (line.startsWith("$")) {
//					line = line.substring(1);// lope it off
//				}
//				// & means only appear in the exercise and not in the reduced hints or solution
//				if (line.startsWith("&")) {
//					line = line.substring(1);// lope it off
//				}
//				// ^ means only appear in the reduced hints
//				if (line.startsWith("^")) {
//					continue;
//				}
//
//				writer.println(line);
//
//			}
//			reader.close();
//			writer.close();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		//Do the normal hints (in store)s one
		destination = workspace +fileSeperator
				+"store"+fileSeperator
				+token+fileSeperator
				+"normal-hints"+fileSeperator+shortName;
		toFile = new File(destination);
		toFile.getParentFile().mkdirs();
		writeNormalHints(fromFile, toFile);

		//Do the reduced hints one
		destination = workspace +fileSeperator
				+"store"+fileSeperator
				+token+fileSeperator
				+"reduced-hints"+fileSeperator+shortName;
		toFile = new File(destination);
		toFile.getParentFile().mkdirs();
		writeReducedHints(fromFile, toFile);

		
		//Do the hints
		destination = workspace + token
		+ "\\hints" + "\\" + shortName+"-hints.html";
		logger.debug("hints for "+destination);
		//fromFile is the same
		toFile = new File(destination);
		if (afile.getName().endsWith("java")
				|| afile.getName().endsWith("xml")
				|| afile.getName().endsWith("jsp")
				|| afile.getName().endsWith("properties")) {
			writeHints(fromFile, toFile, shortName);
		}

		
		// Do the solution one
		destination = workspace +"solution-" + token 
				+ location.substring(here.length()) + "\\" + shortName;
		toFile = new File(destination);
		toFile.getParentFile().mkdirs();
		writeSolved(fromFile, toFile);
		

		
	}

	/**
	 * Writes the file with normal hints
	 * @param fromFile
	 * @param toFile
	 */
	private void writeNormalHints(File fromFile, File toFile) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fromFile));
			PrintWriter writer = new PrintWriter(new FileWriter(toFile));
			String line;
			while ((line = reader.readLine()) != null) {
				// solution lines start with @ and will not appear in exercise
				if (line.startsWith("@")) {
					continue;
				}
				
				// hint lines start with % and will not appear in exercise
				if (line.startsWith("%")) {
					continue;
				}

				
				// Question lines start with # and will appear in both
				if (line.startsWith("#")) {
					line = line.substring(1);// lope it off
				}
				// $ means only appear in the exercise and reduced hints and not in the solution
				if (line.startsWith("$")) {
					line = line.substring(1);// lope it off
				}
				// & means only appear in the exercise and not in the reduced hints or solution
				if (line.startsWith("&")) {
					line = line.substring(1);// lope it off
				}
				// ^ means only appear in the reduced hints
				if (line.startsWith("^")) {
					continue;
				}

				writer.println(line);

			}
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
	
	/**
	 * Writes the file with reduced hints
	 * @param fromFile
	 * @param toFile
	 */
	private void writeReducedHints(File fromFile, File toFile) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fromFile));
			PrintWriter writer = new PrintWriter(new FileWriter(toFile));
			String line;
			while ((line = reader.readLine()) != null) {
				// solution lines start with @ and will not appear in exercise
				if (line.startsWith("@")) {
					continue;
				}
				
				// hint lines start with % and will not appear in exercise
				if (line.startsWith("%")) {
					continue;
				}

				
				// Question lines start with # and will appear in both ex and solution but
				//not in reduced hints
				if (line.startsWith("#")) {
					continue;
				}
				// $ means only appear in the exercise and reduced hints and not in the solution
				if (line.startsWith("$")) {
					line = line.substring(1);// lope it off
				}
				// & means only appear in the exercise
				if (line.startsWith("&")) {
					continue;
				}
				// ^ means only appear in the reduced hints
				if (line.startsWith("^")) {
					line = line.substring(1);// lope it off
				}

				writer.println(line);

			}
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

	/**
	 * Files with no prefix go into every project
	 * @param location
	 * @param afile
	 * @param fileName
	 * @param shortName
	 */
	private void noPrefix(String location, File afile, String fileName,
			String shortName) {		
		// it might be a context file
		if (fileName.contains("context.xml")) {
			// put a copy in the tomcat folder
			String destination;
			File fromFile = new File(afile.getAbsolutePath());
			File toFile;

			for (String str : exerciseList) {
				logger.debug(" Doing the context of " + str);

				/*
				 * ----skip the ex one destination =
				 * TOMCAT_HOME+"\\conf\\Catalina\\localhost\\" +str+".xml";
				 * 
				 * System.out.println(afile.getAbsolutePath());
				 * System.out.println("Will go to ");
				 * System.out.println(destination);
				 * 
				 * fromFile = new File(afile.getAbsolutePath()); toFile = new
				 * File(destination); toFile.getParentFile().mkdirs(); try {
				 * BufferedReader reader = new BufferedReader(new
				 * FileReader(fromFile)); PrintWriter writer = new
				 * PrintWriter(new FileWriter(toFile)); String line=
				 * reader.readLine();// lope of the first line
				 * writer.println("<Context docBase='"
				 * +workspace+str+"\\WebContent' reloadable='true'>");
				 * while((line = reader.readLine())!=null){
				 * writer.println(line); } reader.close(); writer.close(); }
				 * catch (FileNotFoundException e) { // TODO Auto-generated
				 * catch block e.printStackTrace(); } catch (IOException e) { //
				 * TODO Auto-generated catch block e.printStackTrace(); }
				 * -------->
				 */
				// Do the solution one
				destination = TOMCAT_HOME + "\\conf\\Catalina\\localhost\\"
						+"solution-" + str + ".xml";
				toFile = new File(destination);
				try {
					BufferedReader reader = new BufferedReader(new FileReader(
							fromFile));
					PrintWriter writer = new PrintWriter(new FileWriter(toFile));
					String line = reader.readLine();// lope of the first line
					writer.println("<Context docBase='" + workspace +"solution-"+  str
							+ "\\WebContent' reloadable='true'>");
					while ((line = reader.readLine()) != null) {
						writer.println(line);
					}
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
			for (String str : demoList) {
				logger.debug(" Doing the context of " + str);
				destination = TOMCAT_HOME + "\\conf\\Catalina\\localhost\\"
						+ str + ".xml";
				logger.debug(afile.getAbsolutePath());
				logger.debug("Will go to ");
				logger.debug(destination);

				fromFile = new File(afile.getAbsolutePath());
				toFile = new File(destination);
				try {
					BufferedReader reader = new BufferedReader(new FileReader(
							fromFile));
					PrintWriter writer = new PrintWriter(new FileWriter(toFile));
					String line = reader.readLine();// lope of the first line
					writer.println("<Context docBase='" + workspace + str
							+ "\\WebContent' reloadable='true'>");
					while ((line = reader.readLine()) != null) {
						writer.println(line);
					}
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

		}
		/* Put a copy of files with no prefix in every project */

		for (int j = 0; j < exerciseList.size(); j++) {
			/* destination */
			String destination = workspace + exerciseList.get(j)
					+ location.substring(here.length()) + "\\" + shortName;
			logger.debug(afile.getAbsolutePath());
			logger.debug(destination);
			File fromFile = new File(afile.getAbsolutePath());

			File toFile = new File(destination);
			try {
				copyFile(fromFile, toFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// Do the solution one
			destination = workspace +"solution-" + exerciseList.get(j) 
					+ location.substring(here.length()) + "\\" + shortName;
			toFile = new File(destination);
			try {
				copyFile(fromFile, toFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	
	private void writeHints(File fromFile, File toFile, String shortName) {
		String line;
		StringBuilder hintsFile = new StringBuilder("<div class = 'code'>"); //for css styling
		try (FileReader fReader = new FileReader(fromFile);
				BufferedReader reader = new BufferedReader(fReader);) {
			hintsFile.append("<table>");
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("%")) {
					line = line.substring(1);
					//sanitize -- remove angle brackets
					line = line.replaceAll("<br />", "&br;"); //protect from being removed
					line = line.replaceAll("<br/>", "&br;");
					line = line.replaceAll("<br>", "&br;");
					line = line.replaceAll("<ul>", "&ul;");
					line = line.replaceAll("</ul>", "&/ul;");
					line = line.replaceAll("<li>", "&li;");
					line = line.replaceAll("<", "&lt;");
					line = line.replaceAll(">", "&gt;");
					//hintsFile.append("<li>"+line+"</li>");
					line = line.replaceAll("&br;","<br>");
					line = line.replaceAll( "&ul;","<ul>");
					line = line.replaceAll( "&/ul;","</ul>");
					line = line.replaceAll( "&li;","<li>");

					hintsFile.append("<tr bgcolor='LightGoldenRodYellow'><td>"+line+"</td></tr>");
				}
			}
			hintsFile.append("</table>");
			hintsFile.append("</div>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (hintsFile.length() > ("<div class = 'code'>"+"</div> ").length()) {
			toFile.getParentFile().mkdirs();
			
			String header = shortName+" hints";
			String content = hintsFile.toString();
			String sidebarTitle = "Menu";
			String[] links = new String[]{"<a href='"+toFile.getParent()+"\\index.html'>Back</a>"};
			String webPage = createWebPage(header, content, sidebarTitle, links);
			
			try (FileWriter fWriter = new FileWriter(toFile);
					PrintWriter writer = new PrintWriter(fWriter)) {
				writer.println(webPage);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}	
	
	private void writeSolved(File fromFile, File toFile) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fromFile));
			PrintWriter writer = new PrintWriter(new FileWriter(toFile));
			String line;
			while ((line = reader.readLine()) != null) {
				// $ lines only appear in the question
				if (line.startsWith("$")) {
					continue;
				}
				// & means only appear in the exercise
				if (line.startsWith("&")) {
					continue;
				}
				// % lines only appear in the hints
				if (line.startsWith("%")) {
					continue;
				}
				// ^ means only appear in the reduced hints
				if (line.startsWith("^")) {
					continue;// lope it off
				}

				// solution lines start with @ and should appear
				if (line.startsWith("@")) {
					line = line.substring(1);// lope it off
				}
				// # lines (questions typically appear in both)
				if (line.startsWith("#")) {
					line = line.substring(1);// lope it off
				}
				writer.println(line);

			}
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

	/**
	 * For when this there are TODOs but not for this exercise
	 * We write the solved version in the exercise folder
	 * @param fromFile
	 * @param toFile
	 */
	private void writeSolvedNoHash(File fromFile, File toFile) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fromFile));
			PrintWriter writer = new PrintWriter(new FileWriter(toFile));
			String line;
			while ((line = reader.readLine()) != null) {
				// $ lines only appear in the question and reduced hints
				if (line.startsWith("$")) {
					continue;
				}
				// ^ means only appear in the reduced hints
				if (line.startsWith("^")) {
					continue;// lope it off
				}
				// % lines only appear in the hints
				if (line.startsWith("%")) {
					continue;
				}
				// & means only appear in the exercise
				if (line.startsWith("&")) {
					continue;
				}
				// solution lines start with @ and should appear
				if (line.startsWith("@")) {
					line = line.substring(1);// lope it off
				}
				// # lines (questions tyically instructions -- should not appear
				// in other exercises)
				if (line.startsWith("#")) {
					continue;
				}
				writer.println(line);

			}
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

	static String htmlTop = "<!DOCTYPE html PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN' 'http://www.w3.org/TR/html4/loose.dtd'>" +
			"<html><head><meta http-equiv='Content-Type' content='text/html; charset=ISO-8859-1'>" +
			"<title>World Ocean Database Browser</title>"+
			"<link rel='stylesheet' type='text/css' href='C:\\crs"+crs+"\\docs\\styles\\rainforest.css' />"+
			"<body>"+
			"<div id='wrapper'>";
	
	static String htmlBottom = "</div><!-- wrapper --></body></html>";
	
}
