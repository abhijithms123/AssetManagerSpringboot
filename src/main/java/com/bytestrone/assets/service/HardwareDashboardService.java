package com.bytestrone.assets.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.bytestrone.assets.model.HardwareAsset;
import com.bytestrone.assets.viewobjectDashboard.CountSummaryVO;

public interface HardwareDashboardService {

	public List<CountSummaryVO> getCountsByHardwareType();

	public Page<HardwareAsset> fetchAssetByHardware(String assetType, String assignStatus, int pageNumber,
			int pageSize);

}
