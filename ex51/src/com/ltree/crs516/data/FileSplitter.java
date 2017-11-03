package com.ltree.crs516.data;

import static com.ltree.crs516.data.FileBasedDataService.MANAGEABLE_SIZE;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.CharBuffer;
import java.util.zip.GZIPInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Splits up data file into manageable pieces which are written to disk. The 
 * original file could be a gzipped file with very many stations.
 * 
 * @author cmawata
 * 
 */
//NOTE: Each instance of this data service class is designed to be owned by a 
//single thread, the thread that owns the FileBasedDataService using it.
final class FileSplitter {

	private static Logger logger = LoggerFactory.getLogger(FileSplitter.class);
	private CharBuffer charBuffer;
	private int dataSize = 0;
	public String work_sub_dir = null;

	public FileSplitter(int service_id) {
		work_sub_dir = DataConstants.WORK_DIR + service_id + "/";
		new File(work_sub_dir).mkdirs();
	}

	/**
	 * Reads a file that is in WOD09 format and splits it into manageable
	 * pieces. The pieces are placed in a working directory,
	 * <cite>workDir</cite>.
	 * 
	 * @param theFile
	 *            , a String representing the absolute path to the file. If the
	 *            String theFile ends with gz we assume it to be a gzipped file.
	 * 
	 * @return an int, the number of stations in the file.
	 */
	int splitFile(String theFile) {
		String[] tmpArray = new String[MANAGEABLE_SIZE];
		BufferedReader br = null;
		PrintWriter writer = null;
		InputStream in = null;
		try {
			logger.debug("Splitting the data file {}", theFile);
			// Figure out the file extension, e.g. gz
			String[] fileTokens = theFile.split("\\.");
			// The extension will be the last token, i.e. the piece after the
			// last dot.
			String extension = fileTokens[fileTokens.length - 1];
			Reader reader = new FileReader(theFile);
			if (extension.equals("gz")) {
				in = new FileInputStream(theFile);
				GZIPInputStream gzIn = new GZIPInputStream(in);
				reader = new InputStreamReader(gzIn);
			}
			//Create the work directory if necessary.
			File workFile = new File(work_sub_dir);
			if (!workFile.exists()) {
				workFile.mkdir();
			}
			// Cleanup the work directory by deleting anything in there.
			File[] theFiles = workFile.listFiles();
			for (File file : theFiles) {
				file.delete();
			}
			/*
			 * Read the data file. Refer to table 10.1 of
			 * http://www.nodc.noaa.gov/OC5/WOD09/pr_wod09.html.
			 */
			int counter = 0; // counts how many stations we have read.
			/*
			 * A block contains a manageable number of stations. The number is
			 * determined by the field manageableSize. When counter gets to this
			 * size we write a file to disk.
			 */
			int block = 0;
			dataSize = 0;
			br = new BufferedReader(reader);
			String line = "";
			StringBuilder buffer = new StringBuilder();
			while ((line = br.readLine()) != null) { // a null means the end of
														// the data file.
				/*
				 * Detecting the beginning and end of a station is a little
				 * tricky. They all start with a character e.g. 'B' to identify
				 * the World Ocean Database version. However that character
				 * could also be first character on a new line when it is not
				 * the beginning of a new station. Files that follow the earlier
				 *  WOD98 standard files start with a numeric. This program 
				 *  will not handle those formats.
				 */
				charBuffer = CharBuffer.wrap(line);
				char WOD_VersionIdentifier = charBuffer.get();
				if (WOD_VersionIdentifier != 'B') {
					throw new IOException(
							"File of unfamiliar format. Got WOD version "
									+ "identifier " + WOD_VersionIdentifier
									+ " but " + "expected 'B'");
				}
				// Figure out how many bytes are in the profile.
				int bytesInProfile = readIntField();
				/*
				 * All observed and standard level data files are written as a
				 * series of 80 character length ASCII records. We concatenate
				 * the lines that go with a station into a single String. We put
				 * all the data in the file into a String[], one String per
				 * station.
				 */
				int numLines = (int) Math.ceil(bytesInProfile / 80.0);
				buffer.delete(0, buffer.length());
				// Concatenate the lines that go with this station into a single
				// line.
				buffer.append(line);
				for (int i = 1; i < numLines; i++) {
					buffer.append(br.readLine());
				}
				// Put the line for this station into tmpArray.
				tmpArray[counter++] = (new String(buffer));
				dataSize++;
				if (counter == MANAGEABLE_SIZE) {
					// write the file
					writer = new PrintWriter(new FileWriter(work_sub_dir
							+ block + ".db"));
					for (int i = 0; i < MANAGEABLE_SIZE; i++) {
						writer.println(tmpArray[i]);
					}
					writer.close();
					counter = 0;
					block++;
				}
			}
			// write the final block
			writer = new PrintWriter(new FileWriter(work_sub_dir + block
					+ ".db"));
			for (int i = 0; i < counter; i++) {
				writer.println(tmpArray[i]);
			}
			writer.close();
		} catch (EOFException e) {
			logger.info("EOF");
		} catch (IOException e) {
			logger.info(e.getMessage());
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					logger.error("Failed to close non-null reader ", e);
				}
			}
			if (writer != null) {
				writer.close();
			}
		}
		return dataSize;
	}

	/**
	 * Helper method that reads an integer sitting in the next numBytes bytes of
	 * the string. Reading starts at the cursor position.
	 * 
	 * @param line
	 *            , a String representing the station data.
	 * @param bytesInNextField
	 *            , an int, the number of bytes to be read
	 * @return an int sitting in the next bytesInNextField bytes
	 */
	private int readInt(int bytesInNextField) {
		StringBuilder tmpStrBuffer = new StringBuilder();
		for (int j = 0; j < bytesInNextField; j++) {
			tmpStrBuffer.append(charBuffer.get());
		}
		return Integer.parseInt(tmpStrBuffer.toString().trim());
	}

	/**
	 * Helper method for reading integer data. Reads the next byte b, starting
	 * from where the cursor is and then returns the integer in the next b bytes
	 * or null if b is zero.
	 * 
	 * @return an Integer object so that null is a possibility.
	 */
	private Integer readIntField() {
		int bytesInNextField = Integer.parseInt("" + charBuffer.get());
		if (bytesInNextField == 0) {
			return null;
		}
		return readInt(bytesInNextField);
	}

}
