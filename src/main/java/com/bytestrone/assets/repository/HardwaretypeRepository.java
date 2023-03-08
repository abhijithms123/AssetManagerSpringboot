package com.bytestrone.assets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bytestrone.assets.options.HardwareType;

@Repository
public interface HardwaretypeRepository extends JpaRepository<HardwareType, Integer> {
	@Query("SELECT h.hardwaretype FROM HardwareType h ")
	public List<String> getAllHardwareType();
}
