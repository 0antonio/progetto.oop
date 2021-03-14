package univpm.ProgettoUV.controller;

import java.io.FileNotFoundException;
import org.json.simple.JSONArray;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import univpm.ProgettoUV.exception.WrongCoordinatesException;
import univpm.ProgettoUV.model.*;


/**
 *<p>
 *La classe <b>MainController</b> permette all'utente di visualizzare la lista di città prese in considerazione 
 *per le statistiche e di visualizzare i dati  in corrispondenza delle coordinate scelte.
 *</p>
 *
 * @author Giangrossi Antonio
 * @author Di lorenzo Emanuele
 * 
 */


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

	/**
	 * Questo metodo permette di aggiornare la lista di dati delle citta prese in considerazione
	 * 
	 * 
	 * @return <code>String</code>
	 */
	
	@GetMapping(value = "/aggiorna", produces = "application/json")
	public String aggiorna() {
		StampaLista aggiorna = new StampaLista();
		aggiorna.stampa();
		
		return "aggiornamento completato";
	}
	
	/**
	 * Questo metodo permette di visualizzare i dati delle città inserite dall'utente
	 * 
	 * @param lat indica la latitudine della città da inserire
	 * @param lon indica la longitudine della città da inserire
	 * 
	 * @throws WrongCoordinatesException coordinate inserite in maniera sbagliata 
	 * 
	 * @return <code>JSONArray</code>
	 */
	
	@GetMapping(value="/coorCittà",produces = "application/json")
	public JSONArray restituisciDatiNoStats(
			@RequestParam("lat") String lat,
			@RequestParam("lon") String lon) throws WrongCoordinatesException  {
		GestoreRotte gs = new GestoreRotte();
				   return gs.datiNoStats(lat, lon);
		}
	/**
	 * Questo metodo permette di visualizzare la lista di città prese in considerazione per 
	 * il calcolo delle statistiche
	 * 
	 * 
	 * @return <code>JSONArray</code>
	 */

    @GetMapping(value = "/listaCittà", produces = "application/json")
    public JSONArray listaCit() throws FileNotFoundException {
    	GestoreRotte gs = new GestoreRotte();
    	return gs.listaCompleta();
    }
   
   
}


