package univpm.ProgettoUV.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import univpm.ProgettoUV.model.*;

@RestController
public class MainController {
	@GetMapping(value="/città",produces = "application/json")
	public Object exampleMethod(
			@RequestParam("lon") float lon,
			@RequestParam("lat") float lat) {
		return APICoordinates.getCoordinates(lon, lat);
	}
}
