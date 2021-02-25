package univpm.ProgettoUV.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import univpm.ProgettoUV.model.*;

@RestController
public class MainController {
	@GetMapping(value="/città/{namecity}",produces = "application/json")
	public Object exampleMethod(@PathVariable("namecity") String namecity) {
		return APIcoordinates.Getcoordinates(namecity);
	}
}
