package com.covid.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * This is a public class that is used to create instance of Government network
 */
public class Government {

	String configurationFile="";
	
	private final static Logger LOGGER = Logger.getLogger(MobileDevice.class.getName());
	
	
	/**
	 * Constructor for the Government class
	 * The Constructor accepts database configuration file as parameter.
	 * Government object is created only if valid database property file with valid parameters is passed
	 * 
	 * @param configFile - Database configuration file containing database properties 
	 * @param contactTracer - Instance of Government class
	 * @exception - ApplicationValidationException - In case of any input validations or any specific exceptions.
	 */
	public Government(String configFile) throws ApplicationValidationException  {
		
		if((configFile!=null)) { //validation check for null database file
			if(!(configFile.trim().equals(""))) {//validation check for empty database file
				Connection sqlConnection=null;
				try {
					sqlConnection=createSQLConnection(configFile); //private method that checks the database properties and creates a DB connection 
					configurationFile=configFile; //If connection is created, the property file path is saved in a global variable, that can be accessed by other methods
				} catch (ApplicationValidationException e) {
					throw e;
				}finally {
					if(sqlConnection!=null) {
						try {
							sqlConnection.close(); 
						} catch (SQLException e) {
							//throw new ApplicationValidationException("Exception occured while closing the connection");
						}
					}
				}
			}
			else {
				throw new ApplicationValidationException("Database configuration file cannot be empty");	}
		}else {
			throw new ApplicationValidationException("Database configuration file cannot be null");
		}	
		LOGGER.info("Government object instantiated");
		
	}
	
	/**
	 * This method accepts XML file as parameter along with hash value of mobile device contacted 
	 * The method is made as protected to avoid unnecessary access
	 * The mobile device is registered in the database table, if it doesn't exist already
	 * The neighboring devices recorded by the mobile device along with the test value if passed, is saved in the government server.
	 * 
	 * @param initiator - Hash value of the mobile device which calls this method
	 * @param contactInfo - Contacts recorded by the mobile device which calls this method 
	 * @return - Returns true if the mobile device passed has any interactions with covid-infected persons in last 14 days
	 * @exception - ApplicationValidationException - In case of any input validations or any specific exceptions.
	 */
	protected boolean mobileContact( String initiator, String contactInfo ) throws ApplicationValidationException {
		
		if( (initiator!=null) && !(initiator.trim().equals("")) && (contactInfo!=null) && !(contactInfo.trim().equals(""))  )
		{ //validation check for null and empty parameters
			Connection sqlConnection=null;
			Statement statement=null;
			ResultSet result=null;
			
			try {
				sqlConnection=createSQLConnection(configurationFile); //SQL connection is created
				sqlConnection.setAutoCommit(false);
				statement=sqlConnection.createStatement();
				result=statement.executeQuery("select * from fp_mobile_dtls where mobile_name="+initiator);
				if(!(result.next()) ){
					statement.execute("INSERT INTO fp_mobile_dtls(mobile_name,registered_date) values ('"+initiator+"',CURDATE());"); //mobile device is registered if it doesn't exist
					sqlConnection.commit();
				}
				saveContactAndTestHash(initiator,contactInfo); //private method- contacts and test hash updated in the db
				return checkIfInfected(initiator); //checks for covid interaction
			} catch (ApplicationValidationException e) {
				throw e;
			} catch (SQLException e) {
				if(sqlConnection!=null) {
					try {
						sqlConnection.rollback();
					} catch (SQLException e1) {
						LOGGER.warning("Exception during rollback");
					}
				}
				//throw new ApplicationValidationException("Exception occured while creating SQL connection");
			}finally {
					try {
						
						if(result!=null) {
							result.close();
						}
						
						if(statement!=null) {
							statement.close();
						}
						
						if(sqlConnection!=null) {
							sqlConnection.close();
						}
						
					} catch (SQLException e) {
						//throw new ApplicationValidationException("Exception occured while closing the connection");
					}
				}
			}
		return false;
		
	}

