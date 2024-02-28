package com.opms.configuration;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.opms.db.entities.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoginSuccessHandlers extends SavedRequestAwareAuthenticationSuccessHandler{
	
	@Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {
 
		ApplicationUserDetails userDetails = (ApplicationUserDetails) authentication.getPrincipal();
        //System.out.println("TRIGGERED HERE !!!");
        String redirectURL = request.getContextPath();
         
        if (userDetails.hasRole("Teacher")) {
            redirectURL = "admin/dashboard";
        } else if (userDetails.hasRole("Student")) {
            redirectURL = "student/dashboard";
        } else if (userDetails.hasRole("Parent")) {
            redirectURL = "parent/dashboard";
        }
         
        response.sendRedirect(redirectURL);
         
    }
	
}
