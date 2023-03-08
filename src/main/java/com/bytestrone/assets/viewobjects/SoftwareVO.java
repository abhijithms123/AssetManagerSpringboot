package com.bytestrone.assets.viewobjects;

import lombok.Data;

@Data
public class SoftwareVO {

	private String softwareName;
	private String manufacturingCompany;
	private String softwareCategory;
	private String licenseType;
	private String version;
	private String assetNumber;
	private int purchased;
	private int installed;
	int id;
}