	/**
	 * This is a private method to check if the mobile hash has any recent covid interactions 
	 * 
	 * @param initiator - Hash value of the mobile device
	 * @return - Returns true if the mobile device passed has any interactions with covid-infected persons in last 14 days, false otherwise
	 * @exception - ApplicationValidationException - In case of any input validations or any specific exceptions.
	 */
	private boolean checkIfInfected(String initiator) throws ApplicationValidationException {
		
		int affectedRows=0;
		long noOfDaysBetween = Utility.getCurrentDate()-14; //date value to be passed (date value > last 14 days from current date)
		String sql1="INSERT INTO fp_covid_interaction_dtls(log_id, registered_date) "
				+ "SELECT logDtls.log_id ,CURDATE() AS identified_date FROM  fp_device_log logDtls "
				+ "INNER JOIN fp_mobile_dtls mobileDtls ON mobileDtls.mobile_name=logDtls.neighbour_hash "
				+ "INNER JOIN fp_test_dtls testDtls ON testDtls.test_hash=mobileDtls.test_hash "
				+ "LEFT JOIN fp_covid_interaction_dtls interActionDtls  ON logDtls.log_id=interActionDtls.log_id  "
				+ "WHERE  interActionDtls.log_id IS NULL AND "
				+ "logDtls.mobile_hash='"+initiator+"' AND  "
				+ "logDtls.date >="+noOfDaysBetween+" AND  "
				+ "testDtls.test_date >="+noOfDaysBetween+" AND "
				+ "testDtls.test_result='positive';";
		Connection sqlConnection=null;
		Statement statement=null;
		
		try {
			sqlConnection=createSQLConnection(configurationFile); //creates SQL connection
			sqlConnection.setAutoCommit(false);  //auto commit is set to false such that in case of any SQL errors, rollback operation is performed
			statement=sqlConnection.createStatement();
			affectedRows=statement.executeUpdate(sql1);
			sqlConnection.commit();//queries are committed if no errors 
			
		} catch (ApplicationValidationException e) {
			throw e;
		} catch (SQLException e) {
			if(sqlConnection!=null) {
				try {
					sqlConnection.rollback(); //rollback in case of any SQL error
				} catch (SQLException e1) {
					LOGGER.warning("Exception during rollback");
				}
			}
			//throw new ApplicationValidationException("Exception occured while creating SQL connection");
		}finally {
				try {
					if(statement!=null) {
						statement.close();
					}
					if(sqlConnection!=null) {
						sqlConnection.close();
					}
				} catch (SQLException e) {
					//throw new ApplicationValidationException("Exception occured while closing the connection");
				}
			}
		if(affectedRows > 0) return true;
		
		return false;
	}

	
	/**
	 * This is a private method to check if the mobile hash has any recent covid interactions 
	 * 
	 * @param initiator - Hash value of the mobile device
	 * @return - Returns true if the mobile device passed has any interactions with covid-infected persons in last 14 days, false otherwise
	 * @exception - ApplicationValidationException - In case of any input validations or any specific exceptions.
	 */
	private void saveContactAndTestHash(String initiator,String contactInfo) throws ApplicationValidationException {
		
		Connection sqlConnection=null;
		Connection sqlConnection2=null;
		PreparedStatement statement=null;
		PreparedStatement statement2=null;
		try {
			sqlConnection=createSQLConnection(configurationFile); //SQL connection is created
			sqlConnection.setAutoCommit(false); //auto commit is set to false such that in case of any SQL errors, rollback operation is performed
			statement=sqlConnection.prepareStatement("INSERT INTO fp_device_log(mobile_hash,neighbour_hash,date,duration,registered_date) values(?,?,?,?,CURDATE())");
			statement2=sqlConnection.prepareStatement("INSERT INTO fp_mobile_dtls(mobile_name,registered_date) values (?,CURDATE());"); //if neighbor device is not registered in the system(Not required in real-time scenario)
			File file = new File(contactInfo);  //XML file is opened
			DocumentBuilderFactory builderFac = DocumentBuilderFactory.newInstance(); //Javax packages are used to parse the file as XML file  
			DocumentBuilder documntBuilder = builderFac.newDocumentBuilder();  
			Document document = documntBuilder.parse(file);  
			document.getDocumentElement().normalize();  
			NodeList deviceList = document.getElementsByTagName("device"); //All devices recorded by the mobile is taken in the devicelist
			NodeList positiveTest = document.getElementsByTagName("positive-test"); //Test value passed by the mobile is also fetched
			
			
			for (int i = 0; i < deviceList.getLength(); i++) {  //device list is iterated
				Node node = deviceList.item(i);  
				if (node.getNodeType() == Node.ELEMENT_NODE){  
					Element elmt = (Element) node; 
					String neighbourHash=elmt.getElementsByTagName("hash").item(0).getTextContent();
					statement.setString(1,initiator);
					statement.setString(2, neighbourHash);
					statement.setString(3, elmt.getElementsByTagName("date").item(0).getTextContent());
					statement.setString(4, elmt.getElementsByTagName("time").item(0).getTextContent());
					statement.addBatch(); //each device information(hash, date, time) is added to the prepared statement batch and executed finally
					Statement statement1=sqlConnection.createStatement();
					ResultSet result=statement1.executeQuery("select * from fp_mobile_dtls where mobile_name="+neighbourHash); //Neighbor devices added to save in the DB if it doesn't exist 
					if(!(result.next()) ){
						statement2.setString(1, neighbourHash);
						statement2.addBatch();
					}
					result.close();
					statement1.close();
				}  
			}  
			
			statement2.executeBatch(); //neighbor devices are first registered
			statement.executeBatch(); //the device logs are registered
			sqlConnection.commit();//Queries committed if no error
			
			sqlConnection2=createSQLConnection(configurationFile); //Separate connection is created to update the test hash value
			sqlConnection2.setAutoCommit(false);//auto commit is set to false such that in case of any SQL errors, rollback operation is performed
			for (int i = 0; i < positiveTest.getLength(); i++){  
				Node node = positiveTest.item(i);  
				if (node.getNodeType() == Node.ELEMENT_NODE){  
					Element elmt = (Element) node;
					String positiveTestHash=elmt.getElementsByTagName("test-hash").item(0).getTextContent();
						if((positiveTestHash!=null) && !(positiveTestHash.trim().equals(""))){ //if test value exist
							PreparedStatement ps_statement=sqlConnection2.prepareStatement("UPDATE fp_mobile_dtls SET test_hash=? WHERE mobile_name=?");
							ps_statement.setString(1,positiveTestHash);	
							ps_statement.setString(2, initiator);
							try {
								ps_statement.execute();
								sqlConnection2.commit();//Queries committed if no error
							} catch (SQLException e) {
								LOGGER.warning("Invalid test hash");
							} //log.error
							ps_statement.close();
						}
				}
			}
			
			
		} catch (ApplicationValidationException e) {
			throw e;
		} catch (SQLException e) {
			try {
				sqlConnection.rollback();
			} catch (SQLException e1) {
				LOGGER.warning("Exception during rollback");
			}
			//throw new ApplicationValidationException("Exception occured while creating SQL connection");
		} catch (IOException |ParserConfigurationException |SAXException e) {
			//throw new ApplicationValidationException("Exception occured while accessing XML file");
		}  finally {
				try {
					if(statement2!=null) {
						statement2.close();
					}
					if(statement!=null) {
						statement.close();
					}
					if(sqlConnection!=null) {
						sqlConnection.close();
					}
				} catch (SQLException e) {
					//throw new ApplicationValidationException("Exception occured while closing the connection");
				}
			}
		
		
	}
	/**
	 * This is a public method which records the test hash value passed by the test center
	 * Test hash and date is validated before saving in the database
	 * Throws exception if the test hash already exist in the database
	 * 
	 * @param testHash - Test hash value passed by the test center
	 * @param date- Test date on which the user has taken the covid test
	 * @param result - Result of the test taken. True- covid positive, false- covid negative
	 * @exception - ApplicationValidationException - In case of any input validations or any specific exceptions.
	 */
	public void recordTestResult( String testHash, int date, boolean result ) throws ApplicationValidationException {
		
		if((testHash!=null)) { //validation for null test hash
			if(!(testHash.trim().equals(""))) { //validation for empty test hash
				if(Utility.checkDate(date)) {  //checks for valid date, non negative and also checked if the date passed is not future date
					
					Connection sqlConnection=null;
					PreparedStatement statement=null;
					
					try {
						sqlConnection=createSQLConnection(configurationFile); //SQL connection is created
						sqlConnection.setAutoCommit(false); //auto commit is set to false such that in case of any SQL errors, rollback operation can be performed
						statement=sqlConnection.prepareStatement("INSERT INTO fp_test_dtls(test_hash,test_date,test_result,registered_date) VALUES(?,?,?,CURDATE())");
						statement.setString(1,testHash);
						statement.setInt(2, date);
						if(result) {
						statement.setString(3, "positive"); //True- covid positive
						}else {
							statement.setString(3, "negative"); //False- covid negative
						}
						statement.execute();
						sqlConnection.commit(); //No SQL errors, queries are committed
					} catch (SQLException e) {
						try {
							sqlConnection.rollback(); //rollback during SQL exception
						} catch (SQLException e1) {
							LOGGER.warning("Exception during rollback");
						}
						if(e.getMessage().contains("Duplicate entry")) {
							throw new ApplicationValidationException("Test hash already exist");
						}
						//throw new ApplicationValidationException("Exception occured while creating SQL connection");
					}finally {
						try {
							if(statement!=null) {
								statement.close();
							}
							if(sqlConnection!=null) {
								sqlConnection.close();
							}
						} catch (SQLException e) {
							//throw new ApplicationValidationException("Exception occured while closing the connection");
						}
					}
				}else {
					throw new ApplicationValidationException("Invalid date");	}
			}else {
				throw new ApplicationValidationException("TestHash value cannot be empty");	}
		}else {
			throw new ApplicationValidationException("TestHash value cannot be null");
		}
		LOGGER.info("Test details recorded");
	}
	
	
	/**
	 * This is a public method which returns the number of gatherings on a specific date
	 * All the devices recorded on the particular date is fetched from the database
	 * Pairs are taken from the devices list and devices connected by both of them are taken as a set
	 * If the set exceeds the minSize passed, then density is calculated.
	 * If the density obtained is greater than the density passed, gatherings count is increased
	 * 
	 * @param date - Date for which the gatherings to be calculated
	 * @param minSize- Number of individuals in a set for which the gatherings should be considered
	 * @param minTime- The individuals in the gathering set should have contacted their peers for atleast minTime
	 * @param density - Density value against which the calculated density should be compared
	 * @exception - ApplicationValidationException - In case of any input validations or any specific exceptions.
	 */
	public int findGatherings( int date, int minSize, int minTime, float density ) throws ApplicationValidationException {
		int gatheringCount=0;
		Connection sqlConnection=null;
		Statement statement=null;
		ResultSet result=null;
		
		if(Utility.checkDate(date)) {  
			if(minSize>0) {
				if((minTime>0) && (minTime<=1440)){
					if(1/density > 0) {
			try {
				String sql="SELECT mobile_hash, GROUP_CONCAT( CONCAT(neighbour_hash,\"=\",duration ) )AS neighbour_hash"
						+ " FROM fp_device_log WHERE date="+date+" GROUP BY mobile_hash ;\r\n";
				sqlConnection=createSQLConnection(configurationFile);		
				statement=sqlConnection.createStatement();
				result=statement.executeQuery(sql);  //The SQL query returns the devices list from the DB along with their interactions and time. Ex: Single row, mobile_hash=A, neighbor_hash={B=10, C=12, D=13}
				Map<String,  Map<String,Integer> > devicesMap=new ConcurrentHashMap<>(); //The values from the database are stored in Map of Map in the similar format,  A={B=10, C=12, D=13}
				
				//the result from DB is iterated to store the information in Map<Map>
				while(result.next()) {
					String mobileHash=result.getString("mobile_hash"); //mobile hash from DB
					String contacts[]=result.getString("neighbour_hash").split(","); //Corresponding neighbor hash, splitted and iterated to store in map
					Map<String,Integer> neighbrMap=new HashMap<>();
					for(String tmp:contacts) {
						String temp[]=tmp.split("="); //neighbor hash and duration value is split and updated in map
						//check for contains
						if(neighbrMap.containsKey(temp[0])) { //if device already has recordings of the other device, time values are added
							int value=neighbrMap.get(temp[0])+Integer.parseInt(temp[1]);
							neighbrMap.put(temp[0], value);
						}else {
							neighbrMap.put(temp[0], Integer.parseInt(temp[1]));
						}
						
						
					}
					devicesMap.put(mobileHash, neighbrMap); //values are stored in the device map
					
				}
				
				Set<String> individuals=devicesMap.keySet(); //key values from map are stored in the set. Note: ConcuurentHashMap is used to avoid any concurrent modification exceptions 
				int individualsSize=individuals.size();
				String[] individualsArr= new String[individualsSize]; //set is converted into an array for itertaing
				individualsArr=individuals.toArray(individualsArr);
				
				//Array is iterated to consider pairs for gathering
				//x value starts from the first element; y value starts from the (x+1)th element to avoid considering same pair twice
				for(int x=0; x<individualsSize-1; x++) {
					String firstPairElemnt=individualsArr[x]; //element in x location is taken as first element
					
					if(individuals.contains(firstPairElemnt)) { //If the first element can be considered, then other operations are performed
						for(int  y=x+1; y<individualsSize;y++) {
							String nextPairElemnt=individualsArr[y]; //similarly element in y location is taken as next element
								if(individuals.contains(nextPairElemnt)) { //If the next element can be considered, then other operations are performed
									Set<String> gatherings=new HashSet<>(); //Set value to store the common nodes contacted by both first and next element
									gatherings.add(firstPairElemnt); //Adding first and next element in the gathering
									gatherings.add(nextPairElemnt);
										{
											Set<String> setOfFrstElemnt=devicesMap.get(firstPairElemnt).keySet(); //devices contacted by first element from map
											Set<String> setOfNxtElemnt=devicesMap.get(nextPairElemnt).keySet(); //devices contacted by next element from map
											
											for(String tempStr:setOfFrstElemnt) { //device list of first element is iterated to find the matching nodes
												if(individuals.contains(tempStr)) { //if device list of next element contains tempStr, the device is added to gatherings set
													if(setOfNxtElemnt.contains(tempStr)) {
														gatherings.add(tempStr);
													}
												}
											}
											
										}
									int n=gatherings.size();
									if(n>=minSize) { //If the gathering size is greater than the given minimum size
										float c=0;
										String[] arr= new String[n]; //New array is created with size of the gatherings set
										arr=gatherings.toArray(arr); //set is stored as array and iterated
										
										//i value starts from the first element; j value starts from the (i+1)th element to avoid considering same pair twice
										for(int i=0;i<n-1;i++) {
											String firstElement=arr[i]; 
											for(int  j=i+1; j<n;j++) {
												String nextElement=arr[j];		
												if(devicesMap.containsKey(firstElement)) { //If the map contains first element
													Map<String,Integer> valueMap=devicesMap.get(firstElement); //value map of that first element is fetched
													if(valueMap.containsKey(nextElement)) { //if first element has recordings of next element
														if( valueMap.get(nextElement) > minTime) { //if the total time exceeds minTime, c value is increased
															c++;
														}//if total value is less than minTime, cross-checking whether the total time recorded by next element exceeds minTime
														else if(devicesMap.containsKey(nextElement)) { //If the map contains next element
															Map<String,Integer> valueNextMap=devicesMap.get(nextElement);//value map of that next element is fetched
																if(valueNextMap.containsKey(firstElement)) {//if next element has recordings of first element
																	if( valueNextMap.get(firstElement) > minTime) {//if the total time exceeds minTime, c value is increased
																		c++;
																	}
																}
															}
													}
												}else if(devicesMap.containsKey(nextElement)) { //If the map contains next element
													Map<String,Integer> valueMap=devicesMap.get(nextElement);//value map of that next element is fetched
													if(valueMap.containsKey(firstElement)) {//if next element has recordings of first element
														if( valueMap.get(firstElement) > minTime) {//if the total time exceeds minTime, c value is increased
															c++;
														}//if total value is less than minTime, cross-checking whether the total time recorded by first element exceeds minTime
														else if(devicesMap.containsKey(firstElement)) {//value map of that first element is fetched
															Map<String,Integer> valueNextMap=devicesMap.get(firstElement);//if first element has recordings of next element
															if(valueNextMap.containsKey(nextElement)) {
																if( valueNextMap.get(nextElement) > minTime) {//if the total time exceeds minTime, c value is increased
																	c++;
																}
															}
														}
													}
												}
											}
										}
										float x1= ((n * (n-1))/2); //Maximum connection value is calculated
										float d= (c/x1);  //density value is calculated
										if(d>density) { //if density greater than the passed density, gatherings count is increased
											gatheringCount++;
											individuals.removeAll(gatherings); //All the devices from the gatherings is removed from the set
											break; //Loop breaks to take the next pair
										}
									}	
								}	
							}
						}
					}
			} catch (ApplicationValidationException e) {
				throw e;
			} catch (NumberFormatException e) {
				
			} catch (SQLException e) {
				throw new ApplicationValidationException("Exception occured while creating SQL connection");
			}
					}else {
						throw new ApplicationValidationException("Invalid density");
					}}else {
						throw new ApplicationValidationException("Invalid minimum time");
					}}else {
						throw new ApplicationValidationException("Invalid minimum size");
					}
					
				
			
			}else {
				throw new ApplicationValidationException("Invalid date");
			}
		return gatheringCount;
	}
	
