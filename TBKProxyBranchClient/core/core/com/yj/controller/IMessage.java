package com.yj.controller;

public interface IMessage {
	public static final String SUCCESS = "success";
	public static final String ERROR="error";
	public static final String NO_LOGIN="nologin";
	public static final String NO_LOGIN_TB="nologintb";
	public static final String VALIDATION_FAILURE="authentication_error";
	public static final String ERROR_DUL="dulcate";
	
	public static final String FAST_ERROR="操作过快，请休息下！";
	public static final String NO_LOGIN_TB_CN="请使用客户端登录淘宝！";
	public static final String NO_TUIGUANG_INFO="转换失败，可能已经退出计划，请选择其他商品!";
	
}
