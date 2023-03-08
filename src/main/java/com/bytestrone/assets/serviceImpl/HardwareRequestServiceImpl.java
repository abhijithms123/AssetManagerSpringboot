package com.bytestrone.assets.serviceImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bytestrone.assets.model.HardwareRequest;
import com.bytestrone.assets.repository.HardwareRequestRepo;
import com.bytestrone.assets.service.HardwareRequestService;

@Service
public class HardwareRequestServiceImpl implements HardwareRequestService {

	@Autowired
	private HardwareRequestRepo requestRepo;
	
	//to save a hardware request made by a single user.
	@Override
	public void saveHardwareRequest(HardwareRequest reqObj) {
		requestRepo.save(reqObj);
		
	}
    
	//to get the list of hardware requests made by a single user based on request status.
	@Override
	public Page<HardwareRequest> findByRequestStatusAndRequestedBy(String requestStatus, String requestedBy,
			Integer pageNumber, Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNumber,pageSize);
		return requestRepo.findByRequestStatusAndRequestedBy(requestStatus, requestedBy, pageable);
	}
    
	
	//to update the status of a hardware request.
	@Override
	@Transactional
	public void updateRequestStatus(String status, String requestId, String reason) {
		try{HardwareRequest request = requestRepo.findByRequestId(requestId);
		request.setRequestStatus(status);
		request.setReason(reason);
		requestRepo.save(request);}
		catch(NullPointerException ex){
			System.out.println(ex.getMessage() + "request does not exist.");
		}
	}
    
	//to get the count of hardware requests made by a user based on request status
	@Override
	public List<Object[]> getHardwareCountByStatus(String userName) {
		return requestRepo.countHardwareByStatus(userName);
	}
    
	//to get the list of hardware requests based on status
	@Override
	public Page<HardwareRequest> getRequestByStatus(String requestStatus, Integer pageNumber, Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNumber,pageSize);
		return requestRepo.findByRequestStatus(requestStatus, pageable);
	}

	

}
