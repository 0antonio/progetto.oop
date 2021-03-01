package univpm.ProgettoUV.service;

import java.awt.List;
import java.util.ArrayList;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import univpm.ProgettoUV.model.Città;


@Service
public class CittàServiceImpl implements CittàService {
	private static ArrayList<Città> cittàRepo = new ArrayList();
	private final AtomicLong count = new AtomicLong();
	
	public CittàServiceImpl() {
		
		
	}
	
	@Override
	public void inserisciCittà(String name, String country) {
		Città nuova = new Città(name, country);
		if (CittàServiceImpl.contiene(nuova)) {
			// lancia eccezione elemento già inserito
		}
		cittàRepo.add(nuova);
	}
	@Override
	public ArrayList<Città> getCittà() {
		return cittàRepo;
	}
	

	public static boolean contiene(Città nuova) {
		for (int i = 0; i < cittàRepo.size(); i++) { 		      
	          if(cittàRepo.get(i)==nuova) 
	        	  return true;
	      }   
		return false;
		
	}
	
	

}
