package univpm.ProgettoUV.stats;

import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.expression.spel.ast.OperatorInstanceof;

import univpm.ProgettoUV.model.UtilityDati;

public class Stats2 extends UtilityDati implements StatsService {
	private final String fileName = "listaValori.json";
	private JSONArray lista; // tutti i valori di listaValori
	private int numGiorni; //numGiorni è il numero di giorni su cui effettuare le statistiche, scelto dall'utente
	private int lengthVal; // numero di valori acquisiti da openweather
	private JSONObject[] città; // array in cui l'i-esimo elemento è il JSONObject della città, il quale ha chiavi "values", "name" e "id"
	private JSONArray[] listaValues; // array in cui l'i-esimo elemento è il JSONArray "values" dell'i-esima città
	private long[] tempo; // vettore di timestamps
	private long ultimoTempo; //timestapm che indica la mezzanotte più recente
	private int indiceUltimoTempo; // indice dell'ultimo time stamp
	private double[][] uvi; // matrice di uvi. il primo valore cicla sulle città, il secondo su "uvi"

	public Stats2(int numGiorni) { // id della città e numGiorni scelto dall'utente per fare le statistiche (giornaliera, bigiornaliera...)
		lista = leggi();
		this.numGiorni = numGiorni;
		città = new JSONObject[lista.size()];
		listaValues = new JSONArray[lista.size()];
		// ciclo sulle città
		for(int i =0; i<lista.size();i++) {
			città[i] = (JSONObject) lista.get(i);
			if (i==0) { // entra solo una volta nel ciclo
				lengthVal = ((JSONArray) città[0].get("values")).size();
				tempo = new long[lengthVal];
				uvi = new double[lista.size()][lengthVal];
			}
			listaValues[i] = (JSONArray) città[i].get("values");
			
			for(int j=0;j<lengthVal;j++) {
				uvi[i][j] = ((Number) ((JSONObject) ((JSONArray) listaValues[i]).get(j)).get("uvi")).doubleValue();
                
			}
		}
		
		JSONArray valoriPrimaCittà = listaValues[0];
		for(int i=0;i<lengthVal;i++) {
			tempo[i] = (long) ((JSONObject) valoriPrimaCittà.get(i)).get("dt");
		}
		int kCont = 0;
		for(int k = (lengthVal-1); k>=0;k--) {
			System.out.println();
			if((tempo[k]/3600)%24==0) {
				kCont = k;
				break;
			}
		}
		ultimoTempo = tempo[kCont];	
		indiceUltimoTempo = kCont;
	}
	
	@Override
	public JSONArray generaStats(long id) { // genera le statistiche per la città di cui fornisco l'id
		JSONArray out = new JSONArray();
		JSONObject tmp = new JSONObject();
		int indice = trovaIndice(id);
		int numPeriodi = indiceUltimoTempo/(24*numGiorni); // numero di periodi restituiti
		// ciclo sui periodi
		double[] uviInPeriodo = new double[24*numGiorni];
		for(int i = 0; i<numPeriodi;i++) {
			for(int j = 0; j < 24*numGiorni; j++) {

				uviInPeriodo[j] = uvi[indice][indiceUltimoTempo-j-i*24*numGiorni]; //???
			}
			tmp.put("periodo numero", i); // dal più alto al più basso, il periodo 100 è antico
			tmp.put("media", media(uviInPeriodo));
			tmp.put("max", massimo(uviInPeriodo));
			tmp.put("min", minimo(uviInPeriodo));
			tmp.put("varianza", varianza(uviInPeriodo));
			out.add(tmp.clone());
		}
		
		
		return out;
	}
	
	@Override
	public int trovaIndice(long id) { // trova in quale posizione dell'array si trova la città con valore "id"
		int indice = 0;
		for(int i = 0; i<città.length ;i++) {
			if(((long) città[i].get("id"))==id) {
				indice = i;
				break;
			}
		}
		return indice;
	}
	
	@Override
	public double massimo(double[] periodo) {
		double out = periodo[0];
		for(int i = 1; i < periodo.length; i++) {
			if(periodo[i]>out) {
				out = periodo[i];
			}
		}
		return out;
	}
	
	@Override
	public double minimo(double[] periodo) {
		double out = periodo[0];
		for(int i = 1; i < periodo.length; i++) {
			if(periodo[i]<out) {
				out = periodo[i];
			}
		}
		return out;
	}
	
	@Override
	public double media(double[] periodo) {
		double out = 0;
		for(int i = 1; i < periodo.length; i++) {
			out = out +periodo[i];
		}
		return out/periodo.length;
	}
	
	@Override
	public double varianza(double[] periodo) {
		double out = 0;
		double med = media(periodo);
		for(int i = 1; i < periodo.length; i++) {
			out = out + (periodo[i]-med)*(periodo[i]-med);
		}
		return out/periodo.length;
	}
	
	
	private JSONArray leggi() {
		JSONArray obj= new JSONArray();
		/*
		try {
		JSONParser jsonParser = new JSONParser();
         //FileReader reader = new FileReader("city.list.json"); ho cambiato questo
		FileReader reader = new FileReader(fileName);
		//Read JSON file
			
			
				obj = (JSONArray) jsonParser.parse(reader);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		obj = leggi(fileName);
		return obj;
	}
	
	@Override
	public JSONArray getData(long id) {
		JSONArray out = new JSONArray();
		JSONObject[] tmp = new JSONObject[tempo.length];
		int in = trovaIndice(id);
		for(int i = 0; i< tempo.length; i++) {
			tmp[i] = new JSONObject();
			tmp[i].put("uvi", uvi[in][i]);
			
			Date time=new Date ((long)tempo[i]*1000); // timestamp espresso in millisecondi
			//DateFormat dateFormatter;
			//dateFormatter = DateFormat.getDateInstance(DateFormat.LONG);
			//String dateOut = dateFormatter.format(time);
		
			tmp[i].put("data", time.toString());
			out.add(tmp[i]);
		}
		return out;
	}
	
	public int giorniDisponibili() {
		return lengthVal/24;
	}

}
