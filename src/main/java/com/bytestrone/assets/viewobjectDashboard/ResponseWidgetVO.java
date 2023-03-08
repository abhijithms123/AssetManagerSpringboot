package com.bytestrone.assets.viewobjectDashboard;

import java.math.BigInteger;

import lombok.Data;

@Data
public class ResponseWidgetVO {
	
	public ResponseWidgetVO(String softwareCategory, BigInteger purchased, BigInteger installed, BigInteger available) {
		this.softwareCategory = softwareCategory;
		this.available = available;
		this.purchased = purchased;
		this.installed = installed;
	}

	private String softwareCategory;

	private BigInteger available;

	private BigInteger purchased;

	private BigInteger installed;

}
