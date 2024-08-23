package com.service.common;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transunion")
public class TransunionController {
	@GetMapping("/{id}")
	public Integer getScore(@PathVariable("id") String id) throws InterruptedException {
		if(null != id) {
			return (int) ((Math.random() * (900 - 550)) + 550);
		}
		return 0;
		
	}
}
