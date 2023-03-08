package com.bytestrone.assets.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bytestrone.assets.model.SoftwareRequest;
import com.bytestrone.assets.service.SoftwareRequestService;

@RequestMapping("/requests/software")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class SoftwareRequestContoller {
	
	@Autowired
	private SoftwareRequestService requestService;
	
	//to get the list of software requests made by a user based on request status.
	@GetMapping("viewAll/{requestStatus}/{requestedBy}/{pageNumber}/{pageSize}")
	public ResponseEntity<Map<String,Object>> viewAll(@PathVariable String requestStatus, @PathVariable String requestedBy,@PathVariable Integer pageNumber, @PathVariable Integer pageSize){
		Page<SoftwareRequest> requests = requestService.getSoftwareRequests(requestStatus, requestedBy, pageNumber, pageSize);
		List<SoftwareRequest> requestList = requests.getContent();
		Map<String, Object> list = new HashMap<>();
		list.put("requestList", requestList);
		list.put("currentPage", requests.getNumber());
		list.put("totalItem", requests.getTotalElements());
		list.put("totalPages", requests.getTotalPages());
	    return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@PostMapping("/save")
	public void saveRequest(@RequestBody SoftwareRequest reqObj) {
		requestService.saveSoftwareRequest(reqObj);
	}
	
	@PutMapping("/update")
	public void updateRequest(@RequestParam ("status") String status, @RequestParam ("reqId")String requestId,@RequestParam("reason") String reason) {
		requestService.updateRequestStatus(status,requestId,reason);
		

	}
	
	@GetMapping("/count/{userName}")
    public ResponseEntity<List<Object[]>> getCountByStatus(@PathVariable("userName") String userName){
    	List<Object[]> count = requestService.getHardwareCountByStatus(userName);
    	return new ResponseEntity<>(count,HttpStatus.OK);
    }
	
	//to get the list of software requests for admin based on request status.
	@GetMapping("viewAll/{requestStatus}/{pageNumber}/{pageSize}")
	public ResponseEntity<Map<String,Object>> viewAll(@PathVariable String requestStatus,@PathVariable Integer pageNumber, @PathVariable Integer pageSize){
		Page<SoftwareRequest> requests = requestService.getRequestByStatus(requestStatus, pageNumber, pageSize);
		List<SoftwareRequest> requestList = requests.getContent();
		Map<String, Object> list = new HashMap<>();
		list.put("requestList", requestList);
		list.put("currentPage", requests.getNumber());
		list.put("totalItem", requests.getTotalElements());
		list.put("totalPages", requests.getTotalPages());
	    return new ResponseEntity<>(list, HttpStatus.OK);
	}
	

}
