package com.bytestrone.assets.viewobjects;

import lombok.Data;

@Data
public class AssetRequestModel {
     private String reqeustId;
     private int asset;
     private String requestedBy;
     private String requestDate;
     private String requestStatus;
     private String reason;
     
}
