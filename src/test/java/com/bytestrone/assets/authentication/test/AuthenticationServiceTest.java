package com.bytestrone.assets.authentication.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bytestrone.assets.auth.AuthenticationRequest;
import com.bytestrone.assets.auth.AuthenticationResponse;
import com.bytestrone.assets.auth.AuthenticationService;
import com.bytestrone.assets.config.JwtService;
import com.bytestrone.assets.model.Role;
import com.bytestrone.assets.model.User;
import com.bytestrone.assets.repository.UserRepository;
import com.bytestrone.assets.token.TokenRepository;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class AuthenticationServiceTest {
	@Mock
	private UserRepository repository;
	@Mock
	private TokenRepository tokenRepository;
	@Mock
	private PasswordEncoder passwordEncoder;
	@Mock
	private JwtService jwtService;
	@Mock
	private AuthenticationManager authenticationManager;
	
	@InjectMocks
	private AuthenticationService authenticationService;
	
    @Test
    void testAuthenticationSucess() {
    	
    	
    	//Arrange
    	 // Create a mock AuthenticationRequest
        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername("testuser");
        request.setPassword("testpassword");

        // Create a mock User
        User user = new User();
        user.setId(1);
        user.setUsername("testuser");
        user.setRole(Role.USER);
        user.setImageUrl("https://example.com/image.png");

        // Create a mock JWT token
        String jwtToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0dXNlciIsImlhdCI6MTYxNjI2MTM1NywiZXhwIjoxNjE2MzQ3NzU3fQ.7UIebRmxvpF_bHKfCv27AapjZ1CtZDC3qehPfHbIJzQ";

        // Mock the repository to return the User when findByUsername is called
        when(repository.findByUsername(request.getUsername())).thenReturn(Optional.of(user));

        // Mock the jwtService to return the JWT token
        when(jwtService.generateToken(user)).thenReturn(jwtToken);

        // ACT
        AuthenticationResponse response = authenticationService.authenticate(request);
        
        
        //Assert
        
        
        // Verify that authenticationManager.authenticate was called once with the correct UsernamePasswordAuthenticationToken
//        verify(authenticationManager, times(1)).authenticate(eq(new UsernamePasswordAuthenticationToken("testuser", "testpassword")));

        // Verify that repository.findByUsername was called once with the correct userName.
        verify(repository, times(1)).findByUsername(request.getUsername());

        // Verify that jwtService.generateToken was called once with the correct User
        verify(jwtService, times(1)).generateToken(user);

        // Verify that revokeAllUserTokens was called once with the correct User
//        verify(authenticationService, times(1)).revokeAllUserTokens(user);
//
//        // Verify that saveUserToken was called once with the correct User and JWT token
//        verify(authenticationService, times(1)).saveUserToken(user, jwtToken);

        // Verify that jwtService.extractExpiration was called once with the correct JWT token
        verify(jwtService, times(1)).extractExpiration(jwtToken);

        // Verify that the AuthenticationResponse object contains the expected values
        assertEquals(jwtToken, response.getToken());
        assertEquals(user.getUsername(), response.getUsername());
        assertEquals(user.getId(), response.getUserId());
        assertEquals(user.getRole(), response.getRole());
        assertEquals(user.getImageUrl(), response.getImageUrl());
        assertEquals(jwtService.extractExpiration(jwtToken), response.getExpiration());
    }}

    

