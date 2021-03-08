package univpm.ProgettoUV.stats;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class MinMax {
	private JSONArray statistiche;
	private double[][] mediaMatrix; // matrice dove la riga indica la città i-esima e la colonna il periodo j-esimo

	public MinMax(JSONArray statistiche) {
		this.statistiche = statistiche;

	}

	public JSONArray getMinMax(String operatore) {
		JSONArray out = new JSONArray();
		JSONArray statsCittà = (JSONArray) ((JSONObject) statistiche.get(0)).get("stats");
		String[] nome = new String[statsCittà.size()];
		double[] valore = new double[statsCittà.size()];
		for (int i = 0; i < statsCittà.size(); i++) { // inizializzo con i valori della prima città
			nome[i] = (String) ((JSONObject) statistiche.get(0)).get("name");
			valore[i] = (double) ((JSONObject) statsCittà.get(i)).get("media");
		}
		if (operatore.equals("max")) {
			for (int i = 1; i < statistiche.size(); i++) {
				JSONArray statsCittàI = (JSONArray) ((JSONObject) statistiche.get(i)).get("stats"); // prendo le
																									// statistiche
																									// dell'i-esima
																									// città
				for (int j = 0; j < statsCittà.size(); j++) {
					double tmp = (double) ((JSONObject) statsCittàI.get(j)).get("media");
					if (tmp > valore[j]) {
						nome[j] = (String) ((JSONObject) statistiche.get(i)).get("name");
						valore[j] = (double) ((JSONObject) statsCittàI.get(j)).get("media");
					}
				}

			}
		}

		if (operatore.equals("min")) {
			for (int i = 1; i < statistiche.size(); i++) {
				JSONArray statsCittàI = (JSONArray) ((JSONObject) statistiche.get(i)).get("stats"); // prendo le
																									// statistiche
																									// dell'i-esima
																									// città
				for (int j = 0; j < statsCittà.size(); j++) {
					double tmp = (double) ((JSONObject) statsCittàI.get(j)).get("media");
					if (tmp < valore[j]) {
						nome[j] = (String) ((JSONObject) statistiche.get(i)).get("name");
						valore[j] = (double) ((JSONObject) statsCittàI.get(j)).get("media");
					}
				}

			}
		}
		JSONObject[] obj = new JSONObject[statsCittà.size()];
		for (int i = 0; i < statsCittà.size(); i++) {
			obj[i] = new JSONObject();
			obj[i].put("name", nome[i]);
			obj[i].put("media", valore[i]);
			obj[i].put("periodo", i);
			out.add(obj[i]);

		}

		return out;
	}

}
