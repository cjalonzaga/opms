package com.opms.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.opms.db.entities.User;
import com.opms.repositories.ParentRepository;
import com.opms.repositories.StudentRepository;
import com.opms.repositories.SuperAdminRepository;
import com.opms.repositories.TeacherRepository;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {
	
	@Autowired
	private TeacherRepository teacherRepository;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private ParentRepository parentRepository;
	
	@Autowired
	private SuperAdminRepository superAdminRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = null;
		if(teacherRepository.ifUserExist(username)) {
			user = teacherRepository.findByUsername(username);
		}
	
		if(studentRepository.ifUserExist(username)) {
			user = studentRepository.findByUsername(username);
		}
		
		if(parentRepository.ifUserExist(username)) {
			user = parentRepository.findByUsername(username);
		}
		
		if(superAdminRepository.ifUserExist(username)) {
			user = superAdminRepository.findByUsername(username);
		}
		
        if (user == null){
            throw new UsernameNotFoundException("User not found!");
        }
        
        return new ApplicationUserDetails(user);
	}

}
