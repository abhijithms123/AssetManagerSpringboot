package com.bytestrone.assets.options;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ownershipStatus")
public class OwnershipStatus {
	@Id
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	
	@Column(name = "ownershipStatus", unique = false, nullable = false)
	private String ownershipStatus;
}
