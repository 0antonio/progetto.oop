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

import univpm.ProgettoUV.stats.Stats2;
import univpm.ProgettoUV.stats.StatsService;

@RestController
public class StatsController {

	@GetMapping(value = "/stats", produces = "application/json")
	public JSONArray restituisciElenco2(@RequestParam("lat") String lati, @RequestParam("lon") String longi,
			@RequestParam(value = "range", defaultValue = "1") int range,
			@RequestParam(value = "filter", defaultValue = "no") String filter)
			throws WrongCoordinatesException, WrongRangeException, WrongFilterException {

		String message = "";

		String[] lat = lati.split(",");
		String[] lon = longi.split(",");
		JSONArray out = new JSONArray(), value = new JSONArray();
		Vector<Long> dtgiorno = new Vector<>();
		
		Stats2 st = new Stats2(range);

		try {
			if (range <= 0 || range > st.giorniDisponibili()) {
				throw new WrongRangeException();
			}
			if (!(filter.equals("max") || filter.equals("min") || filter.equals("no"))) {
				throw new WrongFilterException();
			}
		} catch (WrongRangeException e) {
			System.out.println(e.getMessaggio());
			out.clear();
			JSONObject tmp = new JSONObject();
			tmp.put("Error", e.getMessaggio());
			out.add(tmp);
			return out;
		} catch (WrongFilterException e) {
			System.out.println(e.getMessaggio());
			out.clear();
			JSONObject tmp = new JSONObject();
			tmp.put("Error", e.getMessaggio());
			out.add(tmp);
			return out;
		}


		for (int i = 0; i < lat.length; i++) {
			String lonElement = lon[i];
			String latElement = lat[i];
			try {
				String name = APICoordinates.getCityname(APICoordinates.caricaArray(), latElement, lonElement);
				long id = APICoordinates.getCityId(APICoordinates.caricaArray(), latElement, lonElement);


				JSONObject tmp = new JSONObject();

				tmp.put("name", name);
				tmp.put("lat", latElement);
				tmp.put("lon", lonElement);

				JSONArray res = st.generaStats(id);
				tmp.put("stats", res);
				out.add(tmp);
			} catch (WrongCoordinatesException e) {
				System.out.println(e.getMessaggio());
				out.clear();
				JSONObject tmp = new JSONObject();
				tmp.put("Error", e.getMessaggio());
				out.add(tmp);
				return out;
			}
		}

		if (filter.equals("no")) {
			return out;
		} else {
			MinMax mm = new MinMax(out);
			return mm.getMinMax(filter);
		}
	}

}
