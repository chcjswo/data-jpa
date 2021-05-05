package com.mocadev.datajpa.controller;

import com.mocadev.datajpa.dto.MemberDto;
import com.mocadev.datajpa.entity.Member;
import com.mocadev.datajpa.repository.MemberRepository;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chcjswo
 * @version 1.0.0
 * @blog http://mocadev.tistory.com
 * @github http://github.com/chcjswo
 * @since 2021-05-06
 **/
@RestController
@RequiredArgsConstructor
public class MemberController {

	private final MemberRepository memberRepository;

	@GetMapping("/members/{id}")
	public String findMember(@PathVariable Long id) {
		Member member = memberRepository.findById(id).get();
		return member.getUsername();
	}

	@GetMapping("/members2/{id}")
	public String findMember2(@PathVariable("id") Member member) {
		return member.getUsername();
	}

	@GetMapping("/members")
	public Page<MemberDto> list(@PageableDefault(size = 3, sort = "username") Pageable pageable) {
		return memberRepository.findAll(pageable)
			.map(MemberDto::new);
	}

	@PostConstruct
	public void init() {
		for (int i = 0; i < 100; i++) {
			memberRepository.save(new Member(String.format("user%d", i), i * 10));
		}
	}


}
