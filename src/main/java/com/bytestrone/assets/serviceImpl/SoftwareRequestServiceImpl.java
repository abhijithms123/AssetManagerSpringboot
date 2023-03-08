package com.bytestrone.assets.serviceImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bytestrone.assets.model.HardwareRequest;
import com.bytestrone.assets.model.SoftwareRequest;
import com.bytestrone.assets.repository.SoftwareRequestRepo;
import com.bytestrone.assets.service.SoftwareRequestService;

@Service
public class SoftwareRequestServiceImpl implements SoftwareRequestService {
	
	@Autowired
	private SoftwareRequestRepo requestRepo;
    
	//to save a software asset request made by the user.
	@Override
	public void saveSoftwareRequest(SoftwareRequest reqObj) {
		requestRepo.save(reqObj);	
	}
    
	//to get all the software asset requests made by the user.
	@Override
	public Page<SoftwareRequest> getSoftwareRequests(String requestStatus, String requestedBy, Integer pageNumber,
			Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNumber,pageSize);
		return requestRepo.findByRequestStatusAndRequestedBy(requestStatus, requestedBy, pageable);
	}
    
	//to update the request status of a software request.
	@Override
	@Transactional
	public void updateRequestStatus(String status,String requestId,String reason) {
		SoftwareRequest request = requestRepo.findByRequestId(requestId);
		request.setRequestStatus(status);
		request.setReason(reason);
		requestRepo.save(request);
	}
	
	//to get the count of software requests made by a user based on request status
		@Override
		public List<Object[]> getHardwareCountByStatus(String userName) {
			return requestRepo.countHardwareByStatus(userName);
		}
       
		
		//to get the list of software requests based on status.
		@Override
		public Page<SoftwareRequest> getRequestByStatus(String requestStatus, Integer pageNumber, Integer pageSize) {
			Pageable pageable = PageRequest.of(pageNumber,pageSize);
			return requestRepo.findByRequestStatus(requestStatus, pageable);
		}
		
	}


