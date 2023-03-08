package com.bytestrone.assets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bytestrone.assets.options.SoftwareCategory;

@Repository
public interface SoftwareCategoryRepository extends JpaRepository<SoftwareCategory, Integer> {
	@Query("SELECT s.softwareCategory FROM SoftwareCategory s ")
	public List<String> getAllSoftwareCategory();
}
