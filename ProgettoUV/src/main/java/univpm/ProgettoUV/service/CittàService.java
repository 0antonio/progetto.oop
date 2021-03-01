package univpm.ProgettoUV.service;

import java.util.Collection;

import univpm.ProgettoUV.model.Città;

public interface CittàService {
	public void inserisciCittà(String city, String country);
	public Collection<Città> getCittà();	

}
