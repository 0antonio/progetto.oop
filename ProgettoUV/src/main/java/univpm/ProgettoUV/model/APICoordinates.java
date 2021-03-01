package univpm.ProgettoUV.model;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import univpm.ProgettoUV.exception.WrongCoordinatesException;

import org.json.simple.parser.JSONParser;

public class APICoordinates {
	private static String filename = "city.list.json";	
	
	public static JSONArray getCoordinates(double lat,double lon) {
		String url = "http://api.openweathermap.org/data/2.5/uvi/forecast?lat="+lat+"&lon="+lon+"&appid=67d40513b0e3e715b6cec6f7e02d354d"; 
		 //url = "http://api.openweathermap.org/data/2.5/uvi/history?lat="+lat+"&lon="+lon+"&appid=67d40513b0e3e715b6cec6f7e02d354d"; 
		//api.openweathermap.org/geo/1.0/direct?q="+nome+"&limit=50&appid=67d40513b0e3e715b6cec6f7e02d354d
		 String data = "";
		 String line = "";
		 JSONObject obj = null;
		 JSONArray arr = null;
		try {
			
			URLConnection openConnection = new URL(url).openConnection();
			InputStream in = openConnection.getInputStream();
			
			
			 try {
			   InputStreamReader inR = new InputStreamReader( in );
			   BufferedReader buf = new BufferedReader( inR );
			  
			   while ( ( line = buf.readLine() ) != null ) {
				   data+= line;
			   }
			 } finally {
			   in.close();
			 }
			obj = (JSONObject) JSONValue.parseWithException(data);	//parse JSON Array
			arr =(JSONArray) obj.get("list");
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	 return arr;

	}
	
	public static JSONArray caricaArray() {
		JSONParser jsonParser = new JSONParser();
		JSONArray cityList = null;
		
		try (FileReader reader = new FileReader(filename)){
			//Read JSON file
			Object obj = jsonParser.parse(reader);
			cityList = new JSONArray();
			return cityList = (JSONArray) obj;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return cityList;
	}
	
	
	public static double getCitylat(JSONArray ja,String nameCity,String country) throws WrongCoordinatesException {
		//Get city object within list
		double lat=0;
		for(int i =0; i<ja.size(); i++) {
			JSONObject cityObject = (JSONObject) ja.get(i);
			if(cityObject.get("name").equals(nameCity)) {
				if(cityObject.get("country").equals(country)) {
					//Get coord object within list
					JSONObject coordObject = (JSONObject) cityObject.get("coord");
					//Get lat
					lat = (Double) coordObject.get("lat");  
				}
			}
		} 
		if (lat == 0 ) throw new WrongCoordinatesException();
		return lat;
	}
	
	public static double getCitylon(JSONArray ja,String nameCity,String country) throws WrongCoordinatesException {
		//Get city object within list
		double lon=0;
		for(int i =0; i<ja.size(); i++) {
			JSONObject cityObject = (JSONObject) ja.get(i);
			if(cityObject.get("name").equals(nameCity)) {
				if(cityObject.get("country").equals(country)) {
					//Get coord object within list
					JSONObject coordObject = (JSONObject) cityObject.get("coord");
					//Get lon
					lon = (Double) coordObject.get("lon");    
					 
				}
			}
		} 
		if (lon == 0) throw new WrongCoordinatesException();
		return lon;
	}
	public static JSONArray getValues(String name, String country) throws WrongCoordinatesException{
		double lat = APICoordinates.getCitylat(APICoordinates.caricaArray(),name,country);
		double lon = APICoordinates.getCitylat(APICoordinates.caricaArray(),name,country);
		
		return APICoordinates.getCoordinates(lat,lon);
	}
	
}
 


