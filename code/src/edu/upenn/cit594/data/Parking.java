package edu.upenn.cit594.data;

import java.util.Date;

/**
 * The Parking class represents parking violation information
 * This class comprises the "Data" tier. It can be used across tiers.
 */
public class Parking {
	
	// Instance variables
	protected Date date;
	protected double fine;
	protected String violation;
	protected String plate;
	protected String state;
	protected String ticket;
	protected String zipcode;
	
	// The Parking constructor
	public Parking(Date date, double fine, String violation, String plate, String state, String ticket, String zipcode) {
		this.date = date;
		this.fine = fine;
		this.violation = violation;
		this.plate = plate;
		this.state = state;
		this.ticket = ticket;
		this.zipcode = zipcode;
	}

	// The Getters and Setters method 
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getFine() {
		return fine;
	}

	public void setFine(double fine) {
		this.fine = fine;
	}

	public String getViolation() {
		return violation;
	}

	public void setViolation(String violation) {
		this.violation = violation;
	}

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
			
}
