package com.arimondigilytics.assignment.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.arimondigilytics.assignment.orm.Role;
import com.arimondigilytics.assignment.orm.User;
import com.arimondigilytics.assignment.service.UserService;

import lombok.var;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/")
	public String landingPage() {
		return "index";
	}

	@PostMapping("/register")
	public String register(MultipartHttpServletRequest request) throws IOException {
		File file = (File) request.getFile("file");
		String line = "";
		List<String> userList = new ArrayList<String>();
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		Set<User> userSet = new HashSet<User>();
		while ((line = bufferedReader.readLine()) != null) {
			userList = Arrays.asList(line.split(","));
			var user = User.builder().emailAddress(userList.get(0)).name(userList.get(1)).build();
			Set<Role> roleSet = new HashSet<Role>();
			for (var i = 2; i < userList.size(); i++) {
				roleSet.add(Role.builder().roleName(userList.get(i)).build());
			}
			user.setRoleSet(roleSet);
			userSet.add(user);
		}
		
		return "";
	}
}
