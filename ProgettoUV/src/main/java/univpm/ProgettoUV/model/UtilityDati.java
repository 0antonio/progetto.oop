package univpm.ProgettoUV.model;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class UtilityDati {
	public JSONArray leggi(String fileName) {
		JSONArray obj = null;
		JSONParser jsonParser = new JSONParser();
		try {
		FileReader reader = new FileReader(fileName);
		//Read JSON file
		
		
			obj = (JSONArray) jsonParser.parse(reader);
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	
	}
	
	public void scrivi(String nameFile, JSONArray jarr) {
		try {
			FileWriter file = new FileWriter(nameFile);
			file.write(jarr.toJSONString());
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
