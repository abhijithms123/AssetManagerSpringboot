package com.bytestrone.assets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bytestrone.assets.options.ManufacturingCompany;
@Repository
public interface ManufacturingCompanyRepository extends JpaRepository<ManufacturingCompany, Integer> {
	@Query("SELECT m.manufacturingCompany FROM ManufacturingCompany m where m.assetType = 'software'")
	public List<String> getAllSoftwareManufacturingCompany();
	
	@Query("SELECT m.manufacturingCompany FROM ManufacturingCompany m where m.assetType = 'hardware'")
	public List<String> getAllHardwareManufacturingCompany();
}
