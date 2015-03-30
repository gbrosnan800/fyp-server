package com.gbrosnan.fyp.rest;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gbrosnan.fyp.objects.CSVRequest;

@RestController
@RequestMapping("/rest/csv")
public class CreateCSV {

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String hello2(@RequestBody List<CSVRequest> csvRequestList) {
    	
    	for(CSVRequest request : csvRequestList) {
    		System.out.println(request.getCollectionName() + "   " + request.getId());
    	}
    	return "{\"message\":\"ok\"}";
    }
	
}
