package edu.upenn.cit594.ui;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import edu.upenn.cit594.processor.ParkingProcessor;
import edu.upenn.cit594.processor.PopulationProcessor;
import edu.upenn.cit594.processor.PropertyProcessor;
import edu.upenn.cit594.logging.Logger;

/**
 * The UserInterface class interacts with users and displays results
 * This class is part of the "User Interface" tier.
 */
public class UserInterface {
	
	// Instance variables
	protected ParkingProcessor parProcessor;
	protected PropertyProcessor proProcessor;
	protected PopulationProcessor popProcessor;
	protected Logger log;
	protected Scanner in;
	protected DecimalFormat df1;
	protected DecimalFormat df2;
		
	// The UserInterface constructor
	public UserInterface(ParkingProcessor parProcessor, PropertyProcessor proProcessor, PopulationProcessor popProcessor, Logger log) {
		this.parProcessor = parProcessor;
		this.proProcessor = proProcessor;
		this.popProcessor = popProcessor;
		this.log = log;
		in = new Scanner(System.in);
		df1 = new DecimalFormat("0.0000");
		df1.setRoundingMode(RoundingMode.DOWN);
		df2 = new DecimalFormat("0");
		df2.setRoundingMode(RoundingMode.DOWN);
	}
	
	// The select method prompts the user to make choices
	public void select() {
		
		System.out.println("===============================================================================================================");
	    System.out.println("|                             MENU SELECTION                                                                  |");
	    System.out.println("===============================================================================================================");
	    System.out.println("| Options:                                                                                                    |");
	    System.out.println("|    0 - Exit                                                                                                 |");
	    System.out.println("|    1 - Display total population for all ZIP codes                                                           |");
	    System.out.println("|    2 - Display total fines per capita for each ZIP codes                                                    |");
	    System.out.println("|    3 - Display average market value for a specified ZIP code                                                |");
	    System.out.println("|    4 - Display average livable area for a specified ZIP code                                                |");
	    System.out.println("|    5 - Display total market value per capita for a specified ZIP code                                       |");
	    System.out.println("|    6 - Display the correlation coefficient between total fines per capita and total market value per capita |");
	    System.out.println("===============================================================================================================");
	    
	    boolean quit = false;
	    do {
	    	System.out.println("Select option: "); 
	    	String input = in.nextLine();
	    	log.printChoice(input);	    	
	    	int choice = -1;
	    	if (input.matches("[0-6]{1}")) {	    		
	    		choice = Integer.parseInt(input);	    		
	    	} else {
	    		System.out.println("Abort! Invalid selection. Please select an integer between 0-6.");
	    		System.exit(1);
	    	}	    	
	    	switch (choice) {
	    	case 1:
	    		System.out.println("You've selected Option 1.");
	    		displayTotalPopulation();
	    		break;
	    	case 2:
	    		System.out.println("You've selected Option 2.");
	    		displayFinePerCapita();
	    		break;
	    	case 3:
	    		System.out.println("You've selected Option 3.");
	    		displayAverageValue();
	    		break;
	    	case 4:
	    		System.out.println("You've selected Option 4.");
	    		displayAverageArea();
	    		break;
	    	case 5:
	    		System.out.println("You've selected Option 5.");
	    		displayValuePerCapita();
	    		break;
	    	case 6:
	    		System.out.println("You've selected Option 6.");
	    		displayCorrlation();
	    		break;
	    	case 0:
	    		System.out.println("You've selected Option 0.");
	    		quit = true;
	    		break;
	    	default:
	    		System.out.println("Abort! Invalid selection. Please select an integer between 0-6.");
	    		System.exit(1);
	    	}
	    } while (!quit);
	    System.out.println("Bye-bye!");
	    System.exit(1);
	    
	}
		
