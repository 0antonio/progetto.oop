package controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestController {
	@RequestMapping(value="/products")
	public ResponseEntity<Object> prova(){
		return 1;
	}

}
