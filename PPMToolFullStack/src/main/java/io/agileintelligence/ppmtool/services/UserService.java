package io.agileintelligence.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import io.agileintelligence.ppmtool.domain.User;
import io.agileintelligence.ppmtool.exceptions.UserAlreadyExistsException;
import io.agileintelligence.ppmtool.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public User saveUser(User newUser) {
		try{
			newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
			newUser.setConfirmPassword("");
			return userRepository.save(newUser);
			
		}catch (Exception e) {
			throw new UserAlreadyExistsException("Username "+newUser.getUsername()+" is already exists.");
		}		
	}

}
