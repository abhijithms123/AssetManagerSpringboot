package com.bytestrone.assets.options;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "licenseType")
public class LicenseType {
	@Id
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	
	@Column(name = "licenseType", unique = false, nullable = false)
	private String licenseType;
}
