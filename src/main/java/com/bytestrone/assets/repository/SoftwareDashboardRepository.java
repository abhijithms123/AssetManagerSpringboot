package com.bytestrone.assets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bytestrone.assets.model.Asset;
import com.bytestrone.assets.model.SoftwareAsset;

@Repository
public interface SoftwareDashboardRepository extends JpaRepository<SoftwareAsset, Integer>{
	
	SoftwareAsset findById(int id);
	
	@Query(value = "SELECT SUM(purchased) FROM software", nativeQuery = true)
	int selectTotalPurchased();

	@Query(value = "SELECT SUM(installed) FROM software", nativeQuery = true)
	int selectTotalInstalled();

	@Query(value = "SELECT SUM(purchased-installed) FROM software", nativeQuery = true)
	int selectTotalAvailable();

	@Query(value = "SELECT  software_category,SUM(purchased) AS purchased, SUM(installed) AS installed, SUM(purchased-installed) AS available FROM software GROUP BY software_category", nativeQuery = true)
	List<Object[]> findBySoftwareCategory();
	
	List<Asset> findSoftwareByStatus(String status);
	
	List<Asset> findSoftwareAssetBySoftwareCategoryAndStatus(String softwareCategory,String status);
	
	@Query(value = "SELECT asset_id, software_name,license_expiry_date, (license_expiry_date - CURRENT_DATE) AS expires_in FROM software WHERE license_expiry_date BETWEEN current_date and current_date+30 ORDER BY license_expiry_date" , nativeQuery = true)
	List<Object[]> findAboutToExpireAssets();

}

