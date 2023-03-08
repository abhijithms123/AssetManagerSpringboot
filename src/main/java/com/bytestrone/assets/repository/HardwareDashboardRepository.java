package com.bytestrone.assets.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bytestrone.assets.model.HardwareAsset;

@Repository
public interface HardwareDashboardRepository extends JpaRepository<HardwareAsset, String>{
  
	Page<HardwareAsset> findByHardwareTypeAndStatus(String hardwareType, String status,Pageable pageable);

	
	@Query(value="with ac as (SELECT hardware_type, COUNT(status) as assign_count FROM hardware WHERE status ='Allocated' GROUP BY hardware_type), uac as (SELECT hardware_type, COUNT(status) as unassign_count FROM hardware WHERE status ='Unallocated' GROUP BY hardware_type)SELECT ac.hardware_type, assign_count,unassign_count FROM ac FULL OUTER JOIN uac ON ac.hardware_type=uac.hardware_type;",nativeQuery = true)
	List<Object[]> getCountDistinctHardwareByAssetType();
	
//	@Query(value = "SELECT action,COUNT(action) as Count from requests r where r.type = 'software' AND r.user_id = :userId group by action", nativeQuery = true)
//	List<Object[]> countSoftwareAction(@Param("userId") String userId);

}