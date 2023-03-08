package com.bytestrone.assets.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class SoftwareRequest {
	
   @Id
   private String requestId;
   
   private String softwareName;
   
   private String manufacturingCompany;
   
   private String softwareCategory;
   
   private String requestStatus;
   
   private String requestedBy;
   
   private Date requestedDate;
   
   private String reason;
   
   private int assetId;
   
  
}
