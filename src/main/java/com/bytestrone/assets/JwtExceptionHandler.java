package com.bytestrone.assets;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import io.jsonwebtoken.ExpiredJwtException;

@ControllerAdvice
public class JwtExceptionHandler extends RuntimeException {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = -5901378993673949412L;

	@ExceptionHandler(value = {ExpiredJwtException.class})
    public ResponseEntity<Object> handleExpiredJwtException(ExpiredJwtException ex) {
        String errorMessage = "JWT token has expired";
        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }
}
