package com.cg.backend.shoppingcart.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cg.backend.shoppingcart.dto.SignupRequest;
import com.cg.backend.shoppingcart.dto.UserDto;
import com.cg.backend.shoppingcart.entities.User;
import com.cg.backend.shoppingcart.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
    private UserRepository userRepo;
	
	@Transactional
	public UserDto createUser(SignupRequest signupRequest) throws Exception {
		User user = new User(signupRequest.getEmail(), new BCryptPasswordEncoder().encode(signupRequest.getPassword()), signupRequest.getName());
        user = userRepo.save(user);
        if (user == null)
            return  null;

        return user.mapUsertoUserDto();
	}

	
	public Boolean hasUserWithEmail(String email) {
		return userRepo.findFirstByEmail(email)!=null;
	}

}
