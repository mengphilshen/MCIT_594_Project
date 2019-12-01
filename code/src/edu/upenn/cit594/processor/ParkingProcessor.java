package edu.upenn.cit594.processor;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import edu.upenn.cit594.data.Parking;
import edu.upenn.cit594.datamanagement.ParkingReader;

/**
 * The ParkingProcessor class processes the parking information.
 * This class is part of the "Processor" tier.
 */
public class ParkingProcessor {
	
	// Instance variables
	protected ParkingReader parReader;
	protected List<Parking> parking;
	
	// The ParkingProcessor constructor
	public ParkingProcessor(ParkingReader parReader) {
		this.parReader = parReader;
		parking = parReader.getParkingInfo();
	}
	
	// The calculateFinePerCapita method computes total fines per capita for each ZIP code
	public Map<String, Double> calculateFinePerCapita(Map<String, Double> populationMap) {
		
		Map<String, Double> finePerCapita = new TreeMap<>();
		List<Parking> PAParking = new ArrayList<Parking>();
		
		for (Parking p : parking) {
			if (!p.getZipcode().isEmpty() && p.getState().equals("PA")) {
				PAParking.add(p);
			}
		}
		
		Map<String, Double> fineMap = PAParking.stream().collect(Collectors.groupingBy(Parking::getZipcode, Collectors.summingDouble(Parking::getFine)));
		
		Set<String> commonZIP = new TreeSet<String>(fineMap.keySet()); 
		commonZIP.retainAll(populationMap.keySet());
		
		for (String zip : commonZIP) {		
			double numerator = fineMap.get(zip);
			double denominator = populationMap.get(zip);
			double result = 0.0;
			if (!(Math.abs(denominator) < 2 * Double.MIN_VALUE)) {
				result = numerator / denominator;
			}
			finePerCapita.put(zip, result);
		}
		
		return finePerCapita;
		
	}

}
