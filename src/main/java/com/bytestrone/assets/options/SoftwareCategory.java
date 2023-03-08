package com.bytestrone.assets.options;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "softwareCategory")
public class SoftwareCategory {
	@Id
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	
	@Column(name = "softwareCategory", unique = true, nullable = false)
	private String softwareCategory;

}