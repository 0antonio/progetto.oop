package univpm.ProgettoUV.exception;

import java.io.IOException;

public class WrongRangeException extends IOException {
private static final long serialVersionUID = 1L;
	
	
	public WrongRangeException() {
		super();
		
	}
	
	public String getMessaggio() {
		return "ERRORE: il range inserito non è valido";
	}

}
