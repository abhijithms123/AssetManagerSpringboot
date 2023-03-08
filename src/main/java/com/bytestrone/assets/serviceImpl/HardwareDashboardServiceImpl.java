package com.bytestrone.assets.serviceImpl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bytestrone.assets.model.HardwareAsset;
import com.bytestrone.assets.repository.HardwareDashboardRepository;
import com.bytestrone.assets.service.HardwareDashboardService;
import com.bytestrone.assets.viewobjectDashboard.CountSummaryVO;

@Service
public class HardwareDashboardServiceImpl implements HardwareDashboardService {
	
	@Autowired
	private HardwareDashboardRepository hardwareRepository;
	
//	returning data of requested Asset type with requested status
	public Page<HardwareAsset> fetchAssetByHardware(String assetType, String status, int page, int size) {
		Pageable paging = PageRequest.of(page, size);
		return hardwareRepository.findByHardwareTypeAndStatus(assetType, status, paging);
	}
//function for getting assigned and unassigned counts for corresponding asset type
	public List<CountSummaryVO> getCountsByHardwareType() {
		int assignCount;
		int unassignCount;
		
		List<CountSummaryVO> response = new ArrayList<>();
		List<Object[]> items = hardwareRepository.getCountDistinctHardwareByAssetType();
		Iterator<Object[]> itr = items.iterator();
		while (itr.hasNext()) {
			Object[] item = itr.next();
			String type = (String) item[0];	
			BigInteger assignedCount = (BigInteger) item[1];	
			BigInteger unassignedCount = (BigInteger) item[2];
			
			assignCount=setCount(assignedCount);
			unassignCount=setCount(unassignedCount);
			
			CountSummaryVO newResp = new CountSummaryVO(type, assignCount, unassignCount);
			response.add(newResp);
		}
		return response;
	}
	// to set count value	
	public int setCount(BigInteger count) {
		if (null != count) {
			return count.intValue();
		} else {
			return 0;
		}
	}

}
