package univpm.ProgettoUV;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import univpm.ProgettoUV.model.GestoreRotte;
import univpm.ProgettoUV.stats.Statistiche;
import univpm.ProgettoUV.stats.StatsService;

public class TestStatistiche {
	private long id1 = 2522677L, id2 = 2522683L; // 1 Zumpano, 2 Zerfaliu
	private int in1, in2;
	private StatsService st1, st2;
	private GestoreRotte gs;
	
	@BeforeEach
	void setUp() throws Exception{
		st1 = new Statistiche(1); // range = 1 
		st2 = new Statistiche(2); // range = 2
		gs = new GestoreRotte();

	}
	
	
	
	@AfterEach
	void tearDown() throws Exception {
	}
	
	
	@Test
	void indice() {
		in1 = st1.trovaIndice(id1);
		in2 = st2.trovaIndice(id2);
		assertEquals(0, in1);
		assertEquals(1, in2);
	}
	
	@Test
	void statisticheComplete() {
		JSONArray tmp1 = st1.generaStats(id1); // Zumpano, range = 1
		JSONArray tmp2 = st2.generaStats(id2); // Zerfaliu, range = 2
		int ind1 = tmp1.size()-1;
		int ind2 = tmp2.size()-1;
		
		assertEquals( 0.0 , ((JSONObject) tmp1.get(ind1)).get("min"));
		assertEquals( 2.526198680266203 , ((JSONObject) tmp1.get(ind1)).get("varianza"));
		assertEquals( 1.0515833333333335 , ((JSONObject) tmp1.get(ind1)).get("media"));
		assertEquals( 4.771 , ((JSONObject) tmp1.get(ind1)).get("max"));
		assertEquals( ind1 , ((JSONObject) tmp1.get(ind1)).get("periodo numero"));
		
		assertEquals( 0.0 , ((JSONObject) tmp2.get(ind2)).get("min"));
		assertEquals( 1.6584553567346652 , ((JSONObject) tmp2.get(ind2)).get("varianza"));
		assertEquals( 0.8012083333333334 , ((JSONObject) tmp2.get(ind2)).get("media"));
		assertEquals( 4.25 , ((JSONObject) tmp2.get(ind2)).get("max"));
		assertEquals( ind2 , ((JSONObject) tmp2.get(ind2)).get("periodo numero"));
		
	}
	

	@Test
	void verificaMinMax(){
		String lati = "39.31089,39.958611";
		String longi = "16.292089,8.70889";
		
		JSONArray tmp = gs.stats(lati, longi, 1, "max");
		JSONObject primoVal = (JSONObject) tmp.get(tmp.size()-1);
		JSONArray tmp2 = gs.stats(lati, longi, 1, "min");
		JSONObject primoVal2 = (JSONObject) tmp2.get(tmp2.size()-1);
		
		assertEquals(tmp.size()-1, primoVal.get("periodo"));
		assertEquals("Zumpano", primoVal.get("name"));
		assertEquals(1.0515833333333335, primoVal.get("media"));
		
		assertEquals(tmp2.size()-1, primoVal2.get("periodo"));
		assertEquals("Zerfaliu", primoVal2.get("name"));
		assertEquals(0.6474166666666666, primoVal2.get("media"));
	
	
	}


}
