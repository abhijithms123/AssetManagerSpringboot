package com.bytestrone.assets.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="software")
public class SoftwareAsset extends Asset {
	private String softwareName;

	private int purchased;

	private String softwareCategory;

//	private String manufacturer;

	private String version;

	private String subVersion;

	private String licenseType;

	private int licenseYear;

	private LocalDate licenseExpiryDate;

	private int installed;

	private String status;

	private String enteredBy;

}
