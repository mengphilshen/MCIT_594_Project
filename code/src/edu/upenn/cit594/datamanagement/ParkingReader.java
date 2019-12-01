package edu.upenn.cit594.datamanagement;

import java.util.List;

import edu.upenn.cit594.data.Parking;

/**
 * The ParkingReader interface declares a method to read all parking input. 
 * This interface is part of the "Data Management" tier. The implementation is determined by the subclass.
 */
public interface ParkingReader {
	
	public List<Parking> getParkingInfo();
	
}
