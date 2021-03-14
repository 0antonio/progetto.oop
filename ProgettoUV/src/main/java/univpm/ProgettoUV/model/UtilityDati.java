package univpm.ProgettoUV.model;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * <p>Classe per leggere o scrivere file</p>
 * @author Giangrossi Antonio
 * @author Di Lorenzo Emanuele
 *
 */
public class UtilityDati {
	
	/**
	 * <p>metodo per leggere un file </p>
	 * @param fileName nome del file da leggere
	 * @return <code>JSONArray</code> contenente i dati del file letto
	 */
	public JSONArray leggi(String fileName) {
		JSONArray obj = null;
		JSONParser jsonParser = new JSONParser();
		try {
		FileReader reader = new FileReader(fileName);		
			obj = (JSONArray) jsonParser.parse(reader);
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return obj;
	
	}
	
	/**
	 * <p>metodo per salvare dati in un file</p>
	 * @param nameFile nome del file su cui salvare i dati
	 * @param jarr <code>JSONArray</code> con i dati da salvare
	 */
	
	public void scrivi(String nameFile, JSONArray jarr) {
		try {
			FileWriter file = new FileWriter(nameFile);
			file.write(jarr.toJSONString());
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	

}
