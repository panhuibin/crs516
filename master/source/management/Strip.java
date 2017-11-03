package management;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Program to compact the load
 * It will delete the src and WebContent folders
 */
public class Strip {
	
	public Strip(){
		String here = new File("").getAbsolutePath();
		workspace=here.substring(0,here.lastIndexOf("\\"))+"\\";

		Properties props = new Properties();
		try {
			props.load(getClass().getResourceAsStream("labs.props"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(props.get("demos")!=null){
		demos = ((String)props.get("demos")).split(",");
		demoList = Arrays.asList(demos);
		System.out.println(demos.length+" demos");
		}
		else{
			demoList = new ArrayList<>();
		}
		exercises=((String)props.get("exercises")).split(",");
		exerciseList = Arrays.asList(exercises);

		deleteFoldersInStrip=((String)props.get("deleteFoldersInStrip")).split(",");
	}
	/**
	 * Names of demos.
	 */
	private String[] demos;
	
	private List<String> demoList;
	
	/**The exercises. The solutions will be the <ex**>_solution*/
	private String[] exercises;
	
	/**
	 *  The folders to be deleted
	 * 
	 */
	private String[] deleteFoldersInStrip;
	private List<String> exerciseList;
	
	private String workspace;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Strip().strip();

	}

	private void strip() {
		System.out.println("workspace is "+workspace);
		for (String demoName : demoList) {
			System.out.println("Processing "+demoName);
			File theTargetFolder = null;
			for (String target : deleteFoldersInStrip) {
				theTargetFolder = new File(workspace+demoName+"\\"+target);
				purge(theTargetFolder);
				
			}

		}

		for (String exName : exerciseList) {
			System.out.println("Processing "+exName);
			File theTargetFolder = null;
			for (String target : deleteFoldersInStrip) {
				theTargetFolder = new File(workspace+exName+"\\"+target);
				purge(theTargetFolder);
				
			}
			String sol = "solution-"+exName;
			System.out.println("Processing "+sol);
			for (String target : deleteFoldersInStrip) {
				theTargetFolder = new File(workspace+sol+"\\"+target);
				purge(theTargetFolder);
			}
			
		}
		System.out.println("Done!");
	
	}

	private void purge(File file) {
		if(file.isFile()){
			file.delete();
			return;
		}
		else if (file.isDirectory()){
			File[] theFiles = file.listFiles();
			for (File file2 : theFiles) {
				purge(file2);
			}
			file.delete();
		}
	}

}
