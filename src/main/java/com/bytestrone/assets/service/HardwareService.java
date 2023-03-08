package com.bytestrone.assets.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.bytestrone.assets.model.HardwareAsset;
import com.bytestrone.assets.model.SoftwareAsset;
import com.bytestrone.assets.viewobjects.HardwareRequestModel;
import com.bytestrone.assets.viewobjects.UpdateHardwareRequestModel;

public interface HardwareService {
	boolean saveHardware(HardwareRequestModel software);
	
	boolean updateHardwareAsset(int hardwareId, UpdateHardwareRequestModel hardwareAsset);

	Page<HardwareAsset> getAllHardwareAssets(int page, int size);
	
	Page<HardwareAsset> searchHardwareItems(int page, int size, String searchTerm);
	
	Page<HardwareAsset> getHardwareAsset(int page, int size, int searchid);
	
	boolean changeStatus(List<Integer> ids);
	
	// getting all hardwares by filtering hardware type, manufacturer and status for admin
	Page<HardwareAsset> searchAllHardwaresByAssetTypeAndManufacturingCompanyAndStatus(int page, int size,
			String hardwareType, String manufacturer, String status);
	
	// getting all hardware assets for user
	Page<HardwareAsset> getHardwareAssetPagination(Integer pageNumber, Integer pageSize);

	HardwareAsset getByhardwareId(int id);
	
	//to update the status of a hardware asset
	void updateHardwareAsset(int id,String status);
	
	//to search available hardware for user
	List<HardwareAsset> getAssetBySearchTerm(String searchTerm);

}

