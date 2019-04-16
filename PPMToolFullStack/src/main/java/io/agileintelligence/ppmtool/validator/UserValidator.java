package io.agileintelligence.ppmtool.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import io.agileintelligence.ppmtool.domain.User;

@Component
public class UserValidator implements Validator{
	
	
	public boolean supports(Class<?> classObj){
		return User.class.equals(classObj);
	}

	@Override
	public void validate(Object object, Errors errors) {
		
		User user = (User) object;
		if(user.getPassword().length()<6){
			errors.rejectValue("password", "Length","Password must be at least 6 charactors");
		}
		
		if(!user.getPassword().equals(user.getConfirmPassword())){
			errors.rejectValue("confirmPassword","Match","Password and confirm password don't match.");
		}
	}	
}
