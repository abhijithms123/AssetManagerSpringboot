package com.bytestrone.assets.viewobjects;

import lombok.Data;

@Data
public class HardwareVO {
	private String assetNumber;
	private String hardwareType;
	private String manufacturingCompany;
	private String modelName;
	private int yearOfManufacture;
	private String ownershipStatus;
	private int id;
	private String status;
}