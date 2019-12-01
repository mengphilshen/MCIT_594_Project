package edu.upenn.cit594.datamanagement;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import edu.upenn.cit594.data.Parking;

/**
 * The CSVFileReader class reads the parking information from a CSV file.
 * This class is part of the "Data Management" tier.
 */
public class CSVParkingReader implements ParkingReader {
	
	// Instance variables
	protected String filename;	
	
	// The CSVFileReader constructor
	public CSVParkingReader(String filename) {
		this.filename = filename;
	}
	
	// The getParkingInfo method reads all parking information into a List of Parking
	public List<Parking> getParkingInfo() {
		
		List<Parking> parkingList = new ArrayList<Parking>();
		
		try {
			
			Scanner in = new Scanner(new File(filename));
			
			while (in.hasNextLine()) {
				String curLine = in.nextLine();
				String[] tokens = curLine.split(",", -1);
				try {
					Date date = new SimpleDateFormat("YYYY-MM-DD'T'hh:mm:ss'Z'").parse(tokens[0].trim());
					double fine = Double.parseDouble(tokens[1].trim());
					String violation = tokens[2].trim();
					String plate = tokens[3].trim();
					String state = tokens[4].trim();
					String ticket = tokens[5].trim();
					String zipcode = tokens[6].trim();
					if (!zipcode.matches("[0-9]{5}") || !state.matches("[A-Z]{2}")) {
						continue;
					}
					parkingList.add(new Parking(date, fine, violation, plate, state, ticket, zipcode));
				} catch (NullPointerException | NumberFormatException | ParseException e) {
					continue;
				}
			}
			
			in.close();
			
		} catch (FileNotFoundException e) {

			e.printStackTrace();
			
		}
		
		return parkingList;
		
	}

}
