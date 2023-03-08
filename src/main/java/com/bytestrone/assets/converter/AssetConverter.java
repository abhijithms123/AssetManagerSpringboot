package com.bytestrone.assets.converter;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.bytestrone.assets.model.Asset;
import com.bytestrone.assets.viewobjectDashboard.AboutToExpireVO;
import com.bytestrone.assets.viewobjectDashboard.ResponseListVO;

public class AssetConverter {
	
	@Autowired
	ModelMapper modelMapper;

	public ResponseListVO convertAssetModelToResponseListVo(Asset asset) {
		return modelMapper.map(asset, ResponseListVO.class);
	}
	
	public AboutToExpireVO convertAssetModelToAboutToExpireVO(Object asset) {
		return modelMapper.map(asset, AboutToExpireVO.class);
	}

}