	/**
	 * This is a private method to create SQL connections
	 * 
	 * @param databaseProperties- File path of the database property file 
	 * @return - Returns SQL connection object
	 * @exception - ApplicationValidationException - In case of any input validations or any specific exceptions.
	 */
	private Connection createSQLConnection(String databaseProperties) throws ApplicationValidationException {
		
		Connection connection=null;
		Statement statement1=null;
		
		try {
			FileInputStream fileStream=new FileInputStream(databaseProperties);  // Database information are stored in a separate property file.
			Properties property=new Properties();
			property.load(fileStream);		
			
			if(checkDBProperties(property)) {
					Class.forName("com.mysql.cj.jdbc.Driver"); //Loading the MySql driver
					
					String additionalHeaders="";
					if(property.getProperty("additionalHeaders")!=null) {
						additionalHeaders=property.getProperty("additionalHeaders");
					}
					String url="jdbc:mysql://"+property.getProperty("hostURL")+":"+property.getProperty("port")+additionalHeaders;	
					connection=DriverManager.getConnection(url, property.getProperty("username"), property.getProperty("password")); //Creating SQL connection
					statement1=connection.createStatement();
					statement1.execute("use "+property.getProperty("schema"));
			}else {
				
				throw new ApplicationValidationException("Exception : Missing parameters in the database property file");
			}
		}catch  (FileNotFoundException e) {
			throw new ApplicationValidationException("Exception : Cannot find database.properties file");
			
		} catch (IOException e) {
			throw new ApplicationValidationException("Exception occured while accessing database.properties");
			
		} catch  (ClassNotFoundException e) {
			throw new ApplicationValidationException("Exception : MySql connector jar missing");
			
		} catch  (SQLException e) {
			throw new ApplicationValidationException("Exception occured while creating SQL connection");
			
		}  finally { //closing the connection and statement objects
			try {
				if(statement1 !=null) {
					statement1.close();
				}
			} catch (SQLException e) {
				//throw new ApplicationValidationException("Exception occured while closing the connection");
			}
		}
		
		return connection;
	}
	
	/**
	 * This is a private method to check the database property file
	 * 
	 * @param property- Database property file 
	 * @return - Returns true if the property file is valid, false otherwise
	 */
	private boolean checkDBProperties(Properties property) {
		
		String hostURL=property.getProperty("hostURL");
		String port=property.getProperty("port");
		String username=property.getProperty("username");
		String password=property.getProperty("password");
		String schema=property.getProperty("schema");
		if((hostURL!=null)  &&  ! (hostURL.trim().equals("")) &&  
				(port!=null)  &&  ! (port.trim().equals("")) &&  
					(username!=null)  &&  ! (username.trim().equals("")) &&  
						(password!=null)  &&  ! (password.trim().equals("")) &&  
							(schema!=null)  &&  ! (schema.trim().equals(""))) {
				
			return true;
		}
		
		return false;
	}

	/**
	 * This is a public method which returns the database property file path passed in the constructor
	 * This is created for testing purposes
	 * 
	 * @return - Returns the database property file path passed in the constructor
	 */
	public String getConfigurationFile() {
		return configurationFile;
	}
	
}
