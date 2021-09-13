package com.covid.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Logger;


/**
 * This is a public class that is used to create instance of Mobile devices
 */
public class MobileDevice {

	String hashValuefin="";
	ArrayList<Contact> contactList=null;
	Government govtObj=null;
	String positiveTestHash="";
	
	
	private final static Logger LOGGER = Logger.getLogger(MobileDevice.class.getName()); 
	
	
	/**
	 * This is a public method to get the hash value created for the mobile object
	 * @return - Returns string value containing hash of the mobile device.
	 */
	public String getHashValuefin() {
		return hashValuefin;
	}
	
	/**
	 * This is a public method to get the size of contact list.
	 * This method is created for testing purposes.
	 * @return - Number of contacts recorded so far by the mobile object before synchronizing.
	 */
	public int getContactListSize() {
		if(contactList==null) {
			return 0;
		}else {
			return contactList.size();
		}
	}
	
	/**
	 * This is a public method to get the test hash value of the mobile device.
	 * This method is created for testing purposes.
	 * @return - Returns string value containing test hash of the mobile device.
	 */
	public String getTestHash() {
		return positiveTestHash;
	}
	
	/**
	 * Constructor for the MobileDevice class
	 * The Constructor accepts mobile configuration file and government object as parameters.
	 * A unique hash value is created based on the mobile device name and corresponding network address.
	 * 
	 * @param configurationFile - Mobile device configuration file with deviceName and networkAddress parameters. 
	 * @param contactTracer - Instance of Government class
	 * @exception - ApplicationValidationException - In case of any input validations or any specific exceptions.
	 */
	public MobileDevice( String configurationFile, Government contactTracer) throws ApplicationValidationException  {
		if((configurationFile!=null)) {	//validation check for null configuration file
			if(!(configurationFile.trim().equals(""))) {  //validation check for empty file
				if(contactTracer!=null) { //validation check for null government object
				
					try {
						FileInputStream fileStream=new FileInputStream(configurationFile);
						Properties property=new Properties();  //Parsing the given file to property file for easy accessing
						property.load(fileStream);
						String deviceName=property.getProperty("deviceName"); //device name from configuration file
						String networkAddress=property.getProperty("networkAddress"); //network address from configuration file
						if((deviceName==null) || (deviceName.trim().equals(""))  ||   (networkAddress==null) || (networkAddress.trim().equals(""))  ) { //if parameter is missing in the property file;it returns null
							throw new ApplicationValidationException("Missing/empty parameters in configuration file"); //validation for device and network address
						}
						hashValuefin=Integer.toString((deviceName+networkAddress).hashCode()); //creating hash value from device name and network address
						contactList=new ArrayList<>(); //instantiating global list
						govtObj=contactTracer;   //Government object passed as parameter is stored in the global variable
					} catch (FileNotFoundException e) {
						throw new ApplicationValidationException("Cannot find the device configuration file");
					} catch (IOException e) {
						throw new ApplicationValidationException("Error while loading configuration file");
					}
				}else {
					throw new ApplicationValidationException("Government object cannot be null"); }
			}else {
				throw new ApplicationValidationException("Configuration file cannot be empty");	}
		}else {
			throw new ApplicationValidationException("Configuration file cannot be null");
		}
		
		LOGGER.info("Device connected to the application"); 
	}
	
