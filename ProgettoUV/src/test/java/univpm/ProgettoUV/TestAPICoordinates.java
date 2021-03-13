package univpm.ProgettoUV;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import univpm.ProgettoUV.exception.WrongCoordinatesException;
import univpm.ProgettoUV.model.APICoordinates;

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
	
	
	@Test
	void testCoordinate() {
		assertEquals (39.958611, lat);
		assertEquals(8.70889, lon);	
	}
	
	@Test
	void testId() {
		assertEquals(2522683, id);
	}

}
