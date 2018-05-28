package org.mw.demo.service;

import org.mw.demo.param.LoginRequest;
import org.mw.demo.param.LoginResponse;

public interface SystemManageService {

	LoginResponse login(LoginRequest loginRequest);

}