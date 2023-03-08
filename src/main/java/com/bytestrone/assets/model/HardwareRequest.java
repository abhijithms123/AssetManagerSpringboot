package com.bytestrone.assets.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class HardwareRequest {
	
   @Id
   private String requestId;
   
   private String modelName;
   
   private String manufacturingCompany;
   
   private String hardwareType;
   
   private String requestStatus;
   
   private String configuration;
   
   private String requestedBy;
   
   private Date requestedDate;
   
   private String reason;
   
   private int assetId;
   
   
}
