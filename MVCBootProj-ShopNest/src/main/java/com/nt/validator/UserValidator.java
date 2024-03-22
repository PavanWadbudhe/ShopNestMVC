package com.nt.validator;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.nt.entity.UserInfo;
import com.nt.entity.UserRoleInfo;

@Component
public class UserValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(UserInfo.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		//Type casting with Model class
		UserInfo uinfo= (UserInfo) target;
		UserRoleInfo rinfo=uinfo.getUserRole();
		
		//Form validation logic and place the error in error obj
		if(uinfo.getFirstName()==null || uinfo.getFirstName().equalsIgnoreCase("") || uinfo.getFirstName().length()==0)
			errors.rejectValue("firstName", "user.first_name.required");
		else if(uinfo.getFirstName().length() <= 2 || uinfo.getFirstName().length() > 20)
			errors.rejectValue("firstName", "user.first_name.length");
		
		if(uinfo.getLastName()==null || uinfo.getLastName().equalsIgnoreCase("") || uinfo.getLastName().length()==0)
			errors.rejectValue("lastName", "user.last_name.required");
		else if(uinfo.getLastName().length() <=2 || uinfo.getLastName().length() > 20)
			errors.rejectValue("lastName", "user.last_name.length");
		
		if(uinfo.getDob()==null || uinfo.getDob().equals("")) 
			errors.rejectValue("dob", "user.birth.required");
		
		if(uinfo.getEmailId()==null || uinfo.getEmailId().equalsIgnoreCase("") || uinfo.getEmailId().length()==0)
			errors.rejectValue("emailId", "user.email.required");
		else if(!(uinfo.getEmailId().contains("@") && uinfo.getEmailId().contains(".")))
			errors.rejectValue("emailId", "user.email.contain");
		
		if(uinfo.getPhno()==null ||uinfo.getPhno().equalsIgnoreCase("") || uinfo.getPhno().length()==0)
		    errors.rejectValue("phno", "user.phno.required");
		else if(uinfo.getPhno().length() < 10 || uinfo.getPhno().length() > 20)
		    errors.rejectValue("phno", "user.phno.length");
		
		if(uinfo.getAmount()==null)
			errors.rejectValue("amount", "user.amount.required");
		
		if(uinfo.getPword()==null || uinfo.getPword().equalsIgnoreCase("") || uinfo.getPword().length()==0)
			errors.rejectValue("pword", "user.password.required");
		else if(uinfo.getPword().length() < 6 || uinfo.getPword().length() > 20)
			errors.rejectValue("pword", "user.password.length");
		
		if(uinfo.getAddrs()==null || uinfo.getAddrs().equalsIgnoreCase("") || uinfo.getAddrs().length()==0)
			errors.rejectValue("addrs", "user.addr.required");
		else if(uinfo.getAddrs().length() > 20)
			errors.rejectValue("addrs", "user.addrs.length");
		
		if(rinfo.getRoleName()==null || rinfo.getRoleName().equalsIgnoreCase("")) {
			errors.rejectValue("userRole.roleName", "user.role.required");			
		}	
		else if(rinfo.getRoleName().length() > 20)
			errors.rejectValue("userRole.roleName", "user.role.length");
		
		if(rinfo.getRoleDesc()==null || rinfo.getRoleDesc().equalsIgnoreCase(""))
			errors.rejectValue("userRole.roleDesc", "user.role_desc.required");
		else if(rinfo.getRoleDesc().length() > 40)
			errors.rejectValue("userRole.roleDesc", "user.role_desc.length");
		
				

	}

}
