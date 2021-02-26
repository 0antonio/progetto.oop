package univpm.ProgettoUV.model;

import java.io.*;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

public class APICoordinates {
	
	public static String getCoordinates(float lat,float lon) {
		String url = "http://api.openweathermap.org/data/2.5/uvi/forecast?lat="+lat+"&lon="+lon+"&appid=67d40513b0e3e715b6cec6f7e02d354d"; 
		//api.openweathermap.org/geo/1.0/direct?q="+nome+"&limit=50&appid=67d40513b0e3e715b6cec6f7e02d354d
		 String data = "";
		 String line = "";
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
			JSONArray obj = (JSONArray) JSONValue.parseWithException(data);	//parse JSON Array
			
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//conversione
		
	 return data;
	}
	}
 


