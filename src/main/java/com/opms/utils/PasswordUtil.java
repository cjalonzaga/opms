package com.opms.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordUtil {

	@Autowired
	private static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	public static void main(String[] args) {
		//encrypt("password123");
		//encrypt("adminTest1324");
	}
	
	public static void encrypt(String password) {
		String encrypted = passwordEncoder.encode(password);
		System.out.println("Password is : " + encrypted);
	}
	
	public static void decrypt(String password) {
		
	}
	
}
