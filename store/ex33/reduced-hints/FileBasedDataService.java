package com.ltree.crs516.data;
 
import static com.ltree.crs516.data.DataConstants.WORK_DIR;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ltree.crs516.domain.Station;
import com.ltree.crs516.util.WODThreadPools;

// NOTE: Each instance of this data service class is designed to be owned by a 
// single thread! 

/**
 * An implementation of the {@link DataService} interface. It is a 
 * representation of the database. It is responsible for reading the data 
 * file and creating {@link Station} objects.
 * 
 * @author crs516 development team.
 *
 */
public final class FileBasedDataService implements DataService {
	
	private static final Logger logger 
			= LoggerFactory.getLogger(FileBasedDataService.class);

	/**
	 * If several threads need a FileBasedDataService they will each get their
	 * own instance. We need to give each instance its own sub-area of the work
	 * folder. Hence the need for service_id. While this will remove competition 
	 * for individual files the threads are still trying to read from the same
	 * hard drive so there will be some contention.
	 */	
	private static AtomicInteger lastServiceId = new AtomicInteger();
	private int service_id = 0;
	
	/**
	 * Splits up files into manageable pieces. The service-id will indicate to 
	 * FileSplitter the sub-folder of the work folder in which to work.
	 */
	private FileSplitter fileSplitter = new FileSplitter(service_id);

	/**
	 * A data file will be broken up into smaller more manageable files. 
	 * The constant MANAGEABLE_SIZE is the number of stations we shall 
	 * put in a file. Make it smaller if you get out of memory errors.
	 */
	public static final int MANAGEABLE_SIZE=1000;

	/**
	 * Parses a file in WOD format and creates a station object.
	 */
	private StationParser stationParser = new StationParser();

	/**
	 * The work directory. To avoid out-of-memory errors, data files will be 
	 * split into manageable pieces and stored in this directory. Since 
	 * Each instance of FileBasedDataService has its own unique work_sub_dir.
	 */
	private String work_sub_dir = null;

	/**
	 * Block we are currently examining. After splitting the file into 
	 * manageable sized chunks we call each chunk a block.
	 * Blocks have names like 0.db, 1.db,... Currently we are
	 * examining currentBlock.db.
	 */
	private int currentBlock = 0;

	/**
	 * Runnable that actually loads the data.
	 */
	private final class DataLoader implements Runnable {
		@Override
		public void run() {
			loading = true;
			logger.info("Data loader loading {} at {}", dataFile, new Date());
			
			// Break up the file to avoid out-of-memory problems.
			// The pieces, referred to as 'blocks', will be in the work folder.
			splitFile(dataFile);
			
			//Read the first block. 
			currentBlock = 0;
			try {
				//This will populate the String[] dataArray with one String 
				//per station. 
				readBlock(currentBlock);
			} catch (IOException e) {
				logger.error("Failed to read a portion of the file ", e);
			}
			//Indicate that we are done loading the stations so 
			//displaying of data can commence.
			logger.info("Data from {} loaded", dataFile);
			loading = false;
			open = true;
		}
	}

	/**
	 * Each entry in this array is a String corresponding to one station. The 
	 * array holds the data for the MANAGEABLE_SIZE stations that form a block. 
	 */
	private String[] dataArray = new String[MANAGEABLE_SIZE];
	
	//TODO 1: Note: When close() is called we will release the cache 'dataArray'
	//declared above this comment and thus allow it to be garbage collected. 
	//At that point the DataService is unusable until another file is loaded. 
	// The variable 'open' declared below keeps track of the open/closed state 
	//of the DataService. TODO 2 is towards the end of the file.
	
	private volatile boolean open;
	
	/**
	 * File object representing the original file where the data set is.
	 * It could be a compressed (.gz) file or an expanded file.
	 */
	private File dataFile;

	/**
	 * Number of stations in the whole data set, i.e., the in the zip file.
	 */
	private int dataSize = 0;

	/**
	 * Indicates if file data is in the process of being read. When false it
	 * is safe to display the data without the threads interfering with one
	 * another.
	 */
	private volatile boolean loading;

	/**
	 * 
	 * Constructs the instance of FileBasedDataService.
	 *
	 * @param theFile The file containing the dataset.
	 */
	public FileBasedDataService(File theFile) {
		dataFile = theFile;
		if(lastServiceId.intValue()==0){
			cleanUpWorkFolder();
		}
		service_id = lastServiceId.getAndIncrement();
		work_sub_dir = DataConstants.WORK_DIR+service_id+"/";
		fileSplitter = new FileSplitter(service_id);
		logger.info("Service ID is {}",service_id);
		load();
	}

