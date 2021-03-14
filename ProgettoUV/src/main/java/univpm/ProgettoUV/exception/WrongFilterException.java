package univpm.ProgettoUV.exception;

import java.io.IOException;
/**
 *<p>
 *L'eccezione <b>WrongFilterException</b> estende <b>IOException</b>.
 *Questa eccezione viene generata quando l'utente inserisce una stringa diversa da 
 *max,min o no.
 *</p>
 *
 * @author Giangrossi Antonio
 * @author Di lorenzo Emanuele
 * 
 */

public class WrongFilterException extends IOException{
private static final long serialVersionUID = 1L;
	
/**
 * Costruttore della classe <b>WrongFilterException</b>
 * 
 */
	
	public WrongFilterException() {
		super();
		
	}
	
	

	/**
	 * Questo metodo ritorna una stringa che descrive l'errore
	 * 
	 * @return <code>String</code>
	 * 
	 */
	
	public String getMessaggio() {
		return "ERRORE: il filtro inserito non è valido";
	}
}
