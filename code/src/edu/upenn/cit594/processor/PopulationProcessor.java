package edu.upenn.cit594.processor;

import java.util.Map;

import edu.upenn.cit594.datamanagement.PopulationReader;

/**
 * The PopulationProcessor class processes the population information.
 * This class is part of the "Processor" tier.
 */
public class PopulationProcessor {
	
	// Instance variables
	protected PopulationReader popReader;
	protected Map<String, Double> populations;
	
	// The PopulationProcessor constructor
	public PopulationProcessor(PopulationReader popReader) {
		this.popReader = popReader;
		populations = popReader.getDemogaphicInfo();
	}
	
	// The caculateTotalPopulation method computes total population for all ZIP codes
	public void caculateTotalPopulation() {
		
		int totalPopulation = 0;
		
		for (Double val:populations.values()) {
			totalPopulation += val; 
		}
		
		System.out.println("Total Population for All ZIP codes = " + totalPopulation);
	}
	
}
