package com.bytestrone.assets.model;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import javax.persistence.SequenceGenerator;



import lombok.Data;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public class Asset {
	
	
	@Id
	@SequenceGenerator(name = "asset_sequence", sequenceName = "asset_sequence", initialValue = 100, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "asset_sequence")
	@Column(name="asset_id")
	private int id;
	
	private String manufacturingCompany;
	
	private String assetType;
	
	private String assetNumber;
	
//	@JsonCreator
//	public Asset (@JsonProperty("asset_id") Integer id ) {
//	    this.id = id;
//	}
//	


	
	
	
}
