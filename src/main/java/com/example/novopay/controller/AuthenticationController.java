package com.example.novopay.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.novopay.model.PostSignUpDto;
import com.example.novopay.model.RegistrationRequest;
import com.example.novopay.model.SignInRequest;
import com.example.novopay.service.AuthenticationService;
import com.example.novopay.util.TokenUtil;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	TokenUtil tokenUtil;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/register")
	public ResponseEntity doRegistration(@RequestBody @Valid List<RegistrationRequest> registrationRequest,
			HttpServletRequest httpServletRequest) throws Exception {
		LOGGER.info("inside do Registartion method of Authentication Controller for user " + "registration ");
		PostSignUpDto postSignUpDto = authenticationService.doRegistration(registrationRequest);
		return new ResponseEntity(postSignUpDto, HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/login")
	public ResponseEntity doLogin(@RequestBody @Valid final SignInRequest signInRequest) throws Exception {
		LOGGER.info("inside do login method of authentication controller for login");
		return new ResponseEntity(authenticationService.doLogin(signInRequest), HttpStatus.OK);
	}
}
