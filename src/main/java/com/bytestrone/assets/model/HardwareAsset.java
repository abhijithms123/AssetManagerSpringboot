package com.bytestrone.assets.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "hardware")
public class HardwareAsset extends Asset {
	
	@Column(name = "hardware_type", length = 40, unique = false)
    private String hardwareType;
	
	private String modelName;
	
	private String modelNumber;
	
	private String configuration;
	
	private int yearOfManufacture;
	
	private String batchNumber;
	
	private String expressServiceCode;
	
	private String ownershipStatus;
	
	private String code;
	
	private String status;
	
	private String enterBy;
	
	
	
	private String serialNumber;
	
	

}
