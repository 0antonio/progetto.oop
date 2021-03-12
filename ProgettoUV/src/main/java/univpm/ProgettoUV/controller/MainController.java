package univpm.ProgettoUV.controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import univpm.ProgettoUV.exception.WrongCoordinatesException;
import univpm.ProgettoUV.model.*;
import univpm.ProgettoUV.stats.Statistiche;
import univpm.ProgettoUV.stats.StatsService;

 

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
	public JSONArray restituisciElenco2(
			@RequestParam("lat") String lat,
			@RequestParam("lon") String lon) throws WrongCoordinatesException  {
		String message = "";
		String[] listLat = lat.split(",");
		String[] listLon = lon.split(",");
		JSONArray out = new JSONArray(), value = new JSONArray();
		JSONObject[] tmp = new JSONObject[listLat.length];
		StatsService object = new Statistiche(1);
		
			for(int i =0;i<listLat.length;i++) {
	        	 String latElement = listLat[i];
	        	 String lonElement = listLon[i];
	        	
			double latitudine = Double.parseDouble(latElement);
			double longitudine = Double.parseDouble(lonElement);
			try {
	        String name = APICoordinates.getCityname(APICoordinates.caricaArray(), latElement, lonElement);
	        long id = APICoordinates.getCityId(APICoordinates.caricaArray(), latElement, lonElement);
	        
			
			message="lat:"+latitudine + " , lon:" + longitudine ; 
	

			tmp[i] = new JSONObject();
			tmp[i].put("name", name);
			tmp[i].put("coor:", message);
			tmp[i].put("valori", object.getData(id));
			out.add(tmp[i]);
			
			}catch(WrongCoordinatesException e) {
				System.out.println(e.getMessaggio());
				   out.clear();
				   JSONObject tmpp=new JSONObject();
				   tmpp.put("Error",e.getMessaggio());
				   out.add(tmpp);
				   return out;
			}
		}
		
			
			
		return out;			
	}
 

    @GetMapping(value = "/listaCittà", produces = "application/json")
    public JSONArray listaCit() throws FileNotFoundException {
        JSONArray obj = null, objOut = new JSONArray();
        JSONObject cityObject = new JSONObject(), coordinate = new JSONObject(), tmp = new JSONObject();
        String paese = new String(), nome = new String();
        int contaIT = 0;
        UtilityDati ut = new UtilityDati();
        obj = ut.leggi("city.list.json");

        for (int i = 0; i < obj.size(); i++) {
            cityObject = (JSONObject) obj.get(i);
            coordinate = (JSONObject) cityObject.get("coord");
            paese = (String) cityObject.get("country");
            nome = (String) cityObject.get("name");

 

            if (paese.equals("IT") && contaIT < 100) {
            	tmp = new JSONObject();
                tmp.put(nome, coordinate);
                objOut.add(tmp);
                contaIT++;
            }

 

        }
        return objOut;

 

    }
   
   
}


