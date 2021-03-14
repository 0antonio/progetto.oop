package univpm.ProgettoUV;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import univpm.ProgettoUV.exception.WrongCoordinatesException;
import univpm.ProgettoUV.model.APICoordinates;

/**
 * Test per verificare la correttezza deella classe APICoordinates
 * 
 * @author Di Lorenzo Emanuele
 * @author Giangrossi Antonio
 *
 */

public class TestAPICoordinates {
	private double lat;
	private double lon;
	private long id;
	private String name;
	private String country;
	
	@BeforeEach
	void setUp() throws Exception{
		name = "Zerfaliu";
		country = "IT";
		try {
			lat = APICoordinates.getCitylat(APICoordinates.caricaArray(),name,country);
			lon = APICoordinates.getCitylon(APICoordinates.caricaArray(),name,country);		
			id = APICoordinates.getCityId(APICoordinates.caricaArray(), String.valueOf(lat) , String.valueOf(lon));
		} catch (WrongCoordinatesException e) {
			e.printStackTrace();
		}
	}
	
	
	
	@AfterEach
	void tearDown() throws Exception {
	}
	
	/**
	 * Verifica che la chiamata restituisce le coordinate corrette
	 */
	@Test
	void testCoordinate() {
		assertEquals (39.958611, lat);
		assertEquals(8.70889, lon);	
	}
	
	/**
	 * verifica che l'id della città selezionata è corretto
	 */
	
	@Test
	void testId() {
		assertEquals(2522683, id);
	}

}
