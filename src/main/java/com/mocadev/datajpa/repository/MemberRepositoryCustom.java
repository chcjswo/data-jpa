package com.mocadev.datajpa.repository;

import com.mocadev.datajpa.entity.Member;
import java.util.List;

/**
 * @author chcjswo
 * @version 1.0.0
 * @blog http://mocadev.tistory.com
 * @github http://github.com/chcjswo
 * @since 2021-05-05
 **/
public interface MemberRepositoryCustom {

	List<Member> findMemberCustom();

}
