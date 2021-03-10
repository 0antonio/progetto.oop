package univpm.ProgettoUV.exception;

import java.io.IOException;

public class WrongFilterException extends IOException{
private static final long serialVersionUID = 1L;
	
	
	public WrongFilterException() {
		super();
		
	}
	
	public String getMessaggio() {
		return "ERRORE: il filtro inserito non è valido";
	}
}
