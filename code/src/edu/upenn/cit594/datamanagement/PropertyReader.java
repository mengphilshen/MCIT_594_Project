package edu.upenn.cit594.datamanagement;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.upenn.cit594.data.Property;

/**
 * The PropertyReader class reads the property information from a CSV file.
 * This class is part of the "Data Management" tier.
 */
public class PropertyReader {
	
	// Instance variables
	protected String filename;
	
	// The PropertyReader constructor
	public PropertyReader(String filename) {
		this.filename = filename;
	}
	
	// The getPropertyInfo method reads all property information into a List of Parking
	public List<Property> getPropertyInfo() {
		
		List<Property> propertyList = new ArrayList<Property>();
		int marketValueIndex = -1;
		int livableAreaIndex = -1;
		int zipcodeIndex = -1;
		
		try {
			
			Scanner in = new Scanner(new File(filename));
			
			String header = in.nextLine();
			String[] labels = header.split(",", -1);			
			for (int i = 0; i < labels.length; i++) {
				if (labels[i].equals("market_value")) {
					marketValueIndex = i;
				}
				if (labels[i].equals("total_livable_area")) {
					livableAreaIndex = i;
				}
				if (labels[i].equals("zip_code")) {
					zipcodeIndex = i;
				}
			}
						
			while (in.hasNextLine()) {
				String curLine = in.nextLine();
				String[] tokens = curLine.split(",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)", -1);                
				try {
					double marketValue = Double.parseDouble(tokens[marketValueIndex].trim());
					double livableArea = Double.parseDouble(tokens[livableAreaIndex].trim());
					String zipcode = tokens[zipcodeIndex].trim().substring(0, 5);
					if (!zipcode.matches("[0-9]{5}")) {
						continue;
					}
					propertyList.add(new Property(marketValue, livableArea, zipcode));	
				} catch (NullPointerException | NumberFormatException | StringIndexOutOfBoundsException e) {
					continue;
				}											
			}
			
			in.close();
			
		} catch (FileNotFoundException e) {

			e.printStackTrace();
			
		}
		
		return propertyList;
		
	} 

}
