package com.bytestrone.assets.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bytestrone.assets.repository.HardwaretypeRepository;
import com.bytestrone.assets.repository.LicenseTypeRepository;
import com.bytestrone.assets.repository.ManufacturingCompanyRepository;
import com.bytestrone.assets.repository.OwnershipStatusRepository;
import com.bytestrone.assets.repository.SoftwareCategoryRepository;
import com.bytestrone.assets.service.OptionService;

@Service
public class OptionServiceImpl implements OptionService {
	
	@Autowired
	private LicenseTypeRepository licenseTypeRepository; 
	
	@Autowired
	private ManufacturingCompanyRepository manufacturingCompanyRepository; 
	
	@Autowired
	private SoftwareCategoryRepository softwareCategoryRepository;
	
	@Autowired
	private OwnershipStatusRepository ownershipStatusRepository;
	
	@Autowired
	private HardwaretypeRepository hardwaretypeRepository;

	@Override
	public List<String> getLicenseType() {
		return licenseTypeRepository.getAllLicenseType();
	}

	@Override
	public List<String> getSoftwareManufacturingCompany() {
		return manufacturingCompanyRepository.getAllSoftwareManufacturingCompany();
	}

	@Override
	public List<String> getSoftwareCategory() {
		return softwareCategoryRepository.getAllSoftwareCategory();
	}

	@Override
	public List<String> getHardwareManufacturingCompany() {
		return manufacturingCompanyRepository.getAllHardwareManufacturingCompany();
	}

	@Override
	public List<String> getOwnershipStatus() {
		return ownershipStatusRepository.getAllOwnershipStatus();
	}

	@Override
	public List<String> getHardwareType() {
		return hardwaretypeRepository.getAllHardwareType();
	}

}

