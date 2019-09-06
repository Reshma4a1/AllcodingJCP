package com.util;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import com.codoid.products.utils.FilenameUtils;
import com.logconfig.LogConfig;


 
public class PropertyReader{
	private static Fillo fillo;
    private static Connection connection;
	static Logger consoleLog = LogConfig.getLogger(PropertyReader.class);
	
    /**
     * Description : method is implemented to read any property file and store in map
     * 
     * @param filePath
     * @return
     */
	public static Map<String, String> getData(String propertyFilePath) {
		Map<String, String> uiProperty = new HashMap<String, String>();
		Properties properties = new Properties();
		InputStream input = null;
		String fileExtension = FilenameUtils.getExtension(propertyFilePath);
		if(fileExtension.equals("properties")){
			try {
				input = new FileInputStream(propertyFilePath);
				properties.load(input);
			} catch (Exception e) {
				e.printStackTrace();
			}

			for (String key : properties.stringPropertyNames()) {
				String value = properties.getProperty(key);
				uiProperty.put(key, value);
			}
		}else{
			consoleLog.info("File Extension must be of properties type");
		}
		return uiProperty;
	}
	
	/**
	 * Description : method implemented to get data from excel data base
	 * @param inputExcelFilePath
	 * @return
	 */
	public static List<Map<String, String>> getDataFromExcelDB(String inputExcelFilePath,String query) {
		List<Map<String, String>> uiConfig = new ArrayList<Map<String, String>>();
			 try {
				 	fillo = new Fillo();
		            connection = fillo.getConnection(inputExcelFilePath);
		            Recordset recordset = connection.executeQuery(query);
		            int rows = recordset.getFieldNames().size();
		            while (recordset.next()){
		            	Map<String, String> mapObj = new HashMap<String, String>();
		            	for(int i=0;i<rows;i++){
		            		mapObj.put(recordset.getFieldNames().get(i),recordset.getField(recordset.getFieldNames().get(i)));
		            	}
		            	uiConfig.add(mapObj);
		            }
			 }catch(Exception e){
				 consoleLog.info("Error while reading data from Excel DB");
				 e.printStackTrace();
			 }
			 return uiConfig;
	 }
	
	/**
	 * Description : method implemented to read json file and return JSONObject object.
	 * @param jsonFilePath
	 * @return
	 */
	public static JSONObject getJSONObject(String jsonFilePath){
		JSONObject jSONObject = null;
		JSONParser jsonParser = null;
		Object obj = null;
		String fileExtension = FilenameUtils.getExtension(jsonFilePath);
		if(fileExtension.equals("json")){
			jSONObject = new JSONObject();
			jsonParser = new JSONParser();
			try(FileReader reader = new FileReader(jsonFilePath)){
				obj = jsonParser.parse(reader);
				jSONObject = (JSONObject)obj;
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			consoleLog.info("File Extension must be of JSON type");
		}
		return jSONObject;
	}
}