package com.opms.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.opms.db.dtos.UserDto;
import com.opms.db.entities.Teacher;
import com.opms.db.entities.User;

@Controller
public class LoginController {
	
	@GetMapping("/login")
    public String login(Model model){
        model.addAttribute("user" , new UserDto());
        
        return "login";
    }

}
