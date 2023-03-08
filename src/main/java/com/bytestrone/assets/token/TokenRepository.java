package com.bytestrone.assets.token;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {
    
	      @Query("SELECT t FROM Token t JOIN t.user u " +
		       "WHERE u.id = :userId AND (t.expired = false OR t.revoked = false)")
		  List<Token> findAllValidTokenByUser(@Param("userId") Integer id);

		  Optional<Token> findByToken(String token);
}
