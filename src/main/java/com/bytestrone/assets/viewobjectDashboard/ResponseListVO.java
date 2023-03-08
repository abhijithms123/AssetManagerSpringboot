package com.bytestrone.assets.viewobjectDashboard;

import lombok.Data;

@Data
public class ResponseListVO {
	private String assetNumber;

	private String assetType;
	
	private String manufacturingCompany;

	private String softwareCategory;

	private String licenseType;

	private String softwareName;

	private int purchased;

}