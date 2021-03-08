package univpm.ProgettoUV;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import univpm.ProgettoUV.stats.Stats;

@SpringBootApplication
public class ProgettoUvApplication {

	public static void main(String[] args) {
		
		Stats.caricaStats();
		SpringApplication.run(ProgettoUvApplication.class, args);
	}

}
