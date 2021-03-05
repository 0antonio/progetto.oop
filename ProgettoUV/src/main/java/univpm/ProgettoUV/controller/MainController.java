package univpm.ProgettoUV.controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import univpm.ProgettoUV.exception.WrongCoordinatesException;
import univpm.ProgettoUV.model.*;
import univpm.ProgettoUV.stats.Stats;

 

@RestController
public class MainController {

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
    }

 
    
    @GetMapping(value = "/coorCittà", produces = "application/json")
    public JSONArray restituisciElenco(@RequestParam("lat") String lati, @RequestParam("lon") String longi)
            throws WrongCoordinatesException {
        long dtmax= 1999999999;
    	
    	String message = "";
        
        String[] lat = lati.split(",");
        String[] lon = longi.split(",");
        JSONArray out = new JSONArray(), value = new JSONArray();
        
        for(int i =0;i<lat.length;i++) {
        	 String lonElement = lon[i];
        	 String latElement = lat[i];
       

        String name = APICoordinates.getCityname(APICoordinates.caricaArray(), latElement, lonElement);
        long id=APICoordinates.getCityId(APICoordinates.caricaArray(), latElement, lonElement);
        Vector <Double> uv = Stats.getUv(Stats.caricaStats(),id);
        Vector <Long> dt = Stats.getDt(Stats.caricaStats(),id);
        JSONObject tmp = new JSONObject();
        
        
        
        tmp.put("name", name);
        tmp.put("lat", latElement);
        tmp.put("lon", lonElement);
		tmp.put("mediaUV", Stats.media(uv,dt,dtmax));
		tmp.put("maxUV", Stats.getMax(uv,dt,dtmax));
		tmp.put("minUV", Stats.getMin(uv,dt,dtmax));
		tmp.put("varianzaUV", Stats.varianza(uv,dt,dtmax, Stats.media(uv,dt,dtmax)));
        out.add(tmp);
        
         }
                   
          return out;
        }
        
 
 

    @GetMapping(value = "/listaCittà", produces = "application/json")
    public JSONArray listaCit() throws FileNotFoundException {
        JSONArray obj = null, objOut = new JSONArray();
        JSONObject cityObject = new JSONObject(), coordinate = new JSONObject(), tmp = new JSONObject();
        String paese = new String(), nome = new String();
        int contaIT = 0;

 

        try {
            JSONParser jsonParser = new JSONParser();
            JSONArray cityList = null;
            FileReader reader = new FileReader("city.list.json");
            // Read JSON file
            obj = (JSONArray) jsonParser.parse(reader);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

 

        for (int i = 0; i < obj.size(); i++) {
            // System.out.println(i);
            cityObject = (JSONObject) obj.get(i);

 

            coordinate = (JSONObject) cityObject.get("coord");
            paese = (String) cityObject.get("country");
            nome = (String) cityObject.get("name");

 

            if (paese.equals("IT") && contaIT < 100) {
                tmp.put(nome, coordinate);
                objOut.add(tmp);
                contaIT++;
            }

 

        }
        return objOut;

 

    }
}


