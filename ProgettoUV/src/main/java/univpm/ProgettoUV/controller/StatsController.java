package univpm.ProgettoUV.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

import univpm.ProgettoUV.exception.WrongCoordinatesException;
import univpm.ProgettoUV.exception.WrongFilterException;
import univpm.ProgettoUV.exception.WrongRangeException;
import univpm.ProgettoUV.model.APICoordinates;
import univpm.ProgettoUV.stats.MinMax;
import univpm.ProgettoUV.stats.Stats;
import univpm.ProgettoUV.stats.Stats2;

@RestController
public class StatsController {

	@GetMapping(value = "/stats", produces = "application/json")
	public JSONArray restituisciElenco(@RequestParam("lat") String lati, @RequestParam("lon") String longi,
			@RequestParam(value = "range", defaultValue = "1") int range) throws WrongCoordinatesException {

		String message = "";

		String[] lat = lati.split(",");
		String[] lon = longi.split(",");
		JSONArray out = new JSONArray(), value = new JSONArray();
		Vector<Long> dtgiorno = new Vector<>();

		for (int i = 0; i < lat.length; i++) {
			String lonElement = lon[i];
			String latElement = lat[i];

			String name = APICoordinates.getCityname(APICoordinates.caricaArray(), latElement, lonElement);
			long id = APICoordinates.getCityId(APICoordinates.caricaArray(), latElement, lonElement);
			Vector<Double> uv = Stats.getUv(Stats.caricaStats(), id);
			Vector<Long> dt = Stats.getDt(Stats.caricaStats(), id);
			for (Long element : dt) {
				if ((element / 3600) % 24 == 0) {
					dtgiorno.add(element);
				}
			}

			JSONObject tmp = new JSONObject();

			tmp.put("name", name);
			tmp.put("lat", latElement);
			tmp.put("lon", lonElement);

			for (int j = 0; j < dtgiorno.size(); j = j + range) {
				JSONObject statsobj = new JSONObject();
				statsobj.put("mediaUV", Stats.media(uv, dt, dtgiorno.get(j)));
				statsobj.put("maxUV", Stats.getMax(uv, dt, dtgiorno.get(j)));
				statsobj.put("minUV", Stats.getMin(uv, dt, dtgiorno.get(j)));
				statsobj.put("varianzaUV",
						Stats.varianza(uv, dt, dtgiorno.get(j), Stats.media(uv, dt, dtgiorno.get(j))));
				tmp.put("stats" + j + " - " + (j + range) + "giorni", statsobj);
			}

			out.add(tmp);

		}

		return out;
	}

	@GetMapping(value = "/stats2", produces = "application/json")
	public JSONArray restituisciElenco2(@RequestParam("lat") String lati, @RequestParam("lon") String longi,
			@RequestParam(value = "range", defaultValue = "1") int range, @RequestParam(value = "filter", defaultValue = "no") String filter)
			throws WrongCoordinatesException,WrongRangeException,WrongFilterException{

		String message = "";

		String[] lat = lati.split(",");
		String[] lon = longi.split(",");
		JSONArray out = new JSONArray(), value = new JSONArray();
		Vector<Long> dtgiorno = new Vector<>();
		

		try {
           if(range<=0 || range>10) {
        	 throw new  WrongRangeException();
			}
		}
		catch(WrongRangeException e){
		   System.out.println(e.getMessaggio());
		   out.clear();
		   JSONObject tmp=new JSONObject();
		   tmp.put("Error",e.getMessaggio());
		   out.add(tmp);
		   return out;
		}

		try {
	           if(!(filter.equals("max") || filter.equals("min") || filter.equals("no"))) {
	        	 throw new  WrongFilterException();
				}
			}
			catch(WrongFilterException e){
			   System.out.println(e.getMessaggio());
			   out.clear();
			   JSONObject tmp=new JSONObject();
			   tmp.put("Error",e.getMessaggio());
			   out.add(tmp);
			   return out;
			}

		
		Stats2 st = new Stats2(range);

		for (int i = 0; i < lat.length; i++) {
			String lonElement = lon[i];
			String latElement = lat[i];

			String name = APICoordinates.getCityname(APICoordinates.caricaArray(), latElement, lonElement);
			long id = APICoordinates.getCityId(APICoordinates.caricaArray(), latElement, lonElement);
			Vector<Double> uv = Stats.getUv(Stats.caricaStats(), id);
			Vector<Long> dt = Stats.getDt(Stats.caricaStats(), id);
			for (Long element : dt) {
				if ((element / 3600) % 24 == 0) {
					dtgiorno.add(element);
				}
			}

			JSONObject tmp = new JSONObject();

			tmp.put("name", name);
			tmp.put("lat", latElement);
			tmp.put("lon", lonElement);

			
			JSONArray res = st.generaStats(id);
			tmp.put("stats", res);
			/*
			 * for(int j=0;j<dtgiorno.size();j=j+range) { JSONObject statsobj = new
			 * JSONObject(); statsobj.put("mediaUV", Stats.media(uv,dt,dtgiorno.get(j)));
			 * statsobj.put("maxUV", Stats.getMax(uv,dt,dtgiorno.get(j)));
			 * statsobj.put("minUV", Stats.getMin(uv,dt,dtgiorno.get(j)));
			 * statsobj.put("varianzaUV", Stats.varianza(uv,dt,dtgiorno.get(j),
			 * Stats.media(uv,dt,dtgiorno.get(j))));
			 * tmp.put("stats"+j+" - "+(j+range)+"giorni",statsobj); }
			 */

			out.add(tmp);

		}
		
		
		if (filter.equals("no")) {
			return out;
		} else {
			MinMax mm = new MinMax(out);
			return mm.getMinMax(filter);
		}
	}
	

}
