package com.bytestrone.assets.viewobjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateSoftwareRequestModel {
	private String subVersion;
	private String softwareCategory;
	private String licenceType;
	private String softwareName;
	private int purchased;
	private int installed;
}