	/**
	 * This is a public method which records the neighbor devices interacted with this mobile device
	 * The method gets the hash value of neighbor device, date of interaction and duration of interaction
	 * Valid neighbor interactions are as Contact objects in a global list and pushed to goverment's database server during synchronize() method
	 * 
	 * @param individual - Hash value of the neighbor device is passed as string value
	 * @param date - Date of neighbor interaction with the mobile device
	 * @param duration - Duration of neighbor interaction with the mobile device
	 * @exception - ApplicationValidationException - In case of any input validations or any specific exceptions.
	 */
	public void recordContact(String individual,int date, int duration) throws ApplicationValidationException {
		
		if((individual!=null)) { //validation check for null individual hash
			if(!(individual.trim().equals(""))) { //validation check for empty individual hash
				if(Utility.checkDate(date)) {   //validation check if passed date is not a future date
					if((duration>0) && (duration<=1440)) {  //validation check if duration is not negative and is within 1440 [1day=1440 minutes]
						contactList.add(new Contact(individual, date, duration)); //valid contacts are stored in a global list of contacts
					}else {
						throw new ApplicationValidationException("Invalid duration");	}
				}else {
					throw new ApplicationValidationException("Invalid date");	}
			}else {
				throw new ApplicationValidationException("Individual value cannot be empty");	}
		}else {
			throw new ApplicationValidationException("Individual value cannot be null");
		}
		
		LOGGER.info("Recorded neighbour interaction");		
	}
	
	
	/**
	 * This is a public method which records the test hash value passed by the user
	 * Valid test hash is stored in global variable and is pushed to goverment's database server during synchronize() method
	 * 
	 * @param testHash - Test hash value passed by the device
	 * @exception - ApplicationValidationException - In case of any input validations or any specific exceptions.
	 */
	public void positiveTest( String testHash ) throws ApplicationValidationException{
			
		if((testHash!=null)) { //null validation check for test hash passed
			if(!(testHash.trim().equals(""))) {  //validation check to see if the value passed is empty
				positiveTestHash=testHash;  //stored in a global variable
			}
			else {
				throw new ApplicationValidationException("Test hash cannot be empty");	}
		}else {
			throw new ApplicationValidationException("Test hash cannot be null");
		}	
		LOGGER.info("Recorded test hash");	
	}
	
	/**
	 * This is a public method to synchronize the mobile
	 * The contact details stored in the list and the test hash value are iterated and are written into an XML file
	 * An XML file is created for each mobile device in the name of its hash inside the project under a directory called Mobile_devices
	 * The XML file is passed to the government's mobileContact() method and values are pushed to the database server from that method.
	 * 
	 * @return - boolean - True if the mobile device has any interactions with covid-infected persons in last 14 days
	 * @exception - ApplicationValidationException - In case of any input validations or any specific exceptions.
	 */
	public boolean synchronize() throws ApplicationValidationException {
		boolean returnValue=false;
		try {
			String fileName="Mobile_devices\\"+hashValuefin+".xml";  
			new File("Mobile_devices").mkdirs(); //Creates a directory called Mobile_devices if it doesn't exists 
			new File(fileName).createNewFile(); //Creates file if it doesn't exists
			new PrintWriter(fileName).close(); //Emptying the file before writing
			Files.write(Paths.get(fileName), "<mobile>\n".getBytes(), StandardOpenOption.APPEND);
			for(Contact contactInfo:contactList) {  //Iterating the global list of Contacts and updating the XML file.
				String newStr="\t<device>\n"+
						"\t\t<hash>"+contactInfo.getHash()+"</hash>\n"+
						"\t\t<date>"+contactInfo.getDate()+"</date>\n"+
						"\t\t<time>"+contactInfo.getDuration()+"</time>\n"+
						"\t</device>\n";

				Files.write(Paths.get(fileName), newStr.getBytes(), StandardOpenOption.APPEND);
			}
			String newString= "\t<positive-test>\n"+
					"\t\t<test-hash>"+positiveTestHash+"</test-hash>\n"+
					"\t</positive-test>\n"+
					"</mobile>\n";
			Files.write(Paths.get(fileName), newString.getBytes(), StandardOpenOption.APPEND);
			
			try {
				returnValue= govtObj.mobileContact(hashValuefin, fileName);  //passing the XML values and hash value to the government method
				contactList.clear(); //if values are updated in DB server, the list is cleared 
				positiveTestHash="";
			} catch (ApplicationValidationException e) {
				if(e.getMessage().contains("Exception occured while creating SQL connection")) {
					throw new ApplicationValidationException(e.getMessage()+" Please synchronize after sometime");
				}
				throw e;
			}
			
		} catch (IOException e) {
			LOGGER.warning("Exception occured while syncing to government network");
		} 
		LOGGER.info("Mobile Synchronized with Government");
		return returnValue;
		
	}
	
	
	
	
}
