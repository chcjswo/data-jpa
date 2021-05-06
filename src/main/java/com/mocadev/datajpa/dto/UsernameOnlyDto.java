package com.mocadev.datajpa.dto;

/**
 * @author chcjswo
 * @version 1.0.0
 * @blog http://mocadev.tistory.com
 * @github http://github.com/chcjswo
 * @since 2021-05-06
 **/
public class UsernameOnlyDto {

	private final String username;

	public String getUsername() {
		return username;
	}

	public UsernameOnlyDto(String username) {
		this.username = username;


	}
}
