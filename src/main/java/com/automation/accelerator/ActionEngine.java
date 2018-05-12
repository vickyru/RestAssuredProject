package com.automation.accelerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.automation.report.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;

import freemarker.template.utility.NullArgumentException;
import io.restassured.http.Header;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class ActionEngine extends TestBase{
	
	private static final Logger LOG = Logger.getLogger(ActionEngine.class);
	
	/**
	 * @param response
	 * @return
	 */
	protected JsonPath getJsonPath(Response response) {
		JsonPath path = null;
		try {
			String jsonString = response.body().asString();
			path = new JsonPath(jsonString);
		} catch (Exception exception) {
			LOG.debug("Exception Occured | getJsonPath " , exception);
		}
		return path;
	}
	
	/**
	 * @param response
	 * @return
	 */
	protected XmlPath getXmlPath(Response response) {
		XmlPath path = null;
		try {
			String xmlString = response.body().asString();
			path = new XmlPath(xmlString);
		} catch (Exception exception) {
			LOG.debug("Exception Occured | getXmlPath " , exception);
		}
		return path;
	}
	
	/**
	 * @param response
	 * @return
	 */
	protected List<Header> getHeaderAsList(Response response) {
		List<Header> headerList = null;
		try {
			headerList = response.getHeaders().asList();
		} catch (Exception exception) {
			LOG.debug("Exception Occured | getHeaderAsList " , exception);
		}
		return headerList;
	}
	
	/**
	 * @param response
	 * @return
	 */
	protected Map<String, String> getCoockiesAsMap(Response response) {
		Map<String, String> coockiesMap = null;
		try {
			coockiesMap = response.cookies();
		} catch (Exception exception) {
			LOG.debug("Exception Occured | getCoockiesAsMAp " , exception);
		}
		return coockiesMap;
	}
	
	/**
	 * @param response
	 * @param objectPath
	 * @return
	 */
	protected ArrayList<Map<String, ?>> getListOfItemOfObjectPath(Response response , String objectPath) {
		JsonPath path = null;
		ArrayList<Map<String, ?>> itemList = null;
		try {
			if (response != null && objectPath != null) {
				path = getJsonPath(response);
				if (path != null) {
					itemList = path.get(objectPath);
				} else {
					throw new IllegalArgumentException("Unable to locate any object for the path " + objectPath);
				}
				
			} else {
				throw new IllegalArgumentException("Response or Object Path argument is Null");
			}
		} catch (ClassCastException | IllegalArgumentException exception) {
			LOG.debug("Exception Occured | getListOfItemOfObjectPath " , exception);
		}
		return itemList;
	}
	
	/**
	 * @param response
	 * @param objectPath
	 * @return
	 */
	protected int getCountOfItemOfObjectPath(Response response , String objectPath) {
		JsonPath path = null;
		int itemCount = 0;
		try {
			if (response != null && objectPath != null) {
				path = getJsonPath(response);
				if (path != null) {
					itemCount = path.get(objectPath + ".size()");
				} else {
					throw new IllegalArgumentException("Unable to locate any object for the path " + objectPath);
				}
				
			} else {
				throw new IllegalArgumentException("Response or Object Path argument is Null");
			}
		} catch (ClassCastException | NullArgumentException exception) {
			LOG.debug("Exception Occured | getCountOfItemOfObjectPath " , exception);
		}
		return itemCount;
	}
	
	
	/**
	 * @param itemToBeConverted
	 * @return
	 */
	protected String getStringFrom(Object itemToBeConverted) {
		String value = null;
		try {
			if (itemToBeConverted != null) {
				value = String.valueOf(itemToBeConverted);
				if(value.contains(".")) {
					value = value.substring(0, value.length()-2);
				}
				System.out.println("Value : " + value);
			} else {
				throw new NullArgumentException("Item to be converted can't be null");
			}
			
		} catch (NullArgumentException exception) {
			LOG.debug("Exception Occured | getStringFrom " , exception);
		}
		return value;
	}
	
	protected boolean assertValues(String argumentIdentifier, Object expectedValue , Object actualvalue) {
		boolean flag = false;
		try {
			if (expectedValue != null && actualvalue != null) {
				if (expectedValue.equals(actualvalue)) {
					ExtentTestManager.getTest().log(LogStatus.PASS, "Expected " + argumentIdentifier  + " " + expectedValue , "Actual " + argumentIdentifier  + " " + actualvalue );
					flag = true;
				} else {
					ExtentTestManager.getTest().log(LogStatus.FAIL, "Expected " + argumentIdentifier  + " " + expectedValue , "Actual " + argumentIdentifier  + " " + actualvalue );
				}
			} else {
				throw new IllegalArgumentException("Expected or Actual value can't be null");
			}
		} catch (IllegalArgumentException exception) {
			LOG.debug("Exception Occured | assertValues " , exception);
		}
		return flag;
	}
	
	public static void main(String[] args) {
		System.out.println(new ActionEngine().assertValues("Status Code " ,Integer.valueOf(3), null));
	}

}
