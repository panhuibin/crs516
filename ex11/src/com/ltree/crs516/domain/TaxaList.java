package com.ltree.crs516.domain;

import java.util.ArrayList;

/**
 * An ArrayList that contains individual Taxa objects. The only reason
 * for this class is to allow the use of the more meaningful method
 * name getNumberofEntries.
 * 
 * @author crs516 development team
 * 
 * @see Taxa.
 *
 */
 @SuppressWarnings("serial")
public class TaxaList extends ArrayList<Taxa> {

	/**
	 * Gets the number of entries
	 * @return the number of entries
	 */
	public int getNumberofEntries() {
		return size();
	}

}
