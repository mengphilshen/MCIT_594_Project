package edu.upenn.cit594;

import java.io.File;
import java.io.IOException;

import edu.upenn.cit594.datamanagement.ParkingReader;
import edu.upenn.cit594.datamanagement.CSVParkingReader;
import edu.upenn.cit594.datamanagement.JSONParkingReader;
import edu.upenn.cit594.datamanagement.PopulationReader;
import edu.upenn.cit594.datamanagement.PropertyReader;
import edu.upenn.cit594.processor.ParkingProcessor;
import edu.upenn.cit594.processor.PopulationProcessor;
import edu.upenn.cit594.processor.PropertyProcessor;
import edu.upenn.cit594.ui.UserInterface;

/**
 * The Main class starts the application.
 * 
 */
public class Main {
	
	public static void main(String[] args) throws IOException {
			
		// Read command line arguments
		if (args.length != 5) {
			System.out.println("The number of arguments is not correctly specified.");
			System.exit(1);
		}
		
		String parkingFormat = args[0];
		String parkingName = args[1];
		String propertyName = args[2];
		String populationName = args[3];
		String logName = args[4];
		
		if (!parkingFormat.equalsIgnoreCase("csv") && !parkingFormat.equalsIgnoreCase("json")) {
			System.out.println("The format of the input file is not correctly specified.");
			System.exit(1);
		}
		
		File parkingFile = new File(parkingName);
		File propertyFile = new File(propertyName);
		File populationFile = new File(populationName);
		File logFile = new File(logName);
		logFile.createNewFile();
		
		if (!parkingFile.exists() || !parkingFile.canRead() ||
			!propertyFile.exists() || !propertyFile.canRead() ||
			!populationFile.exists() || !populationFile.canRead()) {
			System.out.println("The specified input file does not exist or can not opened for reading.");
			System.exit(1);
		}
				
		// Read the population input
		PopulationReader popReader = new PopulationReader(populationName);
			
		// Read the parking input
		ParkingReader parReader;		
		if (parkingFormat.toLowerCase().equals("json")) {
			parReader = new JSONParkingReader(parkingName);
		} else {
			parReader = new CSVParkingReader(parkingName);
		}	
					
		// Read the property input
		PropertyReader proReader = new PropertyReader(propertyName);

		// Process the population information
		PopulationProcessor popProcessor = new PopulationProcessor(popReader);
		
		// Process the parking information
		ParkingProcessor parProcessor = new ParkingProcessor(parReader);
		
		// Process the property information
		PropertyProcessor proProcessor = new PropertyProcessor(proReader);
		
		// Prompt users to specify the action to be performed
		UserInterface ui = new UserInterface(parProcessor, proProcessor, popProcessor);
		ui.select();
		
	}

}
