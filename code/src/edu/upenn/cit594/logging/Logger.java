package edu.upenn.cit594.logging;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * The Logger class writes runtime arguments and user inputs to the log file.
 * This class is part of the "Logging" tier.
 */
public class Logger {
	
	// Instance variables
	protected static String defaultFile = "defaultlog.txt";
	protected PrintWriter out;
	
	// Private Logger constructor
	private Logger(String defaultFile){		
		try {
			File lFile = new File(defaultFile);
			out = new PrintWriter(lFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Private singleton instance
	private static Logger instance = null;
	
	// Static setFilename method sets default file name
	public static void setFilename(String filename) {
		defaultFile = filename;
	}
	
	// Static getInstance method accesses the singleton instance 
	public static Logger getInstance() {
		if (instance == null) {
			instance = new Logger(defaultFile);
		}		
		return instance;
	}
	
	// The printRuntime writes runtime arguments to the log file.
	public void printRuntime(String[] args) {
		
		out.print(System.currentTimeMillis());
		for (String val:args) {			
			out.print(" " + val);
		}
		out.println();		
		out.flush();
		
	}
	
	// The printFile writes the name of the input file to the log file.
	public void printFile(String filename) {
		
		out.println(System.currentTimeMillis() + " File: " + filename);
		out.flush();
		
	}
	
	// The printChoice writes the user's choice to the log file.
	public void printChoice(String option) {
		
		out.println(System.currentTimeMillis() + " Choice: " + option);
		out.flush();
		
	}
	
	// The printZIP writes the specified ZIP code to the log file.
	public void printZIP(String option) {
		
		out.println(System.currentTimeMillis() + " ZIP Code: " + option);
		out.flush();
		
	}
	
}
