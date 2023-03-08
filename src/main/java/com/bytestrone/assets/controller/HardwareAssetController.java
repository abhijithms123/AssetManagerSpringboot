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
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bytestrone.assets.model.HardwareAsset;
import com.bytestrone.assets.model.SoftwareAsset;
import com.bytestrone.assets.service.HardwareService;
import com.bytestrone.assets.viewobjects.HardwareRequestModel;
import com.bytestrone.assets.viewobjects.HardwareVO;
import com.bytestrone.assets.viewobjects.UpdateHardwareRequestModel;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/hardware")
public class HardwareAssetController {
	
	@Autowired
	private HardwareService hardwareService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	/* add hardware asset
	 * 
	 */
	@PostMapping
	public ResponseEntity<Map<String, String>> saveHardware(@RequestBody HardwareRequestModel hardware) {
		Map<String, String> response = new HashMap<>();
		if (hardwareService.saveHardware(hardware)) {		
			response.put("message", "Hardware Asset Added");
			response.put("status", "true");
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} else {
			response.put("message", "Asset already existing");
			response.put("status", "false");
			return new ResponseEntity<>(response, HttpStatus.OK);

		}

	}
	
	/* Updating sub version, softwareCategory, licenceType, softwareName, purchased
	 * of a particular software asset by its ID 
	 */
	@PutMapping("update")
	public ResponseEntity<Map<String, String>> updateHardware(@RequestParam int hardwareId, @RequestBody UpdateHardwareRequestModel hardwareAsset) {
		Map<String, String> response = new HashMap<>();
		if(hardwareService.updateHardwareAsset(hardwareId, hardwareAsset)) {
			response.put("message", "Hardware Asset Updated");	
			response.put("status", "true");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.put("message", "Asset already existing");
			response.put("status", "false");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}
	
	// Get All Software
	@GetMapping("all")
	public ResponseEntity<Map<String, Object>> getAllHardwareAssets(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {

		// Calling service function
		Page<HardwareAsset> pages = hardwareService.getAllHardwareAssets(page, size);
		
		// returning response data
		return responseData(pages, "getHardwareVo");

	}
	
	// Search hardware
	@GetMapping("search")
	public ResponseEntity<Map<String, Object>> searchHardwareAssets(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam String searchTerm) {
		// Calling service function
		Page<HardwareAsset> pages = hardwareService.searchHardwareItems(page, size, searchTerm);
		return responseData(pages, "getHardwareVo");
	}
	
	/* Getting all details of a particular software asset by its ID */
//	@Secured("ROLE_ADMIN")
	@GetMapping("find")
	public ResponseEntity<Map<String, Object>> getSoftwareAsset(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam int hardwareId) {
		Page<HardwareAsset> pages = hardwareService.getHardwareAsset(page, size, hardwareId);
		return responseData(pages, "getSoftwareModel");
	}

	// creating a get mapping that filters all the hardwares from the database by
	// hardware type, manufacturer and status
	@GetMapping("filter")
	public ResponseEntity<Map<String, Object>> filteredHardwares(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam("hardwareType") String hardwareType,
			@RequestParam("manufacturer") String manufacturer, @RequestParam("assignStatus") String status) {

		Page<HardwareAsset> hardwareListByFilter = hardwareService.searchAllHardwaresByAssetTypeAndManufacturingCompanyAndStatus(
				page, size, hardwareType, manufacturer, status);
		return responseData(hardwareListByFilter, "getHardwareVo");
	}
	
	
	/* updating the status of a software asset to "inactive" */
	@PutMapping("update/status")
	public ResponseEntity<Map<String, String>> updateStatus(@RequestBody List<Integer> ids) {
		
		Map<String, String> response = new HashMap<>();
		if(hardwareService.changeStatus(ids)) {
			response.put("message", "Status Updated to inactive");	
			response.put("status", "true");
			return new ResponseEntity<>(response, HttpStatus.OK);	
		}
		else {
			response.put("message", "Software Asset not found");	
			response.put("status", "false");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}
	
	//get all hardwares for user
	@GetMapping("user/viewAll/{pageNumber}/{pageSize}")
	public ResponseEntity<Map<String, Object>> view(@PathVariable Integer pageNumber, @PathVariable Integer pageSize) {
		Page<HardwareAsset> hardware = hardwareService.getHardwareAssetPagination(pageNumber, pageSize);
		List<HardwareAsset> hardwarelist = hardware.getContent();
		Map<String, Object> list = new HashMap<>();
		list.put("hardwareAvaliableList", hardwarelist);
		list.put("currentPage", hardware.getNumber());
		list.put("totalItem", hardware.getTotalElements());
		list.put("totalPages", hardware.getTotalPages());
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	//get the details of a single hardware asset for user
		@GetMapping("/id/{id}")
		public ResponseEntity<HardwareAsset> getById(@PathVariable("id") int id) {
			HardwareAsset hardware = hardwareService.getByhardwareId(id);
			return new ResponseEntity<>(hardware, HttpStatus.OK);
		}

		
		//to update the status of a software asset
		@PutMapping("/update/{id}/{status}")
		public void updateAsset(@PathVariable int id,@PathVariable String status){
			hardwareService.updateHardwareAsset(id, status);
		}
		
		//to search available hardware for user.
		@GetMapping("usersearch/{searchTerm}")
		public ResponseEntity<List<HardwareAsset>> getSoftwaresBySearch(@PathVariable("searchTerm") String searchTerm) {
			List<HardwareAsset> searchResult = hardwareService.getAssetBySearchTerm(searchTerm);
			if (searchResult != null) {
				return new ResponseEntity<>(searchResult, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

		}
		
	
	// Response function - Creating response data
	public ResponseEntity<Map<String, Object>> responseData(Page<HardwareAsset> pages, String formatVo) {
		if (!pages.isEmpty()) {
			List<HardwareAsset> hardwareItems = pages.getContent();
			Map<String, Object> response = new HashMap<>();
			if(formatVo.equals("getHardwareVo")) {
				TypeToken<List<HardwareVO>> outputData = new TypeToken<>() {			
				};
				List<HardwareVO> data = modelMapper.map(hardwareItems, outputData.getType());	//	Mapping List to list
				response.put("Hardwares", data);
			}
			else {
				TypeToken<List<HardwareAsset>> outputData = new TypeToken<>() {			
				};
				List<HardwareAsset> data = modelMapper.map(hardwareItems, outputData.getType());	//	Mapping List to list
				response.put("Hardwares", data);
			}
			response.put("currentPage", pages.getNumber());
			response.put("totalItems", pages.getTotalElements());
			response.put("totalPages", pages.getTotalPages());
			return new ResponseEntity<>(response, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.OK);
	}	

	

}
