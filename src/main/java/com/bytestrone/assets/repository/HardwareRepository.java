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
public interface HardwareRepository extends JpaRepository<HardwareAsset, Integer> {
	boolean existsByAssetNumber(String assetNumber);
	
	Page<HardwareAsset> findByStatusOrStatus(String status1, String status2, Pageable pageable);
	
	Page<HardwareAsset> findById(int id, Pageable pageable);
	
	HardwareAsset findById(int id);
	
	@Query("SELECT h FROM HardwareAsset h WHERE h.assetNumber LIKE CONCAT('%',:searchTerm,'%') "
			+ "OR h.assetType LIKE CONCAT('%',:searchTerm,'%')"
			+ "OR h.manufacturingCompany LIKE CONCAT('%',:searchTerm,'%')"
			+ "OR h.serialNumber LIKE CONCAT('%',:searchTerm,'%')" + "OR h.modelNumber LIKE CONCAT('%',:searchTerm,'%')"
			+ "OR h.modelName LIKE CONCAT('%',:searchTerm,'%')"
			+ "OR h.expressServiceCode LIKE CONCAT('%',:searchTerm,'%')"
			+ "OR h.batchNumber LIKE CONCAT('%',:searchTerm,'%')"
			+ "OR h.ownershipStatus LIKE CONCAT('%',:searchTerm,'%')" + "OR h.code LIKE CONCAT('%',:searchTerm,'%')"
			+ "OR h.configuration LIKE CONCAT('%',:searchTerm,'%')" + "OR h.status LIKE CONCAT('%',:searchTerm,'%')"
			+ "OR h.enterBy LIKE CONCAT('%',:searchTerm,'%')")
	Page<HardwareAsset> searchHardware(String searchTerm, Pageable pageable);
	
	// getting all hardwares by filtering hardware type, manufacturer and status
	Page<HardwareAsset> searchAllHardwaresByHardwareTypeAndManufacturingCompanyAndStatus(Pageable pageable, String hardwareType,
			String manufacturer, String status);
	
	//get all unallocated hardware assets for the user
	Page<HardwareAsset> findByStatus(String status,Pageable pageable);
	
	//search all available software for user
	@Query("SELECT a FROM HardwareAsset a WHERE (a.assetType LIKE CONCAT('%',:searchTerm,'%') "
			+ "OR a.modelName LIKE CONCAT('%',:searchTerm,'%')"
			+ "OR a.manufacturingCompany LIKE CONCAT('%',:searchTerm,'%')) "
			+ "AND a.status = 'Unallocated'")
	List<HardwareAsset> findHardwareBySearchTerm(String searchTerm);

}

