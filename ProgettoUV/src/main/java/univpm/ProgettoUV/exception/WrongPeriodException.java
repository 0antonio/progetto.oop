package univpm.ProgettoUV.exception;

import java.io.IOException;

public class WrongPeriodException extends IOException{

private static final long serialVersionUID = 1L;
	
	
	public WrongPeriodException() {
		super();
		
	}
	
	public String getMessaggio() {
		return "ERRORE: il periodo inserito non è valido";
	}
}
