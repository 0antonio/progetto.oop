package univpm.ProgettoUV.stats;

import org.json.simple.JSONArray;
/**
 * 
 * <p>
 * <b>Interfaccia</b> che calcola le <i>statistiche (media, varianza, max e min)</i> delle citta
 * scelte dall'utente
 * <p>
 * 
 * @author Giangrossi Antonio
 * @author Di lorenzo Emanuele
 *
 */

public interface StatsService {

	/**
	 * ritorna le statistiche delle città inserite
	 * 
	 * @param id indica l'id della città
	 * 
	 * 
	 * @return un <code>JSONArray</code> con le statistiche
	 */
	
	public abstract JSONArray generaStats(long id);

	/**
	 * ritorna l'indice dell'array 
	 * nel quale si trova la città con valore "id"
	 * 
	 * @param id indica l'id della città
	 * 
	 * 
	 * @return un <code>int</code> che indica l'indice
	 */
	public abstract int trovaIndice(long id);

	/**
	 * ritorna il valore massimo del range inserito
	 * 
	 * @param periodo indica il numero di giorni 
	 * 
	 * 
	 * @return un <code>double</code> che indica il valore massimo
	 */
	public abstract double massimo(double[] periodo);

	/**
	 * ritorna il valore minimo del range inserito
	 * 
	 * @param periodo indica il numero di giorni 
	 * 
	 * 
	 * @return un <code>double</code> che indica il valore minimo
	 */
	
	public abstract double minimo(double[] periodo);

	/**
	 * ritorna la media del range inserito
	 * 
	 * @param periodo indica il numero di giorni 
	 * 
	 * 
	 * @return un <code>double</code> che indica la media
	 */
	
	public abstract double media(double[] periodo);
	
	/**
	 * ritorna la varianza del range inserito
	 * 
	 * @param periodo indica il numero di giorni 
	 * 
	 * 
	 * @return un <code>double</code> che indica la varianza
	 */
	public abstract double varianza(double[] periodo);

	/**
	 * ritorna un JSONArray che converte i dt in una data
	 * 
	 * @param id indica l'indice della città
	 * 
	 * 
	 * @return un <code>JSONArray</code> che fa la conversione del tempo
	 */
	
	public abstract JSONArray getData(long id);

	/**
	 * ritorna il numero di giorni in cui sono stati acquisiti i dati
	 *  
	 * 
	 * 
	 * @return un <code>int</code> 
	 */
	
	public abstract int giorniDisponibili();
	
	
}
