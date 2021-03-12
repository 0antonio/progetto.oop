package univpm.ProgettoUV.controller;

import java.io.FileNotFoundException;
import org.json.simple.JSONArray;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import univpm.ProgettoUV.exception.WrongCoordinatesException;
import univpm.ProgettoUV.model.*;

 

@RestController
public class MainController {
	/*
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
    }*/

	
	
	@GetMapping(value = "/aggiorna", produces = "application/json")
	public String aggiorna() {
		StampaLista aggiorna = new StampaLista();
		aggiorna.stampa();
		
		return "aggiornamento completato";
	}
	
	
	
	@GetMapping(value="/coorCittà",produces = "application/json")
	public JSONArray restituisciDatiNoStats(
			@RequestParam("lat") String lat,
			@RequestParam("lon") String lon) throws WrongCoordinatesException  {
		GestoreRotte gs = new GestoreRotte();
				   return gs.datiNoStats(lat, lon);
		}
	

    @GetMapping(value = "/listaCittà", produces = "application/json")
    public JSONArray listaCit() throws FileNotFoundException {
    	GestoreRotte gs = new GestoreRotte();
    	return gs.listaCompleta();
    }
   
   
}


