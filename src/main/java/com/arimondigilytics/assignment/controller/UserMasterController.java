package com.arimondigilytics.assignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.arimondigilytics.assignment.service.UserMasterService;

@Controller
public class UserMasterController {

	@Autowired
	private UserMasterService userMasterService;
	
}
