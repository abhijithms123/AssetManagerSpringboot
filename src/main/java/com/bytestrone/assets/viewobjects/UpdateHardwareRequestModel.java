package com.bytestrone.assets.viewobjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateHardwareRequestModel {
	private String expressServiceCode;
	private String batchNumber;
	private String ownershipStatus;
	private String assetNumber;
	private String code;
	private String configuration;
}