	// The displayTotalPopulation method displays the total population for all ZIP codes
	public void displayTotalPopulation() {
		
		int totalPopulation = popProcessor.caculateTotalPopulation();				
		System.out.println("Total population for all ZIP codes = " + totalPopulation);
		
	}
	
	// The displayFinePerCapita method displays the total fines per capita for each ZIP code
	public void displayFinePerCapita() {
		
		Map<String, Double> finePerCapita = parProcessor.calculateFinePerCapita(popProcessor.getPopulation());	
		System.out.println("The below are the total fines per capita for each ZIP code: " +  finePerCapita.size() + " records");
		for (Map.Entry<String, Double> i : finePerCapita.entrySet()) {
			if (!(Math.abs(i.getValue()) < 2 * Double.MIN_VALUE))
			System.out.println(i.getKey() + " " + df1.format(i.getValue()));
		}
			
	}
	
	// The displayAverageValue method displays the average market value for a specified ZIP codes
	public void displayAverageValue() {
		
		System.out.println("Enter the ZIP code: ");
		String input = in.nextLine();
		log.printZIP(input);
		double output = 0.0;
		Map<String, Double> averageValue = proProcessor.calcuateAverageValue();	
		System.out.println("The below is the average residential market value for " +  input + ": ");
		for (String zip : averageValue.keySet()) {
			if (zip.equals(input)) {
				output = averageValue.get(zip);
			}
		}
		System.out.println(df2.format(output));
		
	}
	
	// The displayAverageArea method displays the average livable area for a specified ZIP code
	public void displayAverageArea() {
		
		System.out.println("Enter the ZIP code: ");
		String input = in.nextLine();
		log.printZIP(input);
		double output = 0.0;
		Map<String, Double> averageArea = proProcessor.calcuateAverageArea();	
		System.out.println("The below is the average residential livable area for " +  input + ": ");
		for (String zip : averageArea.keySet()) {
			if (zip.equals(input)) {
				output = averageArea.get(zip);
			}
		}
		System.out.println(df2.format(output));
		
	}
	
	// The displayValuePerCapita method displays the total market value per capita for a specified ZIP code
	public void displayValuePerCapita() {
		
		System.out.println("Enter the ZIP code: ");
		String input = in.nextLine();
		log.printZIP(input);
		double output = 0.0;
		Map<String, Double> valuePerCapita = proProcessor.calculateValuePerCapita(popProcessor.getPopulation());	
		System.out.println("The below is the total residential market value per capita for " +  input + ": ");
		for (String zip : valuePerCapita.keySet()) {
			if (zip.equals(input)) {
				output = valuePerCapita.get(zip);
			}
		}
		System.out.println(df2.format(output));
		
	}
	
	// The displayCorrelation method displays the correlation coefficient between total fines per capita and total market value per capita
	public void displayCorrlation() {
		
		Map<String, Double> finePerCapita = parProcessor.calculateFinePerCapita(popProcessor.getPopulation());
		Map<String, Double> valuePerCapita = proProcessor.calculateValuePerCapita(popProcessor.getPopulation());	
		Set<String> commonZIP = new TreeSet<String>(finePerCapita.keySet()); 
		commonZIP.retainAll(valuePerCapita.keySet());
		double n = commonZIP.size();
		double xSum = 0.0;
		double ySum = 0.0;
		double xySum = 0.0;
		double x2Sum = 0.0;
		double y2Sum = 0.0;
		double correlation = 0.0;
		for (String zip : commonZIP) {		
			double x = finePerCapita.get(zip);
			double y = valuePerCapita.get(zip);
			xSum += x;
			ySum += y;
			xySum += x*y;
			x2Sum += x*x;
			y2Sum += y*y;
		}
		correlation = (n*xySum-xSum*ySum) / Math.sqrt((n*x2Sum-xSum*xSum)*(n*y2Sum-ySum*ySum));	
		System.out.println("The Pearson’s correlation coefficient between total fines per capita and total market value per capita is " + df1.format(correlation));
		
	}
			
}
