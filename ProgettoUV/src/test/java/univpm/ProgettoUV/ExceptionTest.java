package univpm.ProgettoUV;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import univpm.ProgettoUV.exception.WrongCoordinatesException;
import univpm.ProgettoUV.exception.WrongFilterException;
import univpm.ProgettoUV.exception.WrongRangeException;
import univpm.ProgettoUV.model.APICoordinates;
import univpm.ProgettoUV.stats.Statistiche;
import univpm.ProgettoUV.stats.StatsService;

/**
 * Test per verificare il corretto utilizzo delle eccezioni
 * @author Di Lorenzo Emanuele
 * @author Giangrossi Antonio
 *
 */

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
	
	/**
	 * Test per l'eccezione WrongCoordinatesException. 
	 * Nel primo caso la longitudine inserita corrisponde
	 * a una città, ma non la latitudine. Nel secondo caso
	 * sono stati inseriti valori non numerici
	 * @throws WrongCoordinatesException
	 */
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
	
	/**
	 * Test per l'eccezione WrongFilterException. L'espressione condizionale
	 * per il verificarsi dell'eccezione è stata testata con valori inaccettabili
	 * per il parametro "filter"
	 * @throws WrongFilterException
	 */
	
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
	
	/**
	 * Test per l'eccezione WrongRangeException. L'espressione condizionale
	 * per il verificarsi dell'eccezione è stata testata con un valore negativo per il range
	 * @throws WrongFilterException
	 */
	
	
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
