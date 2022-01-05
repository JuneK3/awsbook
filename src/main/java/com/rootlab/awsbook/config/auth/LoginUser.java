package com.rootlab.awsbook.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser {
	/*
	SessionUser user = (SessionUser) httpSession.getAttribute("user);
	위의 코드로 세션 정보를 가져오는 방식을 개선
	어느 Controller에서든지 @LoginUser만 사용하면 세션 정보를 가져올 수 있게 됨
 	*/
}
