package univpm.ProgettoUV.controller;

import org.json.simple.JSONArray;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



import univpm.ProgettoUV.exception.WrongCoordinatesException;
import univpm.ProgettoUV.exception.WrongFilterException;
import univpm.ProgettoUV.exception.WrongRangeException;
import univpm.ProgettoUV.model.GestoreRotte;

@RestController
public class StatsController {

	@GetMapping(value = "/stats", produces = "application/json")
	public JSONArray restituisciElenco2(@RequestParam("lat") String lati, @RequestParam("lon") String longi,
			@RequestParam(value = "range", defaultValue = "1") int range,
			@RequestParam(value = "filter", defaultValue = "no") String filter)
			throws WrongCoordinatesException, WrongRangeException, WrongFilterException {
		GestoreRotte gs = new GestoreRotte();
		return gs.stats(lati, longi, range, filter);
	}

}
