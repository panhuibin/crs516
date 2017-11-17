package com.ltree.crs516.data;

public final  class DataConstants {
	
	/**
	 * This class should not be instantiated.
	 */
	private DataConstants(){}
	
	public static String WORKSPACE = "/Users/span/Dropbox/LearningTree/HOCD/crs516/sourcecode/";
	
	/**
	 * Location of the code tables. 
	 */
	public static final String CODE_TABLES_DIR = WORKSPACE+"codeTables/";
	
	/**
	 * Location of the data files. 
	 */
	public static final String DATA_DIR = WORKSPACE+"data/";
	
	/**
	 * Location of the serialized data files 
	 */
	public static final String SER_DATA_DIR = WORKSPACE+"serdata/";
	
	/**
	 * The work directory. To avoid out-of-memory errors, data files will be
	 * split into manageable pieces (blocks) and stored in this directory.
	 * The blocks have names like 0.db, 1.db,...
	 * 
	 */
	public static final String WORK_DIR = WORKSPACE+"work/";
	
	public static final int SECOND = 1000;
	
	public static final int MINUTE = 60*SECOND;
	
	public static final int HOUR = 60*MINUTE;

}
