package com.service.equifax;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/equifax")
public class EquifaxController {
	
	@GetMapping("/{id}")
	public Integer getScore(@PathVariable("id") String id) throws InterruptedException {
		if(null != id) {
			 return (int) ((Math.random() * (900 - 500)) + 500);
		}
		return 0;
		
	}

}
