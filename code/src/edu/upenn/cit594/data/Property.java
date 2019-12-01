package edu.upenn.cit594.data;

/**
 * The Property class represents property value information which includes market value, total livable area, and ZIP code.
 * This class comprises the "Data" tier. It can be used across tiers.
 */
public class Property {
	
	// Instance variables
	protected double marketValue;
	protected double livableArea;
	protected String zipcode;
	
	// The Property constructor
	public Property(double marketValue, double livableArea, String zipcode) {
		this.marketValue = marketValue;
		this.livableArea = livableArea;
		this.zipcode = zipcode;
	}

	// The Getters and Setters method
	public double getMarketValue() {
		return marketValue;
	}

	public void setMarketValue(double marketValue) {
		this.marketValue = marketValue;
	}

	public double getLivableArea() {
		return livableArea;
	}

	public void setLivableArea(double livableArea) {
		this.livableArea = livableArea;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
}
