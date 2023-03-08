package com.bytestrone.assets.options;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "manufacturingCompany")
public class ManufacturingCompany {
	@Id
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	
	@Column(name = "assetType", unique = false, nullable = false)
	private String assetType;
	
	@Column(name = "manufacturingCompany", unique = false, nullable = false)
	private String manufacturingCompany;
}
