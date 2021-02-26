package univpm.ProgettoUV.exception;

import java.io.IOException;

public class WrongCoordinatesException extends IOException {

	private static final long serialVersionUID = 1L;
	
	
	public WrongCoordinatesException() {
		super();
		
	}
	
	public String getMessaggio() {
		return "ERRORE: le coordinate inserite non sono corrette";
	}
}
