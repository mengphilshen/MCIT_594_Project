package edu.upenn.cit594.datamanagement;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Map;
import java.util.TreeMap;

/**
 * The PopulationReader class reads the population information from a txt file.
 * This class is part of the "Data Management" tier.
 */
public class PopulationReader {
	
	// Instance variables
	protected String filename;
	
	// The PopulationReader constructor
	public PopulationReader(String filename) {
		this.filename = filename;
	}
	
	// The getDemogaphicInfo method reads all population information into a Map
	public Map<String, Double> getDemogaphicInfo() {
		
		Map<String, Double> populationMap = new TreeMap<>();
				
		try {
			
			Scanner in = new Scanner(new File(filename));
			
			while (in.hasNextLine()) {
				String curLine = in.nextLine();
				String[] tokens = curLine.split(" ");
				
				try {
					String zipcode = tokens[0].trim();
					double population = Double.parseDouble(tokens[1].trim());
					if (!zipcode.matches("[0-9]{5}")) {
						continue;
					}
					populationMap.put(zipcode, population);
				} catch (NullPointerException | NumberFormatException e) {
					continue;
				}
			}
			
			in.close();
			
		} catch (FileNotFoundException e) {

			e.printStackTrace();
			
		}

		return populationMap;
		
	}
	
}
