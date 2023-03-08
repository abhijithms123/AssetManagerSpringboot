package com.bytestrone.assets.viewobjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HardwareRequestModel {
	private String assetNumber;
	private String hardwareType;
	private String manufacturingCompany;
	private String modelNumber;
	private String serialNumber;
	private String modelName;
	private int yearOfManufacture;
	private String expressServiceCode;
	private String batchNumber;
	private String ownershipStatus;
	private String code;
	private String configuration;
	private String enterBy;
}
