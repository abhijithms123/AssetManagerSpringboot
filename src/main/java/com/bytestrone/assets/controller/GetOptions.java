package com.bytestrone.assets.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bytestrone.assets.service.OptionService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/options")
public class GetOptions {
	
	@Autowired
	private OptionService optionService;
	
	@GetMapping("softwareFields")
	public ResponseEntity<Map<String, Object>> getSoftwareOptions(){
		Map<String, Object> response = new HashMap<>();
		response.put("licenseType", optionService.getLicenseType());
		response.put("manufacturingCompany", optionService.getSoftwareManufacturingCompany());
		response.put("softwareCategory", optionService.getSoftwareCategory());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("hardwareFields")
	public ResponseEntity<Map<String, Object>> getHardwareOptions(){
		Map<String, Object> response = new HashMap<>();
		response.put("hardwareType", optionService.getHardwareType());
		response.put("ownershipStatus", optionService.getOwnershipStatus());
		response.put("manufacturingCompany", optionService.getHardwareManufacturingCompany());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
