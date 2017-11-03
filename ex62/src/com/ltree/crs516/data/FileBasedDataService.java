package com.ltree.crs516.data;
 
import static com.ltree.crs516.data.DataConstants.WORK_DIR;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ltree.crs516.domain.Station;
import com.ltree.crs516.util.WODThreadPools;

/**
 * An implementation of the {@link DataService} interface. It is a 
 * representation of the database. It is responsible for reading the data file 
 * and creating {@link Station} objects.
 * 
 * @author crs516 development team
 *
 */
public final class FileBasedDataService implements DataService {

	/**
	 * A data file will be broken up into smaller more manageable files. that 
	 * will fit in memory. The constant MANAGEABLE_SIZEe is the number of 
	 * stations in a file. Make it smaller if you get out of memory errors.
	 */
	public static final int MANAGEABLE_SIZE=1000;

	/**
	 * This is the cache. Each entry in this array is a String corresponding to 
	 * one station. A typical one looks like
	 * B328181001621199438981923 616-4424023452-5542130 511010120102300102310102320101811 49999239141133024114330216229220142997702009296011000033122000442361500442402300452-5542003301660011000033123500442363800442402300452-5792003301660011000033122000442366900442402300452-60420033016600
	 * The expensive process is the parsing of that String to extract the data 
	 * for a Station. The cache array holds the data for the MANAGEABLE_SIZE 
	 * stations that form a block. 
	 */
	private String[] dataArray = new String[MANAGEABLE_SIZE];
	
	/**
	 * A ReentrantReadWriteLock will ensure that the cache is only replenished 
	 * when no reading is taking place.
	 */
	
//TODO 1: Create an instance of ReentrantReadWriteLock.
	private final ReentrantReadWriteLock rwLock = null; //Edit this.

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
	public Station read(int recNo) throws RecordNotFoundException, IOException {
		if (!open) {
			throw new IllegalStateException("DataService not open");
		}
		validateRecNo(recNo);//Checks that the record number is within the range
		//1 to the total number of stations.
		int theBlock = recNo / MANAGEABLE_SIZE; //theBlock is the block that 
		//station number recNo belongs to. 
		try {
			
//TODO 2: Lock the readLock.
			


			if (theBlock != currentBlock) {// The data in the cache is not the 
							//data we need. Have to read theBlock into the cache.
//TODO 3: Upgrade to a writeLock. You have to unlock the readLock first. Note 
//that for a while this thread has no lock at all so another thread 
//could overtake it and manage to get a write lock and write data into the 
//cache before this thread can.


//TODO 4: Instead of just calling readBlock(theBlock) first check that 
// you still have that theBlock != currentBlock, i.e. that 
//another thread did not already do the read. If you still have that 
//theBlock != currentBlock then call readBlock(theBlock)



						readBlock(theBlock); //Check before you do this.


				
//TODO 5: Downgrade to a readLock. Lock the readLock *before* you unlock the 
//writeLock so that you are never without a lock. That way you know the cache 
//could not have been written to by another thread.
	

			}

			int offSet = recNo % MANAGEABLE_SIZE; //How far into the cache is 
													//the station we want?
			Station ans = stationParser.makeStation(dataArray[offSet]);
			//Actually read the station.
			return ans;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
//TODO 6: Unlock the readLock.



			//Just in case there was an exception and the writeLock was not unlocked 
			if(rwLock.writeLock().isHeldByCurrentThread()){
				rwLock.writeLock().unlock();
			}

		}

	}
	
	private final static Logger logger = LoggerFactory.getLogger(FileBasedDataService.class);

	/**
	 * If several threads need a FileBasedDataService they will each get their
	 * own instance. We need to give each instance its own sub-area of the work
	 * folder. Hence the need for service_id.
	 */	

//TODO 7: Since the lastServiceId is static it is potentially visible to 
//multiple threads. It will need to be incremented atomically so change the 
//type of the variable lastServiceId to AtomicInteger rather than int.
// There are more TODO tasks further down.	
	
	private static int lastServiceId = 0; //Edit this.

	
	private int service_id = 0;
	
	/**
	 * Splits up files into manageable pieces. The service-id will indicate to 
	 * FileSplitter the sub-folder of the work folder in which to work.
	 */
	private FileSplitter fileSplitter = new FileSplitter(service_id);



	/**
	 * Parses a file in WOD format and creates a station object.
	 */
	private StationParser stationParser = new StationParser();

	/**
	 * The work directory. To avoid out-of-memory errors, data files will be 
	 * split into manageable pieces and stored in this directory. Each instance
	 * of FileBasedDataService has its own unique work_sub_dir.
	 */
	private String work_sub_dir = null;

