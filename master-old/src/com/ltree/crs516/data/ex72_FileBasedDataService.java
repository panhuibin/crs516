package com.ltree.crs516.data;
 
import static com.ltree.crs516.data.DataConstants.WORK_DIR;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ltree.crs516.data.RecordNotFoundException;
@import com.ltree.crs516.domain.IStation;
import com.ltree.crs516.domain.Station;
@import com.ltree.crs516.domain.StationProxy;
import com.ltree.crs516.util.WODThreadPools;

/**
 * An implementation of the {@link DataService} interface. It is a representation of 
 * the database. It is responsible for reading the data file and creating 
 * {@link Station} objects.
 * 
 * @author crs516 development team
 *
 */
public final class FileBasedDataService implements DataService {
	
	private static final Logger logger = LoggerFactory.getLogger(FileBasedDataService.class);

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
	 * The constant MANAGEABLE_SIZEe is the number of stations we shall 
	 * put in a file. Make it smaller if you get out of memory errors.
	 */
	public static final int MANAGEABLE_SIZE=1000;

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
	private int currentBlock = 0;

	/**
	 * Runnable that actually loads the data.
	 */
	private class DataLoader implements Runnable {
		@Override
		public void run() {
			loading = true;
			logger.info("Data loader loading {} at {}", dataFile, new Date());
			splitFile(dataFile);
			currentBlock = 0;
			try {
				readBlock(currentBlock);
			} catch (IOException e) {
				logger.error("Failed to read a portion of the file ", e);
			}
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
	
	private volatile boolean open;
	
	/**
	 * File object representing the original file where the data set is.
	 * It could be a compressed (.gz) file or an expanded file.
	 */
	private File dataFile;

	/**
	 * Number of stations in the whole data set, i.e., in the zip file
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

//TODO 1: This is the old read() method which always returns a Station. 
//Delete or comment out the code between //{{Marker 1 and //}}end Marker 1	
//{{Marker 1	
$	/**
$	 * Reads a record from the file. Returns a Station.
$	 * 
$	 * @param recNo
$	 *            the record number of the desired record.
$	 * @throws RecordNotFoundException
$	 *             if recNo does not correspond to a Record in the database.
$	 * @throws IOException 
$	 */
$	@Override
$	public Station read(int recNo) throws RecordNotFoundException, IOException {
$		if (!open) {
$			throw new IllegalStateException("DataService not open");
$		}
$		String stationString = getStationString(recNo);
$		try {
$			Station iStationToReturn = stationParser.makeStation(stationString);
$			return iStationToReturn;
$		} catch (IOException e) {
$			e.printStackTrace();
$			return null;
$		}
$	}

//}}end Marker 1


//TODO 2: Remove the comments signs around the code demarkated by Marker 2.	
//{{Marker 2	

$	/*

	// We shall cache the proxies we have created in proxyMap. Proxies are
	//easy to re-create so we shall use Reference types to avoid getting
	//OutOfMemoryErrors just for storing proxies.
	private HashMap<Integer, WeakReference<StationProxy>> proxyMap 
															= new HashMap<>();
	
	//Based on value of boolean noProxy this method either returns a Station or 
	//a StationProxy.
	public IStation read(int recNo, boolean noProxy)
			throws RecordNotFoundException, IOException {
		String stationString = getStationString(recNo);
		IStation iStationToReturn = null;
		if (noProxy) {
			try {
//TODO 2a:Call makeStation on the stationParser and put the reference into the 
//iStationToReturn variable.			
@				iStationToReturn = stationParser.makeStation(stationString);
$
%TODO 2a: iStationToReturn = stationParser.makeStation(stationString);<br/><br/>
				return iStationToReturn;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			// Perhaps the proxy is already in the cache proxyMap
			WeakReference<StationProxy> weakRef = proxyMap.get(recNo);
			if (weakRef != null && (iStationToReturn = weakRef.get()) != null) {
				return iStationToReturn;
			} else {//Proxy was not found in the cache proxyMap.
				try {
//TODO 2b: Call makeProxyStation on the stationParser and 
// put the reference into the iStationToReturn variable.			
@				iStationToReturn = stationParser.makeProxyStation(stationString);
$
%TODO 2b: iStationToReturn = stationParser.makeProxyStation(stationString);<br/><br/>
					
					return iStationToReturn;
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
	}


	//This method calls read(recNo, boolean noProxy) with the value of the boolean noProxy
	//true if the length of the stationString is short (<1000) and false if the 
	//stationString is long. It returns the IStation obtained.
	public IStation read(int recNo) throws RecordNotFoundException, IOException {
		String stationString = getStationString(recNo);
//TODO 2c: Based on the length of stationString call read(recNo, boolean noProxy)
//with the appropriate value of noProxy and return the IStation you get.		

@		if (stationString.length() < 1000) {
@			return read(recNo, true); // return a Station
@		} else {
@			return read(recNo, false); // return a StationProxy
@		}
$
$
$
$
$
$
%TODO 2c:<br/> if (stationString.length() < 1000) {<br/>&#160;&#160;return read(recNo, true); // return a StationProxy<br/>} else {<br/>&#160;&#160;return read(recNo, false); // return a Station<br/>}<br/><br/>
	}


$*/

//}}end Marker 2

	/**
	 * Given an integer recNo if returns the String that has data for 
	 * station number recNo
	 * @param recNo an int, the index of the station.
	 * @return a String with data for the station
	 * @throws RecordNotFoundException if the integer recNo is not in range.
	 * @throws IOException if there is trouble reading the String fron disk
	 */
	private String getStationString(int recNo) throws RecordNotFoundException,
	IOException {
		validateRecNo(recNo);
		// Figure out the block of data that has the string for station number
		// recNo.
		int theBlock = recNo / MANAGEABLE_SIZE;
		// If necessary read data from disk into the cache.
		if (theBlock != currentBlock) {
			readBlock(theBlock);
		}
		// Figure out how far into the cache recNo is.
		int offSet = recNo % MANAGEABLE_SIZE;
		String stationString = dataArray[offSet];
		return stationString;
	}

	/**
	 * Reads a block into memory. The original file was broken up into pieces, 
	 * by the splitFile method. The pieces are called 'blocks' and are located 
	 * in the work directory workDir. Each line in the block is the 
	 * concatenation of all the lines in the original data file that go with a
	 * station. This method reads a block into memory as a String[], one String 
	 * per station. After this method runs the field dataArray has all the data
	 * for block number currentBlock.
	 *
	 * @param theBlock, an int, the index of the block. The files for blocks have names
	 * like 0.db, 1.db etc.
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
					recNo
							+ " is not a valid record number. Record number must be in the range 1 to "
							+ dataSize + ". ");
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

		private int currentStation = 1;
		private String fileName;  
		private boolean invalid;
		
		private FileBasedServiceIterator(String fileName) {
			super();
			this.fileName = fileName;
		}
		
		@Override
		public Station next() {
			if(isInvalid()){
				throw new IllegalStateException("Iterator is invalid");
			}
			Station station = null;
			try {
//TODO: The iterator always returns actual stations so change the call to be 
// station =(Station)read(currentStation++, true);
				
//((Marker 3				
@				station =(Station)read(currentStation++, true);
$				station = read(currentStation++);

//}}Marker 3				

			} catch (IOException | RecordNotFoundException e) {
				logger.error("Failed to read station",e);
			} 
			return station;
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
		public void remove() {
			throw new UnsupportedOperationException("Data is read-only");
		}
	}// End of iterator class
	
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
