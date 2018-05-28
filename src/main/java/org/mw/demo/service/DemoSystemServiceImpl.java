package org.mw.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.mw.demo.param.ILoginConstants;
import org.mw.demo.param.LoginRequest;
import org.mw.demo.param.LoginResponse;
import org.mw.demo.param.User;
import org.mw.demo.util.PubConst;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("demoSystemService")
public class DemoSystemServiceImpl implements SystemManageService {

	private static LoginResponse ERROR_USER_CODE = new LoginResponse(ILoginConstants.USER_NAME_WRONG,
			ILoginConstants.MESSAGE_STR[ILoginConstants.USER_NAME_WRONG]);

	private static LoginResponse ERROR_USER_PASSWORD = new LoginResponse(ILoginConstants.USER_NAME_RIGHT_PWD_WRONG,
			ILoginConstants.MESSAGE_STR[ILoginConstants.USER_NAME_RIGHT_PWD_WRONG]);

	private static User[] createUserWithAvatar() {
		List<User> userList = new ArrayList<User>();
		for (int i = 1; i < 9; i++) {
			User auser = new User();
			auser.setUserId("0" + i);
			auser.setUserCode("0" + i);
			auser.setUserName("user0" + i);
			auser.setUserPWD("0" + i);
			auser.setAvatar("/useravatar/" + PubConst.SYSTEM_DEMO + "/0" + i + ".jpg");
			userList.add(auser);
		}
		return userList.toArray(new User[0]);
	}

	private static User[] defaultUsers = DemoSystemServiceImpl.createUserWithAvatar();
	private static String[] defaultFuncNodes = { "FuncNode01", "FuncNode02", "FuncNode03", "FuncNode04" };

	@Override
	@Transactional
	public LoginResponse login(LoginRequest loginRequest) {
		// 测试用例按userCode生成模拟数据
		String userCode = loginRequest.getUserCode();
		// 测试用例：用户名错误
		if (userCode.equalsIgnoreCase("errorusercode")) {
			return DemoSystemServiceImpl.ERROR_USER_CODE;
		}
		// 测试用例：密码错误
		if (userCode.equalsIgnoreCase("erroruserpassword")) {
			return DemoSystemServiceImpl.ERROR_USER_PASSWORD;
		}
		// 测试用例：用户名包含funcnode，进行节点权限测试
		if (userCode.toLowerCase().indexOf("funcnode") >= 0) {
			LoginResponse loginResponse = new LoginResponse();
			loginResponse.setLoginResult(ILoginConstants.LOGIN_SUCCESS);
			loginResponse.setLoginMessage(null);
			User user = new User();
			user.setUserId(userCode);
			user.setUserCode(userCode);
			user.setUserName(userCode);
			user.setUserPWD(loginRequest.getUserPWD());
			loginResponse.setUser(user);
			List<String> funcnodes = new ArrayList<String>();
			for (int i = 1; i <= DemoSystemServiceImpl.defaultFuncNodes.length; i++) {
				if (userCode.indexOf("0" + i) >= 0) {
					funcnodes.add(DemoSystemServiceImpl.defaultFuncNodes[i - 1]);
				}
			}
			loginResponse.setFunccodes(funcnodes.toArray(new String[0]));
			return loginResponse;
		}
		// 测试用例：系统默认有01-08共8个用户，具有所有节点权限
		LoginResponse loginResponse = new LoginResponse(ILoginConstants.USER_NAME_WRONG,
				ILoginConstants.MESSAGE_STR[ILoginConstants.USER_NAME_WRONG]);
		for (User user : DemoSystemServiceImpl.defaultUsers) {
			if (user.getUserCode().equalsIgnoreCase(userCode)) {
				loginResponse.setLoginResult(ILoginConstants.LOGIN_SUCCESS);
				loginResponse.setLoginMessage(null);
				loginResponse.setUser(user);
				loginResponse.setFunccodes(DemoSystemServiceImpl.defaultFuncNodes);
				break;
			}
		}
		return loginResponse;
	}

}
