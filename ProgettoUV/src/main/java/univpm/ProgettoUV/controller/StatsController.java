package univpm.ProgettoUV.controller;

import org.json.simple.JSONArray;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import univpm.ProgettoUV.exception.WrongCoordinatesException;
import univpm.ProgettoUV.exception.WrongFilterException;
import univpm.ProgettoUV.exception.WrongRangeException;
import univpm.ProgettoUV.model.GestoreRotte;

/**
 *<p>
 *La classe <b>StatsController</b> permette all'utente di visualizzare le statistiche di un periodo inserito dall'utente
 *e di visualizzare la città con media piu alta o bassa 
 *</p>
 *
 * @author Giangrossi Antonio
 * @author Di lorenzo Emanuele
 * 
 */

@RestController
public class StatsController {

	

	/**
	 * Questo metodo permette di visualizzare le statistiche delle citta inserite dall'utente relativamente 
	 * al range inserito e al filtraggio della città con media piu alta/bassa.
	 * 
	 *L'utente può scegliere di mantenere il range di default (giornaliero), puo anche scegliere di 
	 *non utilizzare il filtraggio per la media piu alta/bassa:
	 * 
	 * @param lat indica la latitudine della città da inserire
	 * @param lon indica la longitudine della città da inserire
	 * @param range indica il periodo di giorni per il quale effetuare le statistiche
	 * @param filter indica il filtraggio per la media piu alta/bassa
	 * 
	 * @return <code>JSONArray</code>
	 * 
	 * @throws WrongCoordinatesException coordinate inserite in maniera sbagliata 
	 * @throws WrongRangeException periodo inserito non valido
	 * @throws WrongFilterException filtro inserito sbagliato
	 */
	
	
	
	@GetMapping(value = "/stats", produces = "application/json")
	public JSONArray restituisciElenco2(@RequestParam("lat") String lati, @RequestParam("lon") String longi,
			@RequestParam(value = "range", defaultValue = "1") int range,
			@RequestParam(value = "filter", defaultValue = "no") String filter)
			throws WrongCoordinatesException, WrongRangeException, WrongFilterException {
		GestoreRotte gs = new GestoreRotte();
		return gs.stats(lati, longi, range, filter);
	}

}
