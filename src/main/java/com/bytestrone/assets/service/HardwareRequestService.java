package com.bytestrone.assets.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.bytestrone.assets.model.HardwareRequest;

@Service
public interface HardwareRequestService {
	
	//to save a hardware request made by the user
	public void saveHardwareRequest(HardwareRequest reqObj);
	
	// to display all the hardware requests made by a single user based on request status.
	public Page<HardwareRequest> findByRequestStatusAndRequestedBy(String requestStatus,String requestedBy,Integer pageNumber, Integer pageSize);
	
	//to get the list of requests for admin based on request status.
	public Page<HardwareRequest> getRequestByStatus(String requestStatus,Integer pageNumber, Integer pageSize);
    
	//update the status of a hardware request
	public void updateRequestStatus(String status, String requsetId, String reason);
   
	
	//to get the count of requests made by user based on request status
	public List<Object[]> getHardwareCountByStatus(String userName);


}
