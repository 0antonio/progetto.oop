package univpm.ProgettoUV.controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import univpm.ProgettoUV.exception.WrongCoordinatesException;
import univpm.ProgettoUV.model.*;

@RestController
public class MainController {
	@GetMapping(value = "/nomeCittà", produces = "application/json")
	public String exampleMethod(@RequestParam("name") String name, @RequestParam("country") String country)
			throws WrongCoordinatesException {
		String message = "";
		String[] list = name.split(",");
		String[] countrylist = country.split(",");

		int i = 0;
		for (String element : list) {
			String countryelement = countrylist[i];
			i = i++;
			double lat = APICoordinates.getCitylat(APICoordinates.caricaArray(), element, countryelement);
			double lon = APICoordinates.getCitylat(APICoordinates.caricaArray(), element, countryelement);

			message += "\n" + element + "  " + countryelement + "\n";
			message += APICoordinates.getCoordinates(lat, lon);

		}
		// return APICoordinates.getCoordinates(lat,lon).size();
		return message;
	}

	@GetMapping(value = "/coorCittà", produces = "application/json")
	public JSONArray restituisciElenco(@RequestParam("lat") String lat, @RequestParam("lon") String lon)
			throws WrongCoordinatesException {
		String message = "";
		String[] listLat = lat.split(",");
		String[] listLon = lon.split(",");
		JSONArray out = new JSONArray(), value = new JSONArray();
		JSONObject tmp = new JSONObject();

		int i = 0;
		for (String latElement : listLat) {
			String lonElement = listLon[i];
			i = i++;

			// conversione a double
			double latitudine = Double.parseDouble(latElement);
			double longitudine = Double.parseDouble(lonElement);

			message = latitudine + " , " + longitudine ; // ci devo mettere nome città e country
	
			value = APICoordinates.getCoordinates(latitudine, longitudine);
			tmp.put(message, value);
			out.add(tmp);
		}
		// return APICoordinates.getCoordinates(lat,lon).size();
		return out;

	}

	@GetMapping(value = "/listaCittà", produces = "application/json")
	public JSONArray listaCit() throws FileNotFoundException {
		JSONArray obj = null, objOut = new JSONArray();
		JSONObject cityObject = new JSONObject(), coordinate = new JSONObject(), tmp = new JSONObject();
		String paese = new String(), nome = new String();
		int contaIT = 0;

		try {
			JSONParser jsonParser = new JSONParser();
			JSONArray cityList = null;
			FileReader reader = new FileReader("city.list.json");
			// Read JSON file
			obj = (JSONArray) jsonParser.parse(reader);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < obj.size(); i++) {
			// System.out.println(i);
			cityObject = (JSONObject) obj.get(i);

			coordinate = (JSONObject) cityObject.get("coord");
			paese = (String) cityObject.get("country");
			nome = (String) cityObject.get("name");

			if (paese.equals("IT") && contaIT < 100) {
				tmp.put(nome, coordinate);
				objOut.add(tmp);
				contaIT++;
			}

		}
		return objOut;

	}
}
