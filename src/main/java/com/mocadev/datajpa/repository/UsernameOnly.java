package com.mocadev.datajpa.repository;

/**
 * @author chcjswo
 * @version 1.0.0
 * @blog http://mocadev.tistory.com
 * @github http://github.com/chcjswo
 * @since 2021-05-06
 **/
public interface UsernameOnly {

	//	@Value("#{target.username + '  ' + target.age}")
	String getUsername();

}
