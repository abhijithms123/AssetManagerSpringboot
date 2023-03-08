package com.bytestrone.assets.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.bytestrone.assets.model.SoftwareRequest;

@Service
public interface SoftwareRequestService {
	
	//to save a software asset request made by a user to the database
	void saveSoftwareRequest(SoftwareRequest reqObj);
	
	//to get the list of software requests made by a user based on status
	public Page<SoftwareRequest> getSoftwareRequests(String requestStatus,String requestedBy,Integer pageNumber, Integer pageSize);
	
	//to get the list of requests for admin based on request status.
	public Page<SoftwareRequest> getRequestByStatus(String requestStatus,Integer pageNumber, Integer pageSize);
	
	//to update the status of a software asset request.
	void updateRequestStatus(String status,String requestId,String reason);
	
	//to get the count of requests made by user based on request status
	public List<Object[]> getHardwareCountByStatus(String userName);

}
