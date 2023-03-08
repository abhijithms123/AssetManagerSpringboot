package com.bytestrone.assets.serviceImpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bytestrone.assets.model.SoftwareAsset;
import com.bytestrone.assets.repository.SoftwareRepository;
import com.bytestrone.assets.service.SoftwareService;
import com.bytestrone.assets.viewobjects.SoftwareRequestModel;
import com.bytestrone.assets.viewobjects.UpdateSoftwareRequestModel;

@Service
public class SoftwareServiceImpl implements SoftwareService {
	
	@Autowired
	private SoftwareRepository softwareRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	public boolean saveSoftware(SoftwareRequestModel software) {
		if (softwareRepository.existsByAssetNumber(software.getAssetNumber())) {  //Checking if asset number already exists || else save data
			return false;
		} else {
			SoftwareAsset data = modelMapper.map(software, SoftwareAsset.class);	
			data.setInstalled(0);														// Setting status and installed field initially as unallocated and 0
			data.setStatus("Unallocated");
			data.setAssetType("Software");
			softwareRepository.save(data);
			return true;
		}

	}
	
	/* Find a software asset 
	 * provided by a software ID
	 */
	public Page<SoftwareAsset> getSoftwareAsset(int page, int size, int softwareId) {
		Pageable paging = PageRequest.of(page, size);
		Page<SoftwareAsset> pages = softwareRepository.findById(softwareId, paging);			// getting items by searching using softwareId
		if (null != pages) {
			return pages;
		}
		return null;
	}
	
	/* Changing the fields value of a particular software asset
	 * changing the status field to allocated if value of installed changed to more than 0 value
	 * provided ID of software
	 */
	public boolean updateAsset(int id, UpdateSoftwareRequestModel assetBody) {
		SoftwareAsset softwareAsset = softwareRepository.findById(id);
		if (null != softwareAsset) {
			softwareAsset.setSubVersion(assetBody.getSubVersion());
			softwareAsset.setSoftwareCategory(assetBody.getSoftwareCategory());
			softwareAsset.setLicenseType(assetBody.getLicenceType());
			softwareAsset.setSoftwareName(assetBody.getSoftwareName());
			softwareAsset.setPurchased(assetBody.getPurchased());
			softwareAsset.setInstalled(assetBody.getInstalled());
			if (assetBody.getInstalled() > 0) {
				softwareAsset.setStatus("Allocated");
			}
			softwareRepository.save(softwareAsset);	
			return true;
		}
		else {
			return false;
		}
	}
	
	/* Changing status field of a software asset to "inactive"
	 * provided the ID of software asset
	 */
	public boolean changeStatus(List<Integer> ids) {
		for (int i : ids) {
			SoftwareAsset asset = softwareRepository.findById(i);
			if (null != asset) {
				asset.setStatus("inactive");
				softwareRepository.save(asset);
			}			
		}
		return true;	
	
		}
	

	@Override
	public Page<SoftwareAsset> getAllSoftwareAssets(int page, int size) {

		Pageable paging = PageRequest.of(page, size);
		Page<SoftwareAsset> pages = softwareRepository.findByStatusOrStatus("Unallocated", "Allocated", paging);   // Getting all active(Unallocated and allocated) items
		if (null != pages) {
			return pages;
		}
		return null;
	}

	@Override
	public Page<SoftwareAsset> searchSoftwareItems(int page, int size, String searchTerm) {
		Pageable paging = PageRequest.of(page, size);
		Page<SoftwareAsset> pages = softwareRepository.searchSoftware(searchTerm, paging);			// getting items by searching using searchTerm
		if (null != pages) {
			return pages;
		}
		return null;

	}
	
    //method to get all available softwares for users
	@Override
	public Page<SoftwareAsset> getSoftwareAssetPagination(Integer pageNumber, Integer pageSize) {
		Pageable pageable= PageRequest.of(pageNumber,pageSize);
		
		return  softwareRepository.findByStatus("Unallocated", pageable);
	}

	//find the details of a single software asset for user to make an asset request
	@Override
	public SoftwareAsset getBySoftwareId(int id) {
		return softwareRepository.findById(id);
	}
    
	//to update the status of a software Asset
	@Override
	public void updateSoftwareAsset(int id,String status) {
		SoftwareAsset software = softwareRepository.findById(id);
	    software.setPurchased(software.getPurchased() - 1);
	    System.out.println(software.getPurchased());
	    softwareRepository.save(software);
	    if(software.getPurchased() <=0) {
	       software.setStatus(status);
	       softwareRepository.save(software);
	    }
		
		
		
	}
    
	
	//to search available software for user
	@Override
	public List<SoftwareAsset> getAssetBySearchTerm(String searchTerm) {
		return softwareRepository.findSoftwareBySearchTerm(searchTerm);
	}
    
	//to filter available softwares for user based on category and manufacturer.
	@Override
	public Page<SoftwareAsset> filterSoftwareByCategoryAndManufacturer(int page, int size, String softwareCategory,
			String manufacturer, String status) {
		Pageable pageable = PageRequest.of(page, size);
		return softwareRepository.searchAllSoftwaresBySoftwareCategoryOrManufacturingCompanyAndStatus(pageable, softwareCategory, manufacturer, status);
		
	}

}
