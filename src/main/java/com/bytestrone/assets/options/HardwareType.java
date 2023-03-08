package com.bytestrone.assets.options;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="HardwareType")
public class HardwareType {
	@Id
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	
	@Column(name = "hardwaretype", unique = false, nullable = false)
	private String hardwaretype;

}
