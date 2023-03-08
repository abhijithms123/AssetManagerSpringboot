package com.bytestrone.assets.service;

import java.util.List;

import com.bytestrone.assets.options.LicenseType;
import com.bytestrone.assets.options.ManufacturingCompany;
import com.bytestrone.assets.options.SoftwareCategory;

public interface OptionService {
	public List<String> getLicenseType();
	public List<String> getSoftwareManufacturingCompany();
	public List<String> getSoftwareCategory(); 
	public List<String> getHardwareManufacturingCompany();
	public List<String> getOwnershipStatus();
	public List<String> getHardwareType();
}
