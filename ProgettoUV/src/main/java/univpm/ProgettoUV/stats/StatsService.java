package univpm.ProgettoUV.stats;

import org.json.simple.JSONArray;

public interface StatsService {
	
	public abstract JSONArray generaStats(long id);
	
	public abstract int trovaIndice(long id);
	
	public abstract double massimo(double[] periodo);
	
	public abstract double minimo(double[] periodo);
	
	public abstract double media(double[] periodo);
	
	public abstract double varianza(double[] periodo);
	
	public abstract JSONArray getData(long id);
	
	public abstract int giorniDisponibili();
	
	
}
