package com.bytestrone.assets.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bytestrone.assets.model.HardwareAsset;
import com.bytestrone.assets.model.SoftwareAsset;

@Repository
public interface SoftwareRepository extends JpaRepository<SoftwareAsset, Integer> {
	boolean existsByAssetNumber(String assetNumber);

	Page<SoftwareAsset> findByStatusOrStatus(String status1, String status2, Pageable pageable);
	
	Page<SoftwareAsset> findById(int id, Pageable pageable);
	
	SoftwareAsset findById(int id);

	@Query("SELECT h FROM SoftwareAsset h WHERE h.softwareCategory LIKE CONCAT('%',?1,'%')"
			+ "OR h.softwareName LIKE CONCAT('%',?1,'%')" + "OR h.manufacturingCompany LIKE CONCAT('%',?1,'%')")
	Page<SoftwareAsset> searchSoftware(String searchTerm, Pageable pageable);
	
	Page<SoftwareAsset> findByStatus(String status,Pageable pageable);
	
	//to search available software for user
	@Query("SELECT a FROM SoftwareAsset a WHERE (a.softwareCategory LIKE CONCAT('%',:searchTerm,'%') "
			+ "OR a.softwareName LIKE CONCAT('%',:searchTerm,'%')"
			+ "OR a.manufacturingCompany LIKE CONCAT('%',:searchTerm,'%')) "
			+ "AND a.status = 'Unallocated'")
	List<SoftwareAsset> findSoftwareBySearchTerm(String searchTerm);
	
	// getting all hardwares by filtering hardware type, manufacturer and status
	Page<SoftwareAsset> searchAllSoftwaresBySoftwareCategoryOrManufacturingCompanyAndStatus(Pageable pageable, String hardwareType,
			String manufacturer, String status);
	
	
}

