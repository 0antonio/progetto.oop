package univpm.ProgettoUV.exception;

import java.io.IOException;

/**
 *<p>
 *L'eccezione <b>WrongRangeException</b> estende <b>IOException</b>.
 *Questa eccezione viene generata quando l'utente inserisce un range sbagliato come un numero
 *negativo oppure se il range supera il numero di giorni per il quale sono stati presi i dati.
 *</p>
 *
 * @author Giangrossi Antonio
 * @author Di lorenzo Emanuele
 * 
 */

public class WrongRangeException extends IOException {
private static final long serialVersionUID = 1L;


/**
 * Costruttore della classe <b>WrongRangeException</b>
 * 
 */
	
	public WrongRangeException() {
		super();
		
	}
	

	/**
	 * Questo metodo ritorna una stringa che descrive l'errore
	 * 
	 * @return <code>String</code>
	 * 
	 */
	
	public String getMessaggio() {
		return "ERRORE: il range inserito non è valido";
	}

}
