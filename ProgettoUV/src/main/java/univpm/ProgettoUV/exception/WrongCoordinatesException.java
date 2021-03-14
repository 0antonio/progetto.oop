package univpm.ProgettoUV.exception;

import java.io.IOException;
/**
 *<p>
 *L'eccezione <b>WrongCoordinatesException</b> estende <b>IOException</b>.
 *Questa eccezione viene generata quando l'utente inserisce delle coordinate sbagliate o che non sono 
 *presenti nel file.
 *</p>
 *
 * @author Giangrossi Antonio
 * @author Di lorenzo Emanuele
 * 
 */

public class WrongCoordinatesException extends IOException {

	private static final long serialVersionUID = 1L;
	

	/**
	 * Costruttore della classe <b>WrongCoordinatesException</b>
	 * 
	 */
	
	public WrongCoordinatesException() {
		super();
		
	}
	

	/**
	 * Questo metodo ritorna una stringa che descrive l'errore
	 * 
	 * @return <code>String</code>
	 * 
	 */
	
	
	public String getMessaggio() {
		return "ERRORE: le coordinate inserite non sono corrette";
	}
}
