package com.arimondigilytics.assignment.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arimondigilytics.assignment.orm.User;
import com.arimondigilytics.assignment.repository.UserRepository;

import lombok.var;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public String save_user_set(Set<User> userSet) {
		var result = (List<User>) userRepository.saveAll(userSet);
		return (result.size() == userSet.size()) ? "Success" : "Fail";
	}
}
