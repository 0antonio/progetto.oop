package univpm.ProgettoUV.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import univpm.ProgettoUV.exception.WrongCoordinatesException;
import univpm.ProgettoUV.model.*;

@RestController
public class MainController {
	@GetMapping(value="/città",produces = "application/json")
	public Object exampleMethod(
			@RequestParam("name") String name,
			@RequestParam("country") String country) throws WrongCoordinatesException {
		
		double lat = APICoordinates.getCitylat(APICoordinates.caricaArray(),name,country);
		double lon = APICoordinates.getCitylat(APICoordinates.caricaArray(),name,country);
		
		//return  APICoordinates.getCoordinates(lat,lon).size();
		return APICoordinates.getCoordinates(lat,lon);
		
	}
}
