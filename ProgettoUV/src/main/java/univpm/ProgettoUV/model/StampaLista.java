package univpm.ProgettoUV.model;

import java.util.HashSet;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * <p> Classe per aggiornare il file "listaValori.json" </p>
 * @author Giangrossi Antonio
 * @author Di Lorenzo Emanuele
 *
 */
public class StampaLista extends UtilityDati {
	/**
	 * <p> metodo per aggiornare il file "listaValori.json"
	 * @return <code>JSONArray</code> contenente i dati di "city.list.json"
	 */

	public JSONArray stampa() {
		JSONArray obj = null;
		JSONObject cityObject = null;
		JSONObject coordinate = null;
		JSONArray jarr = new JSONArray();
		double latitudine = 0.0;
		double longitudine = 0.0;
		Object ID = null;
		JSONObject[] tmp = new JSONObject[100]; // numero di città selezionate
		String paese = new String();
		String name = new String();
		int contaIT = 0;
		obj = leggi("city.list.json");

		JSONArray objOld = leggi("listaValori.json"); // copia il file vecchio in un array
		Set<String> cittaPassate = new HashSet<String>();
		JSONArray timeAndVal = new JSONArray();
		int j = 0;
		String italia = "IT";
		JSONObject firstTimeAndVal;
		JSONObject objectOld;
		JSONArray valuesOld;
		JSONObject lastValuesOld;
		long firstTime, lastTime, diff, numDiff;
		JSONArray timeAndValUpdated;
		for (int i = 0; i < obj.size(); i++) {
			cityObject = (JSONObject) obj.get(i);
			coordinate = (JSONObject) cityObject.get("coord");
			latitudine = (Double) coordinate.get("lat");
			longitudine = (Double) coordinate.get("lon");
			ID = cityObject.get("id");
			paese = (String) cityObject.get("country");
			name = (String) cityObject.get("name");

			if (paese.equals(italia) && contaIT < 100 && (!cittaPassate.contains(name))) {
				cittaPassate.add(name);
				tmp[j] = new JSONObject();
				tmp[j].put("id", ID);

				timeAndVal = APICoordinates.getCoordinates(latitudine, longitudine);

				firstTimeAndVal = (JSONObject) timeAndVal.get(0);
				firstTime = (long) firstTimeAndVal.get("dt");

				objectOld = (JSONObject) objOld.get(contaIT);

				valuesOld = (JSONArray) objectOld.get("values");
				lastValuesOld = (JSONObject) valuesOld.get(valuesOld.size() - 1);
				lastTime = (long) lastValuesOld.get("dt");

				diff = lastTime - firstTime;
				numDiff = diff / 3600;

				for (int k = 0; k <= numDiff; k++) {
					valuesOld.remove(valuesOld.size() - 1);
				}
				timeAndValUpdated = new JSONArray();
				for (int cont = 0; cont < valuesOld.size(); cont++) {
					timeAndValUpdated.add(valuesOld.get(cont));
				}
				
				// qui aggiungo i -1.0
				for(int cont = 1; cont<(-numDiff); cont++) {
					JSONObject buffer = new JSONObject();
					buffer.put("dt", lastTime+3600*cont);
					buffer.put("uvi", -1.0);
					timeAndValUpdated.add(buffer);
				}
				
				
				for (int cont = 0; cont < timeAndVal.size(); cont++) {
					timeAndValUpdated.add(timeAndVal.get(cont));
				}

				JSONArray timeAndValUp = (JSONArray) timeAndValUpdated.clone();

				tmp[j].put("values", timeAndValUp);
				tmp[j].put("name", name);
				jarr.add(tmp[j]);
				j++;
				contaIT++;
			}

		}
		scrivi("listaValori.json", jarr);
		System.out.println("fatto");

		return obj;
	}

}
