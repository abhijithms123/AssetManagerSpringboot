package com.bytestrone.assets.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.bytestrone.assets.model.SoftwareAsset;
import com.bytestrone.assets.viewobjects.SoftwareRequestModel;
import com.bytestrone.assets.viewobjects.UpdateSoftwareRequestModel;

public interface SoftwareService {
	boolean saveSoftware(SoftwareRequestModel software);
	
	boolean updateAsset(int softwareId, UpdateSoftwareRequestModel softwareAsset);

	Page<SoftwareAsset> getAllSoftwareAssets(int page, int size);

	Page<SoftwareAsset> searchSoftwareItems(int page, int size, String searchTerm);
	
	Page<SoftwareAsset> getSoftwareAsset(int page, int size, int searchid);
	
	boolean changeStatus(List<Integer> id);
    
	//for user list of softwares
	Page<SoftwareAsset> getSoftwareAssetPagination(Integer pageNumber, Integer pageSize);

	//for details of a single software asset
	SoftwareAsset getBySoftwareId(int id);
	
	void updateSoftwareAsset(int id,String status);
	
	//to search available software list for user
	
	List<SoftwareAsset> getAssetBySearchTerm(String searchTerm);
	
	//filtering softwares based on softwareCategory and manufacturer
	
	Page<SoftwareAsset> filterSoftwareByCategoryAndManufacturer(int page, int size, String softwareCategory, String manufacturer, String status );
    
}

