package com.arimondigilytics.assignment.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.arimondigilytics.assignment.orm.Role;
import com.arimondigilytics.assignment.orm.User;
import com.arimondigilytics.assignment.service.UserService;

import lombok.val;
import lombok.var;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/")
	public String landingPage() {
		return "index";
	}

	public String convertToCsv(String[] userObj) {
		String[] errorList = new String[3];
		for (var i = 0; i < userObj.length; i++) {
			userObj[i] = userObj[i].split("#")[0];
			errorList[i] = userObj[i].split("#")[1];
		}
		return Stream.of(userObj).collect(Collectors.joining(",")).concat(",")
				.concat(Stream.of(errorList).collect(Collectors.joining(",")));
	}

	@PostMapping("/register")
	public String register(MultipartHttpServletRequest request, HttpServletRequest req, Model model)
			throws IOException {
		int noOfRowsParsed = 0, noOfRowsFailed = 0;
		int error = 0;
		File file = (File) request.getFile("file");
		String line = "";
		List<String> userList = new ArrayList<String>();
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		Set<User> userSet = new HashSet<User>();
		List<String[]> dataArrayList = new ArrayList<String[]>();

		while ((line = bufferedReader.readLine()) != null) {
			userList = Arrays.asList(line.split(","));
			var user = User.builder()
					.emailAddress((userList.get(0).matches("^[a-zA-Z0-9+_-]+@(.+)$")) ? userList.get(0)
							: userList.get(0).concat("#INVALID EMAIL"))
					.name(((userList.get(1)).matches("^[a-zA-Z]*$")) ? (userList.get(1))
							: userList.get(1).concat("#INVALID NAME"))
					.build();
			Set<Role> roleSet = new HashSet<Role>();
			for (var i = 2; i < userList.size(); i++) {
				roleSet.add(Role.builder().roleName((userList.get(i).matches("^[a-zA-Z]*$")) ? userList.get(i)
						: userList.get(i).concat("#INVALID ROLE")).build());
			}
			user.setRoleSet(roleSet);
			noOfRowsParsed++;

			if ((user.toString()).indexOf("INVALID") >= 0) {
				error = 1;
				String[] roleSetArray = new String[roleSet.size() + 2];
				roleSetArray[0] = userList.get(0);
				roleSetArray[1] = userList.get(1);
				int j = 2;
				for (var role : roleSet) {
					roleSetArray[j] = role.getRoleName();
					j++;
				}
				dataArrayList.add(roleSetArray);
				noOfRowsFailed++;
			} else
				userSet.add(user);
		}
		userService.save_user_set(userSet);
		if (error == 1) {
			File fileError = new File("src/main/resources/assets/error.csv");
			PrintWriter printWriter = new PrintWriter(fileError);
			dataArrayList.stream().map(this::convertToCsv).forEach(printWriter::println);
			model.addAttribute("url", "src/main/resources/assets/error.csv");
		}
		model.addAttribute("noOfRowsParsed", noOfRowsParsed);
		model.addAttribute("noOfRowsFailed", noOfRowsFailed);
		model.addAttribute("SaveResult", userService.save_user_set(userSet));
		return "/register";
	}

	@GetMapping("/download/{fileName}")
	public void download(URL url, @PathVariable String fileName) throws IOException {
		val inputStream = url.openStream();
		Files.copy(inputStream, Paths.get(fileName));
	}
}
