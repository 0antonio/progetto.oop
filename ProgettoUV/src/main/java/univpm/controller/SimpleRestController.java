package univpm.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import univpm.model.HelloWorldClass;

@RestController
public class SimpleRestController {
	@GetMapping("/hello")
	public HelloWorldClass exampleMethod(@RequestParam(name = "param1", defaultValue = "World") String param1) {
		
		return new HelloWorldClass("prova", "funziona");
	}

}
