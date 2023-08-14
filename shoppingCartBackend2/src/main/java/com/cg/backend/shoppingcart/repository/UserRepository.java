package com.cg.backend.shoppingcart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cg.backend.shoppingcart.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	 	
		User findFirstByEmail(String email);

	    Optional<User> findByEmail(String email);

	    Optional<User> findByName(String name);
}
