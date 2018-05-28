package org.mw.demo.controller;

import org.mw.demo.param.ILoginConstants;
import org.mw.demo.param.LoginRequest;
import org.mw.demo.param.LoginResponse;
import org.mw.demo.service.SystemManageService;
import org.mw.demo.util.PubConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SystemCtrl {
	protected static final Logger LOG = LoggerFactory.getLogger(SystemCtrl.class);

	@Value(value = "${nc63.rfffp_baseurl}")
	private String nc63_rfffp_baseurl;

	@Value(value = "${nc.busiCenterCode}")
	private String nc_busiCenterCode;

	@Autowired
	private SystemManageService demoSystemService;

	@RequestMapping(path = "/system/{systemid}/login", method = RequestMethod.POST)
	@ResponseBody
	public LoginResponse login(@PathVariable("systemid") String systemid, @RequestBody LoginRequest loginRequest) {
		if (systemid.equals(PubConst.SYSTEM_DEMO)) {
			return demoSystemService.login(loginRequest);
		}
		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setLoginResult(ILoginConstants.UNKNOWN_ERROR);
		loginResponse.setLoginMessage("未识别的系统");
		return loginResponse;
	}
}
