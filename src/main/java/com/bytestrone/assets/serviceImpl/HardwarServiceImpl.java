package com.bytestrone.assets.serviceImpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bytestrone.assets.model.HardwareAsset;
import com.bytestrone.assets.repository.HardwareRepository;
import com.bytestrone.assets.service.HardwareService;
import com.bytestrone.assets.viewobjects.HardwareRequestModel;
import com.bytestrone.assets.viewobjects.UpdateHardwareRequestModel;

@Service
public class HardwarServiceImpl implements HardwareService {
	
	@Autowired
	private HardwareRepository hardwareRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public boolean saveHardware(HardwareRequestModel hardware) {
		if (hardwareRepository.existsByAssetNumber(hardware.getAssetNumber())) {  //Checking if asset number already exists || else save data
			return false;
		} else {
			HardwareAsset data = modelMapper.map(hardware, HardwareAsset.class);															// Setting status and installed field initially as unallocated and 0
			data.setStatus("Unallocated");
			data.setAssetType("Hardware");
			hardwareRepository.save(data);
			return true;
		}

	}
	
	/* Changing the fields value of a particular software asset
	 * changing the status field to allocated if value of installed changed to more than 0 value
	 * provided ID of software
	 */
	public boolean updateHardwareAsset(int id, UpdateHardwareRequestModel assetBody) {
		HardwareAsset hardwareAsset = hardwareRepository.findById(id);
		if (null != hardwareAsset) {
			hardwareAsset.setExpressServiceCode(assetBody.getExpressServiceCode());
			hardwareAsset.setBatchNumber(assetBody.getBatchNumber());
			hardwareAsset.setOwnershipStatus(assetBody.getOwnershipStatus());
			hardwareAsset.setAssetNumber(assetBody.getAssetNumber());
			hardwareAsset.setCode(assetBody.getCode());
			hardwareAsset.setConfiguration(assetBody.getConfiguration());
			hardwareRepository.save(hardwareAsset);	
			return true;
		}
		else {
			return false;
		}
	}
	
	public Page<HardwareAsset> getAllHardwareAssets(int page, int size) {

		Pageable paging = PageRequest.of(page, size);
		Page<HardwareAsset> pages = hardwareRepository.findByStatusOrStatus("Unallocated", "Allocated", paging);  
		if (null != pages) {
			return pages;
		}
		return null;
	}
	
	@Override
	public Page<HardwareAsset> searchHardwareItems(int page, int size, String searchTerm) {
		Pageable paging = PageRequest.of(page, size);
		Page<HardwareAsset> pages = hardwareRepository.searchHardware(searchTerm, paging);			// getting items by searching using searchTerm
		if (null != pages) {
			return pages;
		}
		return null;

	}
	
	// implementation of method in service to filter all hardwares by hardware type,
	// manufacturer and status
	@Override
	public Page<HardwareAsset> searchAllHardwaresByAssetTypeAndManufacturingCompanyAndStatus(int page, int size,
			String hardwareType, String manufacturer, String status) {

		Pageable pageable = PageRequest.of(page, size);
		return hardwareRepository.searchAllHardwaresByHardwareTypeAndManufacturingCompanyAndStatus(pageable, hardwareType,
				manufacturer, status);
	}

	
	@Override
	public Page<HardwareAsset> getHardwareAsset(int page, int size, int searchid) {
		Pageable paging = PageRequest.of(page, size);
		Page<HardwareAsset> pages = hardwareRepository.findById(searchid, paging);			// getting items by searching using softwareId
		if (null != pages) {
			return pages;
		}
		return null;
	}

	@Override
	public boolean changeStatus(List<Integer> ids) {
		for (int i : ids) {
			HardwareAsset asset = hardwareRepository.findById(i);
			if (null != asset) {
				asset.setStatus("inactive");
				hardwareRepository.save(asset);
			}
			else {
				return false;
			}
		}
		return true;	
	}
	
    // get all available hardware for the user
	@Override
	public Page<HardwareAsset> getHardwareAssetPagination(Integer pageNumber, Integer pageSize) {
	Pageable pageable= PageRequest.of(pageNumber,pageSize);
		
		return  hardwareRepository.findByStatus("Unallocated", pageable);
	}
    
	//to get a single hardware asset for user based on asset id
	@Override
	public HardwareAsset getByhardwareId(int id) {
		return hardwareRepository.findById(id);
	}
    
	//to update the status of a hardware asset
	@Override
	public void updateHardwareAsset(int id, String status) {
		HardwareAsset hardware = hardwareRepository.findById(id);
		hardware.setStatus(status);
		hardwareRepository.save(hardware);
		
	}
	
	//to search available hardware for user

	@Override
	public List<HardwareAsset> getAssetBySearchTerm(String searchTerm) {
		return hardwareRepository.findHardwareBySearchTerm(searchTerm);
	}
}
