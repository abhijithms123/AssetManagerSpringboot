package com.bytestrone.assets.viewobjects;

import java.time.LocalDate;

import lombok.Data;

@Data
public class SoftwareRequestModel {

	private String assetNumber;
	private String manufacturingCompany;
	private String version;
	private String subVersion;
	private String softwareCategory;
	private int licenseYear;
	private String licenseType;
	private String softwareName;
	private int purchased;
	private LocalDate licenseExpiryDate;
	private float licenseCost;
	private String enteredBy;

}
