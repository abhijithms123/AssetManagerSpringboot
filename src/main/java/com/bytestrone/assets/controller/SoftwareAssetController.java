package com.bytestrone.assets.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
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

import com.bytestrone.assets.model.SoftwareAsset;
import com.bytestrone.assets.service.SoftwareService;
import com.bytestrone.assets.viewobjects.SoftwareRequestModel;
import com.bytestrone.assets.viewobjects.SoftwareVO;
import com.bytestrone.assets.viewobjects.UpdateSoftwareRequestModel;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/software")
public class SoftwareAssetController {
	
	private String message = "message";
	private String status = "status"; 
	private String getSoftwareVo = "getSoftwareVo";

	@Autowired
	private SoftwareService softwareService;
	
	@Autowired
	private ModelMapper modelMapper;

	// Add Software Asset
	@PostMapping
	public ResponseEntity<Map<String, String>> saveSoftware(@RequestBody SoftwareRequestModel software) {
		Map<String, String> response = new HashMap<>();
		if (softwareService.saveSoftware(software)) {		
			response.put(message, "Software Asset Added");
			response.put(status, "true");
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} else {
			response.put(message, "Asset already existing");
			response.put(status, "false");
			return new ResponseEntity<>(response, HttpStatus.OK);

		}

	}
	
	/* Updating sub version, softwareCategory, licenceType, softwareName, purchased
	 * of a particular software asset by its ID 
	 */
	@PutMapping("update")
	public ResponseEntity<Map<String, String>> updateAsset(@RequestParam int softwareId, @RequestBody UpdateSoftwareRequestModel softwareAsset) {
		Map<String, String> response = new HashMap<>();
		if(softwareService.updateAsset(softwareId, softwareAsset)) {
			response.put(message, "Software Asset Updated");	
			response.put(status, "true");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.put(message, "Asset already existing");
			response.put(status, "false");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	// Get All Software
	@GetMapping
	public ResponseEntity<Map<String, Object>> getAllSoftwareAssets(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {

		// Calling service function
		Page<SoftwareAsset> pages = softwareService.getAllSoftwareAssets(page, size);

		// returning response data
		return responseData(pages, getSoftwareVo);

	}
	
	/* Getting all details of a particular software asset by its ID */
	@GetMapping("find")
	public ResponseEntity<Map<String, Object>> getSoftwareAsset(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam int softwareId) {
		Page<SoftwareAsset> pages = softwareService.getSoftwareAsset(page, size, softwareId);
		return responseData(pages, "getSoftwareModel");
	}

	// Search Software
	@GetMapping("search")
	public ResponseEntity<Map<String, Object>> searchSoftwareAssets(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam String searchTerm) {
		// Calling service function
		Page<SoftwareAsset> pages = softwareService.searchSoftwareItems(page, size, searchTerm);
		return responseData(pages, getSoftwareVo);

	}
	
	/* updating the status of a software asset to "inactive" */
	@PutMapping("update/status")
	public ResponseEntity<Map<String, String>> updateStatus(@RequestBody List<Integer> ids) {
		Map<String, String> response = new HashMap<>();
		if(softwareService.changeStatus(ids)) {
			response.put(message, "Status Updated to inactive");	
			response.put(status, "true");
			return new ResponseEntity<>(response, HttpStatus.OK);	
		}
		else {
			response.put(message, "Software Asset not found");	
			response.put(status, "false");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}
	//get all softwares for user
	@GetMapping("user/viewAll/{pageNumber}/{pageSize}")
	public ResponseEntity<Map<String, Object>> view(@PathVariable Integer pageNumber, @PathVariable Integer pageSize) {
		Page<SoftwareAsset> software = softwareService.getSoftwareAssetPagination(pageNumber, pageSize);
		List<SoftwareAsset> softwarelist = software.getContent();
		Map<String, Object> list = new HashMap<>();
		list.put("softwareAvaliableList", softwarelist);
		list.put("currentPage", software.getNumber());
		list.put("totalItem", software.getTotalElements());
		list.put("totalPages", software.getTotalPages());
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	//get the details of a single software asset for user
	@GetMapping("id/{id}")
	public ResponseEntity<SoftwareAsset> getById(@PathVariable("id") int id) {
		SoftwareAsset software = softwareService.getBySoftwareId(id);
		return new ResponseEntity<>(software, HttpStatus.OK);
	}
	
	//to update the status of a software asset
	@PutMapping("/update/{id}/{status}")
	public void updateAsset(@PathVariable int id,@PathVariable String status){
		softwareService.updateSoftwareAsset(id, status);
	}
	
	@GetMapping("usersearch/{searchTerm}")
	public ResponseEntity<List<SoftwareAsset>> getSoftwaresBySearch(@PathVariable("searchTerm") String searchTerm) {
		List<SoftwareAsset> searchResult = softwareService.getAssetBySearchTerm(searchTerm);
		if (searchResult != null) {
			return new ResponseEntity<>(searchResult, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}
	
	//filter softwares for user
	@GetMapping("filter")
	public ResponseEntity<Map<String, Object>> filterSofwtares(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,@RequestParam("softwareCategory") String category, @RequestParam("manufacturer") String manufacturer, @RequestParam("assignStatus") String status){
		
		Page<SoftwareAsset> softwareListByFilter = softwareService.filterSoftwareByCategoryAndManufacturer(page, size, category, manufacturer, status);
		
		
		return responseData(softwareListByFilter, getSoftwareVo);
		
	}
	

	
	


	// Response function - Creating response data
	public ResponseEntity<Map<String, Object>> responseData(Page<SoftwareAsset> pages, String formatVo) {
		if (!pages.isEmpty()) {
			List<SoftwareAsset> softwareItems = pages.getContent();
			Map<String, Object> response = new HashMap<>();
			if(formatVo.equals("getSoftwareVo")) {
				TypeToken<List<SoftwareVO>> outputData = new TypeToken<>() {			
				};
				List<SoftwareVO> data = modelMapper.map(softwareItems, outputData.getType());	//	Mapping List to list
				response.put("Softwares", data);
			}
			else {
				TypeToken<List<SoftwareAsset>> outputData = new TypeToken<>() {			
				};
				List<SoftwareAsset> data = modelMapper.map(softwareItems, outputData.getType());	//	Mapping List to list
				response.put("Softwares", data);
			}
			response.put("currentPage", pages.getNumber());
			response.put("totalItems", pages.getTotalElements());
			response.put("totalPages", pages.getTotalPages());
			return new ResponseEntity<>(response, HttpStatus.OK);

		}
		Map<String, Object> responseError = new HashMap<>();
		responseError.put("message", "error");
		return new ResponseEntity<>(responseError, HttpStatus.OK);
	}

}
