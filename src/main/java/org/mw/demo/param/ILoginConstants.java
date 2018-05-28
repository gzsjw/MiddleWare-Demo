package org.mw.demo.param;

public interface ILoginConstants {
	/**
	 * 未知错误
	 */
	public static final int UNKNOWN_ERROR = -1;

	/**
	 * 登录成功
	 */
	public static final int LOGIN_SUCCESS = 0;

	/**
	 * 身份合法
	 */
	public static final int USER_IDENTITY_LEGAL = 1;

	/**
	 * 名称错误
	 */
	public static final int USER_NAME_WRONG = 2;

	/**
	 * 密码错误
	 */
	public static final int USER_NAME_RIGHT_PWD_WRONG = 3;

	/**
	 * 用户被锁定
	 */
	public static final int USER_LOCKED = 4;

	/**
	 * 用户已在线
	 */
	public static final int USER_ALREADY_ONLINE = 5;

	/**
	 * 用户未到生效日期
	 */
	public static final int USER_BEFORE_EFFECT = 6;

	/**
	 * 用户已到失效日期
	 */
	public static final int USER_AFTER_EXPIRED = 7;

	/**
	 * 达到用户数上限
	 */
	public static final int OVER_MAX_USER = 8;

	/**
	 * 用户未启用
	 */
	public static final int USER_UNENABLE = 9;

	/**
	 * 用户已停用
	 */
	public static final int USER_DISABLE = 10;

	/**
	 * 登陆客户端IP非法
	 */
	public static final int ILLEGAL_CLIENT = 11;

	/**
	 * 切换登录身份不合法
	 */
	public static final int SWITCH_LOGIN_IDENTITY_ILLEGAL = 12;

	/**
	 * 业务中心有效
	 */
	public static final int BUSICENTER_VALIDATE = 21;

	/**
	 * 业务中心被锁定
	 */
	public static final int BUSICENTER_LOCKED = 22;

	/**
	 * 业务中心还未到生效日期
	 */
	public static final int BUSICENTER_BEFORE_EFFECT = 23;

	/**
	 * 业务中心已到失效日期
	 */
	public static final int BUSICENTER_AFTER_EXPIRED = 24;

	/**
	 * 业务中心不存在
	 */
	public static final int BUSICENTER_NOT_FIND = 25;

	/**
	 * 注册登录用户成功
	 */
	public static final int REGISTER_USER_SUCCESS = 30;

	/**
	 * 达到license最大数
	 */
	public static final int REGISTER_USER_OVER_MAX_LICENSE = 31;

	/**
	 * 注册节点成功
	 */
	public static final int REGISTER_OPENNODE_SUCCESS = 32;

	public static final String[] MESSAGE_STR = { "登录成功", "身份合法", "名称错误",
			"密码错误", "用户被锁定", "用户在线", "用户未到生效日期", "用户已到失效日期", "达到用户数上限",
			"用户未启用", "用户已停用", "登录客户端IP非法", "切换登录身份不合法", "", "", "", "", "", "",
			"", "", "业务中心有效", "业务中心被锁定", "业务中心还未到生效日期", "业务中心已到失效日期",
			"业务中心不存在", "", "", "", "", "注册登录用户成功", "达到license最大数", "注册节点成功" };

}
