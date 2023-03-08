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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bytestrone.assets.model.HardwareAsset;
import com.bytestrone.assets.service.HardwareDashboardService;
import com.bytestrone.assets.viewobjectDashboard.CountSummaryVO;
import com.bytestrone.assets.viewobjectDashboard.HardwareListVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/hardwareDashboard")
@CrossOrigin(origins = "http://localhost:4200")
public class HardwareDashboardController {

	@Autowired
	private HardwareDashboardService hardwareService;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping("/counts")
	public ResponseEntity<List<CountSummaryVO>> getHardware() {
		return ResponseEntity.ok(hardwareService.getCountsByHardwareType());
	}

	@GetMapping("/list/{assetType}/{assignStatus}/{pageNumber}/{pageSize}")
	public ResponseEntity<Map<String, Object>> getHardwareByAssetType(@PathVariable("assetType") String assetType,
			@PathVariable("assignStatus") String assignStatus, @PathVariable("pageNumber") int pageNumber,
			@PathVariable("pageSize") int pageSize) {
		Page<HardwareAsset> listHardware = hardwareService.fetchAssetByHardware(assetType, assignStatus, pageNumber,
				pageSize);
		if (null != listHardware) {
			List<HardwareAsset> hardwareItems = listHardware.getContent();

			TypeToken<List<HardwareListVO>> reponseList = new TypeToken<>() {
			};
			List<HardwareListVO> data = modelMapper.map(hardwareItems, reponseList.getType());

			Map<String, Object> response = new HashMap<>();
			response.put("Hardwares", data);
			response.put("currentPage", listHardware.getNumber());
			response.put("totalItems", listHardware.getTotalElements());
			response.put("totalPages", listHardware.getTotalPages());
			return new ResponseEntity<>(response, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
