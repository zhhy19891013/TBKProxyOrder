package com.yj.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 通用的异常处理
 * 
 * @author Administrator
 *
 */
@RestControllerAdvice
public class CommonExceptionHandler {
	/**
	 * 处理未知异常
	 * @param request
	 * @return
	 */
	@ExceptionHandler(value = Exception.class)
	public Object hanlderException(HttpRequest request) {
		Map map = new HashMap();
		map.put("code", 100);
		map.put("msg", "");
		map.put("url", request.getURI());
		return map;
	}

}
