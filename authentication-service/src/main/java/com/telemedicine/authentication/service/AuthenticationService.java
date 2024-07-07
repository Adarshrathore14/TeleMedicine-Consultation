package com.telemedicine.authentication.service;

import com.telemedicine.authentication.dto.LoginDetails;

import javax.security.auth.login.AccountNotFoundException;

public interface AuthenticationService {
    String authenticate(LoginDetails loginDetails) throws AccountNotFoundException;
}
