package com.bytestrone.assets.viewobjectDashboard;

import java.util.Date;

import lombok.Data;

@Data
public class AboutToExpireVO {
	
	private int id;

	private String softwareName;

	private Date licenseExpiryDate;

	private Integer expiresIn;
	
	public AboutToExpireVO(int assetId, String softwareName, Date licenseExpireDate, Integer expiresIn) {
		this.id = assetId;
		this.softwareName = softwareName;
		this.licenseExpiryDate = licenseExpireDate;
		this.expiresIn = expiresIn;
	}

}
