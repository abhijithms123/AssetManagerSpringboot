package com.bytestrone.assets.serviceImpl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bytestrone.assets.converter.AssetConverter;
import com.bytestrone.assets.model.Asset;
import com.bytestrone.assets.repository.SoftwareDashboardRepository;
import com.bytestrone.assets.service.SoftwareDashboardService;
import com.bytestrone.assets.viewobjectDashboard.AboutToExpireVO;
import com.bytestrone.assets.viewobjectDashboard.ResponseGraphVO;
import com.bytestrone.assets.viewobjectDashboard.ResponseListVO;
import com.bytestrone.assets.viewobjectDashboard.ResponseWidgetVO;

@Service
public class SoftwareServiceDashboardImpl implements SoftwareDashboardService {
	
	@Autowired
	private SoftwareDashboardRepository softwareRepository;
	
	@Autowired
	private AssetConverter assetConverter;

	@Override
	public List<ResponseListVO> getAsset(int id) {
		List<ResponseListVO> responseListVO = new ArrayList<>();
		responseListVO.add(assetConverter.convertAssetModelToResponseListVo(softwareRepository.findById(id)));
		return responseListVO;
	}
	
	private int getTotalSoftwarePurchased() {
		return softwareRepository.selectTotalPurchased();
	}


	private int getTotalSoftwareInstalled() {
		return softwareRepository.selectTotalInstalled();
	}

	private int getTotalSoftwareAvailable() {
		return softwareRepository.selectTotalAvailable();
	}

	@Override
	public ResponseGraphVO getGraphData() {
		ResponseGraphVO responseGraphVO = new ResponseGraphVO();
		responseGraphVO.setTotalPurchased(getTotalSoftwarePurchased());
		responseGraphVO.setTotalInstalled(getTotalSoftwareInstalled());
		responseGraphVO.setTotalAvailable(getTotalSoftwareAvailable());
		return responseGraphVO;
	}

	@Override
	public List<ResponseWidgetVO> getWidgetData() {
		List<ResponseWidgetVO> responseWidgetVO = new ArrayList<>();
		List<Object[]> resultList = softwareRepository.findBySoftwareCategory();
		Iterator<Object[]> iteratorResult = resultList.iterator();
		while (iteratorResult.hasNext()) {
			Object[] item = iteratorResult.next();
			ResponseWidgetVO responseWidgetVoObject = new ResponseWidgetVO((String) item[0], (BigInteger) item[1],
					(BigInteger) item[2], (BigInteger) item[3]);
			responseWidgetVO.add(responseWidgetVoObject);
		}
		return responseWidgetVO;
	}

	@Override
	public List<ResponseListVO> getBySoftwareCategory(String softwareCategory, String status) {
		List<Asset> asset = null;
		if (softwareCategory.equals("all")) {
			asset = softwareRepository.findSoftwareByStatus(status);
		} else {
			asset = softwareRepository.findSoftwareAssetBySoftwareCategoryAndStatus(softwareCategory, status);
		}
		List<ResponseListVO> responseListVO = new ArrayList<>();
		asset.stream().forEach(s -> responseListVO.add(assetConverter.convertAssetModelToResponseListVo(s)));
		return responseListVO;
	}

	@Override
	public List<AboutToExpireVO> getAboutToExpireAssets() {
		List<AboutToExpireVO> aboutToExpireVoList = new ArrayList<>();
		List<Object[]> asset = softwareRepository.findAboutToExpireAssets();
		Iterator<Object[]> iteratorResult = asset.iterator();
		while (iteratorResult.hasNext()) {
			Object[] item = iteratorResult.next();
			AboutToExpireVO aboutToExpireVoObject = new AboutToExpireVO((int) item[0],(String) item[1], (Date) item[2],
					(Integer) item[3]);
			aboutToExpireVoList.add(aboutToExpireVoObject);
		}
		return aboutToExpireVoList;
	}
	
	

}
