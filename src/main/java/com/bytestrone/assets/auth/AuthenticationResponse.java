package com.bytestrone.assets.auth;

import java.util.Date;

import com.bytestrone.assets.model.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

  private String token;
  
  private String username;
  
  private int userId;
  
  private Role role;
  
  private String imageUrl;
  
  private Date expiration;
}