package univpm.ProgettoUV.controller;      

import org.json.simple.JSONArray;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import univpm.ProgettoUV.exception.WrongCoordinatesException;
import univpm.ProgettoUV.model.*;

@RestController
public class MainController {
	@GetMapping(value="/città",produces = "application/json")
	public String exampleMethod(
			@RequestParam("name") String name,
			@RequestParam("country") String country) throws WrongCoordinatesException {
		String message = "";
		String[] list = name.split(",");
		String[] countrylist = country.split(",");
		
		int i=0;
		for(String element:list) {
			String countryelement = countrylist[i];
				i=i++;
		double lat = APICoordinates.getCitylat(APICoordinates.caricaArray(),element,countryelement);
		double lon = APICoordinates.getCitylat(APICoordinates.caricaArray(),element,countryelement);
		
		message+="\n"+element+"  "+countryelement+"\n";
		message+=APICoordinates.getCoordinates(lat,lon);
		
		}
		//return  APICoordinates.getCoordinates(lat,lon).size();
		return message;
	}
}
