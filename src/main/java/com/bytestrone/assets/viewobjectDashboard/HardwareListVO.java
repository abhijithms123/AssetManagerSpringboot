package com.bytestrone.assets.viewobjectDashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class HardwareListVO {
	
	private String assetNumber;
	private String hardwareType;
    private String manufacturingCompany;
    private String modelName;
    private int yearOfManufacture;
    private String ownershipStatus;

}

