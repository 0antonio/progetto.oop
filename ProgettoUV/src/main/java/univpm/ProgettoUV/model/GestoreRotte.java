package univpm.ProgettoUV.model;

import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import univpm.ProgettoUV.exception.WrongCoordinatesException;
import univpm.ProgettoUV.exception.WrongFilterException;
import univpm.ProgettoUV.exception.WrongRangeException;
import univpm.ProgettoUV.stats.MinMax;
import univpm.ProgettoUV.stats.Statistiche;
import univpm.ProgettoUV.stats.StatsService;

public class GestoreRotte {
	public JSONArray datiNoStats(String lat, String lon) {
		String message = "";
		String[] listLat = lat.split(",");
		String[] listLon = lon.split(",");
		JSONArray out = new JSONArray(), value = new JSONArray();
		JSONObject[] tmp = new JSONObject[listLat.length];
		StatsService object = new Statistiche(1);

		for (int i = 0; i < listLat.length; i++) {
			String latElement = listLat[i];
			String lonElement = listLon[i];

			double latitudine = Double.parseDouble(latElement);
			double longitudine = Double.parseDouble(lonElement);
			try {
				String name = APICoordinates.getCityname(APICoordinates.caricaArray(), latElement, lonElement);
				long id = APICoordinates.getCityId(APICoordinates.caricaArray(), latElement, lonElement);

				message = "lat:" + latitudine + " , lon:" + longitudine;

				tmp[i] = new JSONObject();
				tmp[i].put("name", name);
				tmp[i].put("coor:", message);
				tmp[i].put("valori", object.getData(id));
				out.add(tmp[i]);

			} catch (WrongCoordinatesException e) {
				System.out.println(e.getMessaggio());
				out.clear();
				JSONObject tmpp = new JSONObject();
				tmpp.put("Error", e.getMessaggio());
				out.add(tmpp);
			}

		}
		return out;
	}

	public JSONArray listaCompleta() {
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
	
	public JSONArray stats(String lati, String longi, int range, String filter) {
		String message = "";

		String[] lat = lati.split(",");
		String[] lon = longi.split(",");
		JSONArray out = new JSONArray(), value = new JSONArray();
		Vector<Long> dtgiorno = new Vector<>();
		
		StatsService st = new Statistiche(range);

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