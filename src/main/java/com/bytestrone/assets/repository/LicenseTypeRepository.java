package com.bytestrone.assets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bytestrone.assets.options.LicenseType;
@Repository
public interface LicenseTypeRepository extends JpaRepository<LicenseType, Integer> {
	@Query("SELECT l.licenseType FROM LicenseType l ")
	public List<String> getAllLicenseType();
}
