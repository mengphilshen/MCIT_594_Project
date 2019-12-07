package edu.upenn.cit594.processor;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import edu.upenn.cit594.data.Parking;
import edu.upenn.cit594.data.Property;
import edu.upenn.cit594.datamanagement.ParkingReader;
import edu.upenn.cit594.datamanagement.PropertyReader;

/**
 * The ValueToFineProcessor class calculates the property value per capita to fine per capita ratio by zip code.
 * This class is part of the "Processor" tier.
 */
public class ValueToFineProcessor {
	
	protected Map<String, Double> finePerCapita;
	protected Map<String, Double> valuePerCapita;
	
	// The ParkingProcessor constructor
	public ValueToFineProcessor(Map<String, Double> finePerCapita, Map<String, Double> valuePerCapita) {
		this.finePerCapita = finePerCapita;
		this.valuePerCapita = valuePerCapita;
	}
	
	// The calculateValueToFineScore method value to fine ratio per capita for each ZIP code
	public Map<String, Double> calculateValueToFineScore() {
		Map<String, Double> valueToFine = new TreeMap<>();
		
		for (Map.Entry<String, Double> entry : finePerCapita.entrySet()) {
			valueToFine.put(entry.getKey(), valuePerCapita.get(entry.getKey()) / finePerCapita.get(entry.getKey()));
		}
		return valueToFine;
	}
	
	// The sortValueToFine method sort the valueToFine map by ratio
	public <K,V extends Comparable<? super V>> Map<String, Double> sortValueToFine() {
		Map<String, Double> valueToFineMap = this.calculateValueToFineScore();
		List<Map.Entry<String, Double>> list = new ArrayList<>(valueToFineMap.entrySet());
		list.sort(Map.Entry.comparingByValue());
		
		Map<String, Double> sortedValueToFineMap = new TreeMap<>();
		for (Map.Entry<String, Double> entry : list) {
			sortedValueToFineMap.put(entry.getKey(), entry.getValue());
		}
		
		return sortedValueToFineMap;
	}

}