	/**
	 * Block we are currently examining. After splitting the file into 
	 * manageable sized chunks we call each chunk a block.
	 * Blocks have names like 0.db, 1.db,... Currently we are
	 * examining currentBlock.db.
	 */
	private int currentBlock = -1;

	/**
	 * Runnable that actually loads the data.
	 */
	private final  class DataLoader implements Runnable {
		@Override
		public void run() {
			loading = true;
			logger.info("Data loader loading {} at {}", dataFile, new Date());
			
			// Break up the file to avoid out-of-memory problems The pieces, 
			//referred to as 'blocks', will be in the work folder.
			splitFile(dataFile);
			
			//Read the first block. 
			currentBlock = 0;
			try {
				//This will populate the array of Strings, dataArray, with one 
				//String per station. 
				readBlock(currentBlock);
			} catch (IOException e) {
				logger.error("Failed to read a portion of the file ", e);
			}
			//Indicate that we are done loading the stations so displaying of 
			//data can commence.
			logger.info("Data from {} loaded", dataFile);
			loading = false;
			open = true;
		}
	}

//TODO 8: The boolean called open will be read and written to concurrently by 
//threads checking if the data service is open and changing its value as 
//Stations are loaded. Give it a volatile modifier.
	private boolean open;

	/**
	 * File object representing the original file where the data set is.
	 * It could be a compressed (.gz) file or an expanded file.
	 */
	private File dataFile;

	/**
	 * Number of stations in the whole data set, i.e., the file
	 */
	private int dataSize = 0;

	/**
	 * Indicates if file data is in the process of being read. When false it
	 * is safe to display the data without the threads interfering with one
	 * another.
	 */
//TODO 9: The boolean called loading will be called concurrently by threads 
//checking if data is being loaded into the data service and changing its 
//value as Stations are loaded. Add an appropriate modifier.	
	private boolean loading;


	/**
	 * 
	 * Constructs the instance of FileBasedDataService.
	 *
	 * @param theFile The file containing the dataset.
	 */
	public FileBasedDataService(File theFile) {
		dataFile = theFile;
		
//TODO 10: Look at the javadocs of java.util.concurrent.atomic.AtomicInteger for
// a method that gets the current value of the AtomicInteger and fix the 
//line below.
		
		if(lastServiceId==0){ //Edit this line.
			cleanUpWorkFolder();
		}

//TODO 11: The line below is a read-modify-write. Edit it to increment and get 
//the lastServiceId safely.

		service_id = lastServiceId++; //Edit this line.
		work_sub_dir = DataConstants.WORK_DIR+service_id+"/";
		fileSplitter = new FileSplitter(service_id);
		logger.info("Service ID is {}",service_id);
		load();
	}

	/**
	 * Cleans up the work folder each time we start so that files do not
	 * accumulate on the hard drive.  
	 */
// TODO 12: Synchronize this method. Although we have an AtomicInteger we are
// performing a check-then-act operation. Also fix the line where you get the 
//check the value of lastServiceId.

	private void cleanUpWorkFolder() {//Edit this line.
		if(lastServiceId==0){ //Edit this line.
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
	
//================ No more TODOs after this ===================================	

	/**
	 * Gets the location of the data file.
	 * @return a File object representing the data file.
	 */
	File getDataFile() {
		if(!open){
			throw new IllegalStateException("DataService not open.");
		}
		return dataFile;
	}
	
	/**
	 * Determine if data is being read from file.
	 * @return true if data is in the process of being read.
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
	 * Reads a block into memory. The original file is broken up
	 * by the splitFile method. The pieces are called 'blocks' and are located 
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
	 * Accessor for the number of stations in the data file
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
					recNo + " is not a valid record number. Record number " +
							"must be in the range 1 to " + dataSize + ". ");
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

	private final class FileBasedServiceIterator implements Iterator<Station>{

		private int currentStation = 0;
		
		// We keep track of the file because if the user changes the file this 
		//iterator becomes unusable.
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
		
		@Override
		public boolean hasNext() {
			if(isInvalid()){
				throw new IllegalStateException("Iterator is invalid");
			}
			return(currentStation<size());
		}
	
		@Override
		public Station next() {
			if(isInvalid()){
				throw new IllegalStateException("Iterator is invalid");
			}
			Station station = null;
			try {
				station = read(currentStation++);
			} catch (IOException | RecordNotFoundException e) {
				logger.error("Failed to read station",e);
			} 
			return station;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("Data is read-only");
		}
	}// End of iterator class.

	@Override
	public Iterator<Station> iterator() {

		if(dataFile == null || !open){
			throw new IllegalStateException("Need to choose a data file first");
		}
		return new FileBasedServiceIterator(dataFile.getName());
	}

	@Override
	public void close(){
		open = false;
		dataArray = new String[MANAGEABLE_SIZE]; 
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
