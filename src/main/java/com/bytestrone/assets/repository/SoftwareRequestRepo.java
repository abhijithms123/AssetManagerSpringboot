package com.bytestrone.assets.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bytestrone.assets.model.SoftwareRequest;

@Repository
public interface SoftwareRequestRepo extends JpaRepository<SoftwareRequest, String> {
	
	@Query(value= "SELECT request_status,COUNT(request_status) as Count from software_request r where r.requested_by = :userName group by request_status",nativeQuery = true)
	List<Object[]> countHardwareByStatus(@Param("userName") String userName);
	
	//to display list of software requests for a single user.
	Page<SoftwareRequest> findByRequestStatusAndRequestedBy(String requestStatus,String requestedBy,Pageable pageable);
	
	//to display a list software requests for admin based on status.
	Page<SoftwareRequest> findByRequestStatus(String requestStatus,Pageable pageable);
	
	//to get a specific request based on request id.
	SoftwareRequest findByRequestId(String requestId);

}
