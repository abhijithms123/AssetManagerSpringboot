package com.bytestrone.assets.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bytestrone.assets.service.SoftwareDashboardService;
import com.bytestrone.assets.viewobjectDashboard.AboutToExpireVO;
import com.bytestrone.assets.viewobjectDashboard.ResponseGraphVO;
import com.bytestrone.assets.viewobjectDashboard.ResponseListVO;
import com.bytestrone.assets.viewobjectDashboard.ResponseWidgetVO;


@RestController
@RequestMapping("/softwareDashboard")
@CrossOrigin("http://localhost:4200")
public class SoftwareDashboardController {
	
	@Autowired
	private SoftwareDashboardService softwareService;
	
	@GetMapping("graphData")
	public ResponseEntity<ResponseGraphVO> getGraphValues() {
		ResponseGraphVO responseGraphVO = softwareService.getGraphData();
		return new ResponseEntity<>(responseGraphVO, HttpStatus.OK);
	}

	
	@GetMapping("asset-status-widget")
	public ResponseEntity<List<ResponseWidgetVO>> getData() {

		List<ResponseWidgetVO> responseWidgetResponse = softwareService.getWidgetData();
		return new ResponseEntity<>(responseWidgetResponse, HttpStatus.OK);
	}

	@GetMapping("asset/{softwareCategory}/{status}")
	public ResponseEntity<List<ResponseListVO>> getSofwareCategory(
			@PathVariable(name = "softwareCategory") String softwareCategory,
			@PathVariable(name = "status") String status) {

		return new ResponseEntity<>(softwareService.getBySoftwareCategory(softwareCategory, status), HttpStatus.OK);
	}
	
	@GetMapping("assetId/{assetId}")
	public ResponseEntity<List<ResponseListVO>> getAssetData(@PathVariable(name = "assetId") Integer assetId){
		return new ResponseEntity<>(softwareService.getAsset(assetId),HttpStatus.OK);
	}
	
	@GetMapping("aboutToExpire")
	public ResponseEntity<List<AboutToExpireVO>> getAboutToExpireData(){
		return new ResponseEntity<>(softwareService.getAboutToExpireAssets(), HttpStatus.OK);
	}

}
