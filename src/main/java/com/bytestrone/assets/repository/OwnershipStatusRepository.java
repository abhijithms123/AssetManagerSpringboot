package com.bytestrone.assets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bytestrone.assets.options.OwnershipStatus;

@Repository
public interface OwnershipStatusRepository extends JpaRepository<OwnershipStatus, Integer>{
	@Query("SELECT o.ownershipStatus FROM OwnershipStatus o ")
	public List<String> getAllOwnershipStatus();
}
