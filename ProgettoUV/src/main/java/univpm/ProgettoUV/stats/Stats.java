package univpm.ProgettoUV.stats;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Stats {
	
	private static String filename = "listaValori.json";
	
	public static JSONArray caricaStats() {
		JSONParser jsonParser = new JSONParser();
		JSONArray statsList = null;
		
		try (FileReader reader = new FileReader(filename)){
			//Read JSON file
			Object obj = jsonParser.parse(reader);
			statsList = new JSONArray();
			return statsList = (JSONArray) obj;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return statsList;
	}
	
	// funzione che mi restituisce gli uv
public static Vector <Double> getUv(JSONArray ja,long id) {
		
	  Vector <Double> ArrayUV= new Vector<>();
	  
		
		for(int i =0; i<ja.size(); i++) {
			
			JSONObject cityObject = (JSONObject) ja.get(i);
			if((Long) cityObject.get("id")==id) {
				JSONArray valueArray = (JSONArray) cityObject.get("values");
				for(int j=0;j<valueArray.size();j++) {
				JSONObject valueObject = (JSONObject) valueArray.get(j);
					String uv=""+(valueObject.get("uvi"));
					ArrayUV.add(Double.valueOf(uv));
					}
			      }
			    }
			return ArrayUV;
		} 

	

// funzione che mi restituisce i dt
public static Vector <Long> getDt(JSONArray ja,long id) {
	
	 
	  Vector <Long> Arraydt= new Vector<>();
		
		for(int i =0; i<ja.size(); i++) {
			
			JSONObject cityObject = (JSONObject) ja.get(i);
			if((Long) cityObject.get("id")==id) {
				JSONArray valueArray = (JSONArray) cityObject.get("values");
				for(int j=0;j<valueArray.size();j++) {
				JSONObject valueObject = (JSONObject) valueArray.get(j);
					Long dt=(Long) (valueObject.get("dt"));
					Arraydt.add(dt);
					}
			      }
			    }
			return Arraydt;
	}

public static double media(Vector<Double> uvi,Vector<Long> dt,long dtmax) {
	// TODO Auto-generated method stub
	int i=0,k=0;
	double media=0;
	for(Double uvielement:uvi) {
		if(dt.get(i)<dtmax) {
			k++;
		media+=uvielement;
		}
     i++;
	}
	return media/k;
}


public static double getMax(Vector<Double> uvi,Vector<Long> dt,long dtmax) {
	// TODO Auto-generated method stub
	int i=0;
	double max=0;
	for(Double uvielement:uvi) {
		if(dt.get(i)<dtmax) {
			if(max<uvielement) {
				max=uvielement;
			}
		}
     i++;
	}
	return max;
}


public static double getMin(Vector<Double> uvi,Vector<Long> dt,long dtmax) {
	// TODO Auto-generated method stub
	int i=0;
	double min=0;
	for(Double uvielement:uvi) {
		if(i==0) {
			min=uvielement;
		      }
		if(dt.get(i)<dtmax) {
			if(min>uvielement) {
				min=uvielement;
			}
		}
     i++;
	}
	return min;
}


public static double varianza(Vector<Double> uvi,Vector<Long> dt,long dtmax,double media) {
	// TODO Auto-generated method stub
	int i=0,k=0;
	double var=0;
	for(Double uvielement:uvi) {
		if(dt.get(i)<dtmax) {
			k++;
		var+=(uvielement-media)*(uvielement-media);
		}
     i++;
	}
	return var/k;
	
}
	
}