	/**
	 * Cleans up the work folder each time we start so that files do not
	 * accumulate on the hard drive. 
	 */
	private synchronized void cleanUpWorkFolder() {
		if(lastServiceId.intValue()==0){
			logger.info("Cleaning up work directory");
			File workDir = new File(WORK_DIR);
			if(workDir.exists()){
				File[] workSubDirs = workDir.listFiles();
				if(workSubDirs!=null){
					for (File file : workSubDirs) {
						if(!file.isDirectory()){
							file.delete(); 
							continue;
						}
						File[] dataFiles = file.listFiles();
						if(dataFiles!=null){
						for (File file2 : dataFiles) {
							file2.delete();
						}
						file.delete();
						}
					}
				}
			}
			else{
				workDir.mkdir();
			}
		}
	}

	/**
	 * Gets the location of the data file.
	 * @return a File object representing the data file.
	 */
	File getDataFile() {
		if(!open){
			throw new IllegalStateException("DataService not open");
		}
		return dataFile;
	}
	
	/**
	 * Determine if data is being read from file.
	 * @return true if data is in the process of being read,
	 * false otherwise.
	 */
	@Override
	public boolean isLoading() {
		return loading;
	}

	/**
	 * Triggers the loading of the data from dataFile.
	 */
	@Override
	public void load() {
		logger.debug("Data file is {}", dataFile);
		if(dataFile == null){
			return;
		}
		loading = true;
		WODThreadPools.getDaemonThreadService().submit(new DataLoader());
	}

