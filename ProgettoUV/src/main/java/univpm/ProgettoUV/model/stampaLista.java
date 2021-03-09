package univpm.ProgettoUV.model;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class stampaLista {
	
	
	public static JSONArray stampa() {
		JSONArray obj= null;
		JSONObject cityObject=null;
		JSONObject coordinate=null;
		JSONArray jarr = new JSONArray();
		double latitudine = 0.0;
		double longitudine = 0.0;
		Object ID = null;
		JSONObject[] tmp = new JSONObject[100];
		String paese = new String();
		String name = new String();
		int contaIT = 0;
		System.out.println("parte 1");
		try {
		JSONParser jsonParser = new JSONParser();
		JSONArray cityList = null;
         FileReader reader = new FileReader("city.list.json");
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
		
		
		// copia il file vecchio in un array
		JSONArray objOld = new JSONArray();
		try {
			JSONParser jsonParserOld = new JSONParser();
			
	       FileReader readerOld = new FileReader("listaValori.json");
				//Read JSON file	
			objOld = (JSONArray) jsonParserOld.parse(readerOld);
					readerOld.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		// fine copiatura vecchio file in objOld
		
		System.out.println("parte 2");
		Set<String> cittàPassate = new HashSet<String>();
		JSONArray timeAndVal = new JSONArray();
		int j = 0;
		String italia = "IT";
		JSONObject firstTimeAndVal;
		JSONObject objectOld;
		JSONArray valuesOld ;
		JSONObject lastValuesOld;
		long firstTime;
		long lastTime ;
		long diff;
		long numDiff;
		JSONArray timeAndValUpdated;
		//bj.size()
		for(int i =0; i<obj.size(); i++) {
			//System.out.println(i);
			cityObject = (JSONObject) obj.get(i);
			
			coordinate = (JSONObject) cityObject.get("coord");
			latitudine = (Double) coordinate.get("lat");
			longitudine = (Double) coordinate.get("lon");
			ID = cityObject.get("id");
			paese = (String) cityObject.get("country");
			name = (String) cityObject.get("name");
			//System.out.println(paese);
			
	
			if (paese.equals(italia) && contaIT<100 && (!cittàPassate.contains(name))) {
				//System.out.println(paese);
				cittàPassate.add(name);
				tmp[j] = new JSONObject();
				tmp[j].put("id", ID);
				
				timeAndVal = APICoordinatesNuovo.getCoordinates(latitudine,longitudine);
	
				firstTimeAndVal = (JSONObject) timeAndVal.get(0);
				firstTime = (long) firstTimeAndVal.get("dt");
				
				
				objectOld = (JSONObject) objOld.get(contaIT);
			
				valuesOld = (JSONArray) objectOld.get("values");
				lastValuesOld = (JSONObject) valuesOld.get(valuesOld.size()-1);
				lastTime = (long) lastValuesOld.get("dt");
				
				diff = lastTime - firstTime;
				numDiff = diff/3600;
		
				for(int k = 0; k<=numDiff; k++) {
					valuesOld.remove(valuesOld.size()-1);
				}
				timeAndValUpdated = new JSONArray();
				for(int cont=0; cont<valuesOld.size();cont++) {
				timeAndValUpdated.add(valuesOld.get(cont));
				}
				for(int cont=0; cont<timeAndVal.size();cont++) {
				timeAndValUpdated.add(timeAndVal.get(cont));
				}
				System.out.println(timeAndVal.size());
				System.out.println(timeAndValUpdated.size());
			
				JSONArray timeAndValUp = (JSONArray) timeAndValUpdated.clone();

				tmp[j].put("values", timeAndValUp);
				tmp[j].put("name",  name);
				jarr.add(tmp[j]);
				j++;
				contaIT++;
				//System.out.println(i);
			}
			
		} 
		try {
			FileWriter file = new FileWriter("listaValori.json");
			file.write(jarr.toJSONString());
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("fatto");
		
		return obj;
	}

}
