package univpm.ProgettoUV.stats;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * <p>
 * <b>Classe</b> che prende le <i>statistiche</i> delle città scelte dall'utente
 * in un <i>range</i> e mostra all'utente quale citta ha media max o min
 * <p>
 * 
 * @author Giangrossi Antonio
 * @author Di lorenzo Emanuele
 */

public class MinMax {
	private JSONArray statistiche;
	private double[][] mediaMatrix; // matrice dove la riga indica la città i-esima e la colonna il periodo j-esimo

	/**
	 * Costrutture della classe MinMax
	 * 
	 * @param statistiche <code>JSONArray </code> contenente le statistiche
	 *                    elaborate con la classe Statistiche
	 */
	public MinMax(JSONArray statistiche) {
		this.statistiche = statistiche;

	}

	/**
	 * ritorna la media max o min in un determinato periodo tra le città scelte
	 * 
	 * @param operatore indica la stringa inserita dall'utente per il filtraggio
	 * 
	 * 
	 * @return un <code>JSONArray</code>
	 */

	public JSONArray getMinMax(String operatore) {
		JSONArray out = new JSONArray();
		JSONArray statsCitta = (JSONArray) ((JSONObject) statistiche.get(0)).get("stats");
		String[] nome = new String[statsCitta.size()];
		double[] valore = new double[statsCitta.size()];
		for (int i = 0; i < statsCitta.size(); i++) { // inizializzo con i valori della prima città
			nome[i] = (String) ((JSONObject) statistiche.get(0)).get("name");
			if (!((String) ((JSONObject) statsCitta.get(i)).get("Dati archivio")).equals("ASSENTI")) {
				valore[i] = (double) ((JSONObject) statsCitta.get(i)).get("media");
			} else {
				valore[i] = -1.0;
			}
		}
		if (operatore.equals("max")) {
			for (int i = 1; i < statistiche.size(); i++) {
				
					JSONArray statsCittaI = (JSONArray) ((JSONObject) statistiche.get(i)).get("stats"); // prendo le
																										// statistiche
																										// dell'i-esima
																										// città
					for (int j = 0; j < statsCitta.size(); j++) {
						if (!((String) ((JSONObject) statsCitta.get(j)).get("Dati archivio")).equals("ASSENTI")) {

						double tmp = (double) ((JSONObject) statsCittaI.get(j)).get("media");
						if (tmp > valore[j]) {
							nome[j] = (String) ((JSONObject) statistiche.get(i)).get("name");
							valore[j] = (double) ((JSONObject) statsCittaI.get(j)).get("media");
						}
					}

				}
			}
		}

		if (operatore.equals("min")) {
			for (int i = 1; i < statistiche.size(); i++) {

					JSONArray statsCittaI = (JSONArray) ((JSONObject) statistiche.get(i)).get("stats"); // prendo le
																										// statistiche
																										// dell'i-esima
																										// città
					for (int j = 0; j < statsCitta.size(); j++) {

						if (!((String) ((JSONObject) statsCitta.get(j)).get("Dati archivio")).equals("ASSENTI")) {
						double tmp = (double) ((JSONObject) statsCittaI.get(j)).get("media");
						if (tmp < valore[j]) {
							nome[j] = (String) ((JSONObject) statistiche.get(i)).get("name");
							valore[j] = (double) ((JSONObject) statsCittaI.get(j)).get("media");
						}
					}

				}
			}
		}
		JSONObject[] obj = new JSONObject[statsCitta.size()];
		for (int i = 0; i < statsCitta.size(); i++) {
			obj[i] = new JSONObject();
			if (((String) ((JSONObject) statsCitta.get(i)).get("Dati archivio")).equals("COMPLETI")) {
				obj[i].put("name", nome[i]);
				obj[i].put("media", valore[i]);
				obj[i].put("periodo", i);
				obj[i].put("Dati archivio", "COMPLETI");
			} else if (((String) ((JSONObject) statsCitta.get(i)).get("Dati archivio")).equals("PARZIALI")) {
				obj[i].put("name", nome[i]);
				obj[i].put("media", valore[i]);
				obj[i].put("periodo", i);
				obj[i].put("Dati archivio", "PARZIALI");
			} else {
				obj[i].put("periodo", i);
				obj[i].put("Dati archivio", "ASSENTI");
			}
			out.add(obj[i]);

		}

		return out;
	}

}
