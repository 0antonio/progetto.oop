package univpm.ProgettoUV.controller;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import univpm.ProgettoUV.exception.WrongCoordinatesException;
import univpm.ProgettoUV.model.*;
import univpm.ProgettoUV.service.*;

@RestController
public class MainController {
	@Autowired
	CittàServiceImpl città;
	
	@GetMapping(value="/risultato",produces = "application/json")
	public Object restituisciValori() throws WrongCoordinatesException {
		JSONObject out = new JSONObject();
		String identifier = "";
		ArrayList<Città> cit = città.getCittà();
		for (int i = 0; i<cit.size();i++) {
			Città tmp = cit.get(i);
			identifier = tmp.getName() +", " +tmp.getCountry();
		out.put(identifier, APICoordinates.getValues(tmp.getName(),tmp.getCountry()));
		}
		return out;
	}
	
	@PostMapping(value="/città",produces = "application/json")
	public ResponseEntity<Object> inserisciCittà(
			@RequestParam("name") String name,
			@RequestParam("country") String country)  throws WrongCoordinatesException{ //cambiare exception 
		
		città.inserisciCittà(name, country);
		
		//return  APICoordinates.getCoordinates(lat,lon).size();
		return new ResponseEntity<>("città inserita correttamente", HttpStatus.CREATED);
	}
	
}
