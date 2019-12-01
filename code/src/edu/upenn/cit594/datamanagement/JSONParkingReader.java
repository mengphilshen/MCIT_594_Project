package edu.upenn.cit594.datamanagement;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import edu.upenn.cit594.data.Parking;

/**
 * The JSONParkingReader class reads the parking information from a JSON file.
 * This class is part of the "Data Management" tier.
 */
public class JSONParkingReader implements ParkingReader {
	
	// Instance variables
	protected String filename;	
	
	// The JSONParkingReader constructor
	public JSONParkingReader(String filename) {
		this.filename = filename;
	}
	
	// The getParkingInfo method reads all parking information into a List of Parking
	public List<Parking> getParkingInfo() {
		
		List<Parking> parkingList = new ArrayList<Parking>();		
		JSONParser parser = new JSONParser();		
		JSONArray parking;
		
		try {
			
			parking = (JSONArray) parser.parse(new FileReader(filename));
			Iterator<?> iter = parking.iterator();
			
			while (iter.hasNext()) {
				JSONObject curLine = (JSONObject) iter.next();					
				try {
					String Date = (String) curLine.get("date").toString().trim();
					Date date = new SimpleDateFormat("YYYY-MM-DD'T'hh:mm:ss'Z'").parse(Date);
					String Fine = (String) curLine.get("fine").toString().trim();
					double fine = Double.parseDouble(Fine);
					String violation = (String) curLine.get("violation").toString().trim();
					String plate = (String) curLine.get("plate_id").toString().trim();
					String state = (String) curLine.get("state").toString().trim();
					String ticket = (String) curLine.get("ticket_number").toString().trim();
					String zipcode = (String) curLine.get("zip_code").toString().trim();		
					if (!zipcode.matches("[0-9]{5}") || !state.matches("[A-Z]{2}")) {
						continue;
					}
					parkingList.add(new Parking(date, fine, violation, plate, state, ticket, zipcode));
				} catch (NullPointerException | NumberFormatException | java.text.ParseException e) {
					continue;
				}
			}
						
		} catch (FileNotFoundException e) {

			e.printStackTrace();
			
		} catch (IOException e) {

			e.printStackTrace();
			
		} catch (ParseException e) {

			e.printStackTrace();
			
		}
		
		return parkingList;
		
	}
	
}