	/**
	 * Reads a record from the file. Returns a Station.
	 * 
	 * @param recNo
	 *            the record number of the desired record.
	 * @throws RecordNotFoundException
	 *             if recNo does not correspond to a Record in the database.
	 * @throws IOException 
	 */
	@Override
	public Station read(int recNo) 
			throws RecordNotFoundException, IOException {
		if(!open){
			throw new IllegalStateException("DataService not open");
		}
		validateRecNo(recNo);
		int theBlock = recNo / MANAGEABLE_SIZE;
		if (theBlock != currentBlock) {
			readBlock(theBlock);
		}
		int offSet = recNo % MANAGEABLE_SIZE;
		try {
			Station ans = stationParser.makeStation(dataArray[offSet]);
			return ans;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Reads a block into memory. The original file was broken up into pieces, 
	 * by FileSplitter. The pieces are called 'blocks' and are located 
	 * in the work directory workDir. Each line in the block is the 
	 * concatenation of all the lines in the original data file that go with a
	 * station. This method reads a block into memory as a String[], one String 
	 * per station. After this method runs the field dataArray has all the data
	 * for block number currentBlock.
	 *
	 * @param theBlock, an int, the index of the block. The files for blocks 
	 * have names like 0.db, 1.db etc.
	 * @throws IOException if there are problems reading the file.
	 */
	private void readBlock(int theBlock) throws IOException {
		logger.info("Reading block {}",(work_sub_dir+theBlock+".db"));
		try(BufferedReader reader = new BufferedReader(
				new FileReader(work_sub_dir+theBlock+".db"))){
			String lineForAStation = null;
			int i=0;
			while((lineForAStation = reader.readLine())!=null){
				dataArray[i++]=lineForAStation;
			}
			currentBlock = theBlock;
		}
	}

	/**
	 * Sets the location of the data file.
	 */
	public void setDataFile(File newFile) {
		dataFile = newFile;
	}

	/**
	 * Accessor for the number of stations in the data file.
	 * 
	 * @return an int, the number of stations in the data file.
	 */
	@Override
	public int size() {
		if(loading){
			return 0;
		}
		if(!open){
			throw new IllegalStateException("DataService not open");
		}
		return dataSize;
	}

	/**
	 * Reads a file in in WOD09 format and splits it into manageable pieces.
	 * 
	 * @param theFile
	 *            the File to be read.
	 * 
	 */
	private void splitFile(File theFile) {
		dataSize = fileSplitter.splitFile(theFile.getAbsolutePath());
		logger.info("dataSize is now {}",dataSize);
	}

	/**
	 * Checks if a record number is valid.
	 * 
	 * @param recNo
	 *            the record number to be validated
	 * @throws RecordNotFoundException
	 *             if there is no such record
	 * @return true if the record number is a valid one.
	 */
	private boolean validateRecNo(int recNo) throws RecordNotFoundException {
		if(!open){
			throw new IllegalStateException("DataService not open");
		}
		if (dataSize == 0) {
			throw new RecordNotFoundException(
					"There is no data yet -- you need to read in a file.");
		}
		if (recNo < 0 || recNo > dataSize) {
			throw new RecordNotFoundException(
					recNo
							+ " is not a valid record number. Record number "
							+"must be in the range 1 to "+ dataSize + ". ");
		}
		return true;
	}
	
	@Override
	public void load(Object details) {
		if(details instanceof File){
			dataFile = (File)details;
			load();
		}
	}

	// Implement the Iterator interface.
	//TODO 2: First some discussion --
	
	// TODO 2a: Note: This class is for use by only one 
	//thread so we have no threading issues. The only other
	//thread is the one that runs the DataLoader and it is 
	//not alive at the time iteration can be done.
	
	//TODO 2b: Note: We do have an issues if the owner calls
	//setFile() or close() method in between calls to the iterator.
	//The iterator is then unusable. We will take that into account.
	
	//TODO 2c: Note: We also have an issue if the owner gets
	//a second iterator inside the loop for the first one as
	//they could potentially read stations that are far
	//apart. This would not create errors but it would result in 
	//many cache misses and subsequent file reads which is inefficient.
		
	//TODO 3: Now let us write an Iterator! Complete the code in the range  
	//from //{{Marker 1 to //}}end Marker 1 to write the 
	//FileBasedServiceIterator class.
	
	//{{Marker 1
	/**
	 * Iterator for clients to use while traversing all the Stations in the 
	 * DataService using a foreach loop.
	 * @author crs 516 development team.
	 *
	 */
	private final class FileBasedServiceIterator implements Iterator<Station>{

		private int currentStation = 0;
		
		// We keep track of the file because if the user changes the file
		// this iterator becomes unusable.
		private String fileName;  
		private boolean invalid;
		
		private FileBasedServiceIterator(String fileName) {
			super();
			this.fileName = fileName;
		}

		/**
		 * When true we should not use the iterator.
		 * @return a boolean, true if the iterator is invalid.
		 */
		private boolean isInvalid(){
			if(invalid || !open || !fileName.equals(dataFile.getName())){
				invalid = true;
			}
			return invalid;
		}
		
		/**
		 * Called by client to find out if there are more Stations to see.
		 */
		@Override
		public boolean hasNext() {

			//TODO 3a: 
			//If the iterator is invalid throw an IllegalStateException.
			//Otherwise, return true if currentStation is less than size()-1 
			//and false if currentStation is not less than size()-1.
			




			return false;
		}
	
		/**
		 * Called by client to get a reference to a Station.
		 */
		@Override
		public Station next() {
			
			//TODO 3b: If the iterator is invalid throw an 
			//IllegalStateException. Otherwise, increment currentStation and 
			//then use the method read() to obtain and return the station of 
			//that index. You will have to catch some exceptions. (Highlight 
			//the code, Right-click | Surround With | Try/catch). In case of 
			//failure return null.
			










			return null;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("Data is read-only");
		}
	}// End of iterator inner class
	
	// }} End Marker 1

	
// TODO 4: Complete the code in the range //{{Marker2 to //}} End Marker 2 
// to implement the Iterable<Station> interface.
	
	//{{Marker 2
	
	/**
	 * Called by client to get a reference to the iterator.
	 */
	public Iterator<Station> iterator() {
	
		//TODO 4a: If Field dataFile is null or the variable open is false then 
		//throw an IllegalStateException. The user has not yet chosen a file.	
		//Otherwise, return a new instance of FileBasedServiceIterator, passing
		//the name of dataFile into the constructor. 





		return null;
	}

	// }} End Marker 2

	//Implementing the Closeable interface.

//TODO 5: In the close method, set the 'open' field to false and
//re-initialize dataArray as a new String array of length MANAGEABLE_SIZE.
	public void close(){


		logger.info("closing");
	}
	
	@Override
	public List<Integer> getCruiseNumbers() {
		throw new UnsupportedOperationException("Not yet implemented");
	}
	
	@Override
	public Station read(Integer cruiseNumber, int index) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public Integer getCruiseSize(Integer cruiseNumber) {
		throw new UnsupportedOperationException("Not yet implemented");
	}
	
}
