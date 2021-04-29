package com.mocadev.datajpa.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

/**
 * @author chcjswo
 * @version 1.0.0
 * @blog http://mocadev.tistory.com
 * @github http://github.com/chcjswo
 * @since 2021-04-30
 **/
@Entity
@Getter
@Setter
public class Member {

	@Id @GeneratedValue
	private Long id;
	private String username;

	protected Member() {}

	public Member(String username) {
		this.username = username;
	}

}
