package com.bytestrone.assets.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bytestrone.assets.model.HardwareRequest;
import com.bytestrone.assets.model.SoftwareRequest;

@Repository
public interface HardwareRequestRepo extends JpaRepository<HardwareRequest, String> {
    
	@Query(value= "SELECT request_status,COUNT(request_status) as Count from hardware_request r where r.requested_by = :userName group by request_status",nativeQuery = true)
	List<Object[]> countHardwareByStatus(@Param("userName") String userName);

	//to display list of hardware requests for a single user
	Page<HardwareRequest> findByRequestStatusAndRequestedBy(String requestStatus,String requestedBy,Pageable pageable);
	
	//to display list of hardware requests for admin based on request status.
	Page<HardwareRequest> findByRequestStatus(String requestStatus,Pageable pageable);
     
	//to get a specific request based on request id.
	HardwareRequest findByRequestId(String requestId);
	
}
