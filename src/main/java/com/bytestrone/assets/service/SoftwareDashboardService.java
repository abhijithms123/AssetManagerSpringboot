package com.bytestrone.assets.service;

import java.util.List;

import com.bytestrone.assets.viewobjectDashboard.AboutToExpireVO;
import com.bytestrone.assets.viewobjectDashboard.ResponseGraphVO;
import com.bytestrone.assets.viewobjectDashboard.ResponseListVO;
import com.bytestrone.assets.viewobjectDashboard.ResponseWidgetVO;

public interface SoftwareDashboardService {
	
	public List<ResponseListVO> getAsset(int id);

	public ResponseGraphVO getGraphData();

	public List<ResponseWidgetVO> getWidgetData();

	public List<ResponseListVO> getBySoftwareCategory(String softwareCategory, String status);

	public List<AboutToExpireVO> getAboutToExpireAssets();

}
