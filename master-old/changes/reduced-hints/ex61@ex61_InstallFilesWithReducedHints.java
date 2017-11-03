import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.channels.FileChannel;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class InstallFilesWithReducedHints {
	public static void main(String[] args) {
		char fileSeparater = File.separatorChar;
		String projectFolderName = new File("").getAbsolutePath();
		String projectName = projectFolderName.substring((projectFolderName.lastIndexOf(fileSeparater)+1));
		String workSpace = projectFolderName.substring(0,(projectFolderName.lastIndexOf(fileSeparater)));
		String hintsFolderName = workSpace+fileSeparater
				+"store"+fileSeparater+projectName;
		File reduced_hints_folder = new File(hintsFolderName+fileSeparater+"reduced-hints");

		JFileChooser chooser = new JFileChooser(reduced_hints_folder);
		chooser.setMultiSelectionEnabled(true);
		File[] targets = null;
		int result = chooser
				.showDialog(new JFrame(), "Choose Files to Replace");
		if (result == JFileChooser.APPROVE_OPTION) {
			targets = chooser.getSelectedFiles();
		} else {
			return;
		}
		
		for (File file : targets) {
			if (file.getName().startsWith("Install")) {
				continue;
			}
			String line;
			try (BufferedReader reader = new BufferedReader(
					new FileReader(file));) {
				while ((line = reader.readLine()) != null) {
					line = line.replace(';', ' ').trim();
					if (line.startsWith("package")) {
						String packageName = line.split(" ")[1];
						String presentFileAbsolutePath = projectFolderName
								+ fileSeparater + "src" + fileSeparater
								+ packageName.replace('.', fileSeparater)
								+ fileSeparater + file.getName();
						File presentFile = new File(presentFileAbsolutePath);
						// File fileToStore = new
						// File(hintsFolderName+fileSeparater
						// +"normal-hints"+fileSeparater+file.getName());
						// copyFile(presentFile, fileToStore);
						copyFile(file, presentFile);
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
		}
		/*		
			File installFilesWithNormalHintsFile = new File(projectFolderName+fileSeparater+"store"+fileSeparater+"normal-hints"+fileSeparater+"InstallFilesWithNormalHints.java");
			File targetInstallFile = new File(
					projectFolderName+fileSeparater+"src"+fileSeparater+"InstallFilesWithNormalHints.java");
			try {
				copyFile(installFilesWithNormalHintsFile, targetInstallFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			File thisFile = new File(projectFolderName+fileSeparater+"src"+fileSeparater+"InstallFilesWithReducedHints.java");
			thisFile.deleteOnExit();
*/			
			JOptionPane.showMessageDialog(null, "Refresh the project to update the files");
	}
	
	/**Uses NIO to quickly copy files*/
	static void copyFile(File in, File out) throws IOException {
		out.getParentFile().mkdirs();
		try (FileInputStream inStream = new FileInputStream(in);
				FileChannel inChannel = inStream.getChannel();
				FileOutputStream outStream = new FileOutputStream(out);
				FileChannel outChannel = outStream.getChannel();){
			inChannel.transferTo(0, inChannel.size(), outChannel);
		} 
	}
}
