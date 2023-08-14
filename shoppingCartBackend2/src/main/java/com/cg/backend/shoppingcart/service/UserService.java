package com.cg.backend.shoppingcart.service;

import com.cg.backend.shoppingcart.dto.SignupRequest;
import com.cg.backend.shoppingcart.dto.UserDto;

public interface UserService {
	UserDto createUser(SignupRequest signupRequest) throws Exception;

    Boolean hasUserWithEmail(String email);
}
