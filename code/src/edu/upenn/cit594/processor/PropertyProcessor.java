package edu.upenn.cit594.processor;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import edu.upenn.cit594.data.Property;
import edu.upenn.cit594.datamanagement.PropertyReader;

/**
 * The PropertyProcessor class
 * This class is part of the "Processor" tier.
 */
public class PropertyProcessor {
	
	// Instance variables
	protected PropertyReader proReader;
	protected List<Property> property;
	
	// The PropertyProcessor constructor
	public PropertyProcessor(PropertyReader proReader) {
		this.proReader = proReader;
		property = proReader.getPropertyInfo();
	}
	
	// The SumByCaculation interface
	public interface SumByCaculation {			
		public Map<String, Double> sumBy();			
	}
	
	// The ValueSumBy class
	public class ValueSumBy implements SumByCaculation {
		public Map<String, Double> sumBy() {
			Map<String, Double> map = property.stream().collect(Collectors.groupingBy(Property::getZipcode, Collectors.summingDouble(Property::getMarketValue)));
			return map;
		};
	}
	
	// The AreaSumBy class
	public class AreaSumBy implements SumByCaculation {
		public Map<String, Double> sumBy() {
			Map<String, Double> map = property.stream().collect(Collectors.groupingBy(Property::getZipcode, Collectors.summingDouble(Property::getLivableArea)));
			return map;
		};
	}
	
	// The calcuateAverage method computes average for each ZIP code
	public Map<String, Double> calcuateAverage(SumByCaculation by) {
		
		Map<String, Double> average = new TreeMap<>();
		
		Map<String, Double> map1 = by.sumBy();
		Map<String, Long> map2 = property.stream().collect(Collectors.groupingBy(Property::getZipcode, Collectors.counting()));
		
		for (String zip : map1.keySet()) {
			double numerator = map1.get(zip);
			long denominator = map2.get(zip);
			double result = 0.0;
			if (!(Math.abs(denominator) < 2 * Double.MIN_VALUE)) {
				result = numerator / denominator;
			}
			average.put(zip, result);
		}
		
		return average;
		
	}
	
	// The calcuateAverageValue method computes average market value for each ZIP code
	public Map<String, Double> calcuateAverageValue() {
		return calcuateAverage(new ValueSumBy());
	}
	
	// The calcuateAverageArea method computes average livable area for each ZIP code
	public Map<String, Double> calcuateAverageArea() {
		return calcuateAverage(new AreaSumBy());
	}
		
	// The calculateValuePerCapita method computes total market value per capita for each ZIP code
	public Map<String, Double> calculateValuePerCapita(Map<String, Double> populationMap) {	
		
		Map<String, Double> valuePerCapita = new TreeMap<>();
		
		Map<String, Double> valueMap = property.stream().collect(Collectors.groupingBy(Property::getZipcode, Collectors.summingDouble(Property::getMarketValue)));
		
		Set<String> commonZIP = new TreeSet<String>(valueMap.keySet()); 
		commonZIP.retainAll(populationMap.keySet());
		
		for (String zip : commonZIP) {		
			double numerator = valueMap.get(zip);
			double denominator = populationMap.get(zip);
			double result = 0.0;
			if (!(Math.abs(denominator) < 2 * Double.MIN_VALUE)) {
				result = numerator / denominator;
			}					
			valuePerCapita.put(zip, result);
		}
		
		return valuePerCapita;
		
	}
	
}
