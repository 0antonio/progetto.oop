package univpm.ProgettoUV;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import univpm.ProgettoUV.controller.StatsController;
import univpm.ProgettoUV.exception.WrongCoordinatesException;
import univpm.ProgettoUV.exception.WrongFilterException;
import univpm.ProgettoUV.exception.WrongRangeException;
import univpm.ProgettoUV.model.APICoordinates;
import univpm.ProgettoUV.model.GestoreRotte;
import univpm.ProgettoUV.stats.Statistiche;
import univpm.ProgettoUV.stats.StatsService;

public class ExceptionTest {
	private String lat;
	private String lon;
	private String filter;
	private int range;
	@BeforeEach
	void setUp() throws Exception{
		range = 1;
		filter = "min";
	}
	
	
	
	@AfterEach
	void tearDown() throws Exception {

	}
	
	
	@Test
	void testWrongCoordinates() throws WrongCoordinatesException{
	assertThrows(WrongCoordinatesException.class, () -> {
		lon = "47.159401";
		lat = "0.2";
		APICoordinates.getCityId(APICoordinates.caricaArray(), lat, lon);
	}) ;
	assertThrows(WrongCoordinatesException.class, () -> {
		lat = "%/";
		lon = "a$";
		APICoordinates.getCityId(APICoordinates.caricaArray(), lat, lon);
	}) ;
	
	}
	
	@Test
	void testWrongFilter() throws WrongFilterException{
	assertThrows(WrongFilterException.class, () -> {
		filter = "ma";
		StatsService st = new Statistiche(range);
		if (range <= 0 || range > st.giorniDisponibili()) {
			throw new WrongRangeException();
		}
		if (!(filter.equals("max") || filter.equals("min") || filter.equals("no"))) {
			throw new WrongFilterException();
		}
	}) ;
	
	assertThrows(WrongFilterException.class, () -> {
		filter = "3";
		StatsService st = new Statistiche(range);
		if (range <= 0 || range > st.giorniDisponibili()) {
			throw new WrongRangeException();
		}
		if (!(filter.equals("max") || filter.equals("min") || filter.equals("no"))) {
			throw new WrongFilterException();
		}
	}) ;
	
	assertThrows(WrongFilterException.class, () -> {
		filter = "max-$";
		StatsService st = new Statistiche(range);
		if (range <= 0 || range > st.giorniDisponibili()) {
			throw new WrongRangeException();
		}
		if (!(filter.equals("max") || filter.equals("min") || filter.equals("no"))) {
			throw new WrongFilterException();
		}
	}) ;

	
	}
	
	@Test 
	void testWrongRange() throws WrongRangeException{
		
		assertThrows(WrongRangeException.class, () -> {
			range = -1;
			StatsService st = new Statistiche(range);
			if (range <= 0 || range > st.giorniDisponibili()) {
				throw new WrongRangeException();
			}
			if (!(filter.equals("max") || filter.equals("min") || filter.equals("no"))) {
				throw new WrongFilterException();
			}
		}) ;
		
	}


}
