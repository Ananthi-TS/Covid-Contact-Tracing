package com.covid.main;


/**
 * This class is used to create instances of the recorded neighbor devices
 */
public class Contact {

	String hash;
	int date,duration;
	
	/**
	 * Constructor for the class
	 * 
	 * @param hash- Hash of the neighbor device
	 * @param date- Date of interaction
	 * @param duration- Duration of interaction
	 */
	public Contact(String hash, int date, int duration) {
		this.hash = hash;
		this.date = date;
		this.duration = duration;
	}

	//Getter methods for hash, date and duration values
	public String getHash() {
		return hash;
	}

	public int getDate() {
		return date;
	}

	public int getDuration() {
		return duration;
	}
	
	
}
