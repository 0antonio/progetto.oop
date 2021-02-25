package univpm.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MainController {
	
	@GetMapping("/prova")
	public prova exampleMethod(@RequestParam(name = "param1", defaultValue = "bene") String param1) {
		return new prova("funziona", param1);
		}

}
