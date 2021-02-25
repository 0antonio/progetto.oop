package univpm.ProgettoUV.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import univpm.ProgettoUV.model.*;

@RestController
public class MainController {
	@GetMapping("/hello")
	public Prova exampleMethod(@RequestParam(name = "param1", defaultValue = "world") String param1) {
		return new Prova("funziona", param1);
	}
}
