package edu.upenn.cit594;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import edu.upenn.cit594.data.Parking;
import edu.upenn.cit594.data.Property;
import edu.upenn.cit594.datamanagement.ParkingReader;
import edu.upenn.cit594.datamanagement.CSVParkingReader;
import edu.upenn.cit594.datamanagement.JSONParkingReader;
import edu.upenn.cit594.datamanagement.PopulationReader;
import edu.upenn.cit594.datamanagement.PropertyReader;
import edu.upenn.cit594.processor.ParkingProcessor;
import edu.upenn.cit594.processor.PopulationProcessor;
import edu.upenn.cit594.processor.PropertyProcessor;

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
		
		System.out.println("The command line arguments are: ");
		for (String val:args) {
			System.out.println(val);
        }
		
		// Read the population input
		PopulationReader popReader = new PopulationReader(populationName);
		Map<String, Double> population = popReader.getDemogaphicInfo();		
		System.out.println("population.size() = " + population.size());		
		
		// Read the parking input
		ParkingReader parReader;		
		if (parkingFormat.toLowerCase().equals("json")) {
			parReader = new JSONParkingReader(parkingName);
		} else {
			parReader = new CSVParkingReader(parkingName);
		}	
		List<Parking> parking = parReader.getParkingInfo();				
		System.out.println("parking.size() = " + parking.size());
		
		// Read the property input
		PropertyReader proReader = new PropertyReader(propertyName);
		List<Property> property = proReader.getPropertyInfo();	
		System.out.println("property.size() = " + property.size());

		// Process the population information
		PopulationProcessor popProcessor = new PopulationProcessor(popReader);
		popProcessor.caculateTotalPopulation();
		
		// Process the parking information
		ParkingProcessor parProcessor = new ParkingProcessor(parReader);
		Map<String, Double> finePerCapita = parProcessor.calculateFinePerCapita(population);
		System.out.println("finePerCapita.size() = " + finePerCapita.size());		
		for (Map.Entry<String, Double> m : finePerCapita.entrySet()) {
			System.out.println(m.getKey() + ": " + m.getValue());
		}
		
		// Process the property information
		PropertyProcessor proProcessor = new PropertyProcessor(proReader);
		Map<String, Double> valuePerCapita = proProcessor.calculateValuePerCapita(population);
		System.out.println("valuePerCapita.size() = " + valuePerCapita.size());		
		for (Map.Entry<String, Double> m : valuePerCapita.entrySet()) {
			System.out.println(m.getKey() + ": " + m.getValue());
		}
		
		Map<String, Double> averageValue = proProcessor.calcuateAverageValue();	
		System.out.println("averageValue.size() = " + averageValue.size());	
		for (Map.Entry<String, Double> m : averageValue.entrySet()) {
			System.out.println(m.getKey() + ": " + m.getValue());
		}
		
		Map<String, Double> averageArea = proProcessor.calcuateAverageArea();	
		System.out.println("averageArea.size() = " + averageArea.size());	
		for (Map.Entry<String, Double> m : averageArea.entrySet()) {
			System.out.println(m.getKey() + ": " + m.getValue());
		}
				
	}

}
