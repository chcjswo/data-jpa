package com.mocadev.datajpa.repository;


import static org.assertj.core.api.Assertions.assertThat;

import com.mocadev.datajpa.dto.MemberDto;
import com.mocadev.datajpa.entity.Member;
import com.mocadev.datajpa.entity.Team;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chcjswo
 * @version 1.0.0
 * @blog http://mocadev.tistory.com
 * @github http://github.com/chcjswo
 * @since 2021-04-30
 **/

@SpringBootTest
@Transactional
@Rollback(false)
class MemberRepositoryTest {

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	TeamRepository teamRepository;

	@PersistenceContext
	EntityManager em;

	@Test
	void testMember() {
		// given
		Member member = new Member("jpa");
		Member savedMember = memberRepository.save(member);

		Member findMember = memberRepository.findById(savedMember.getId()).get();

		assertThat(findMember.getId()).isEqualTo(savedMember.getId());
		assertThat(findMember.getUsername()).isEqualTo(savedMember.getUsername());
	}

	@Test
	void findByUsernameAndAgeGreaterThanTest() {
		// given
		Member m1 = new Member("AAA", 20);
		Member m2 = new Member("AAA", 10);

		memberRepository.save(m1);
		memberRepository.save(m2);

		List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 15);

		assertThat(result.get(0).getUsername()).isEqualTo("AAA");
		assertThat(result.get(0).getAge()).isEqualTo(20);
		assertThat(result.size()).isEqualTo(1);
	}

	@Test
	void findUserTest() {
		// given
		Member m1 = new Member("AAA", 20);
		Member m2 = new Member("AAA", 10);
		memberRepository.save(m1);
		memberRepository.save(m2);

		List<Member> result = memberRepository.findUser("AAA", 20);

		assertThat(result.get(0).getUsername()).isEqualTo("AAA");
		assertThat(result.get(0).getAge()).isEqualTo(20);
		assertThat(result.size()).isEqualTo(1);
	}

	@Test
	void findUsernameListTest() {
		// given
		Member m1 = new Member("AAA", 20);
		Member m2 = new Member("AAA", 10);
		memberRepository.save(m1);
		memberRepository.save(m2);

		List<String> result = memberRepository.findUsernameList();

		assertThat(result.size()).isEqualTo(2);
	}

	@Test
	void findDtoTest() {
		// given
		Team team = new Team("teamA");
		teamRepository.save(team);

		Member m1 = new Member("AAA", 20);
		m1.setTeam(team);
		memberRepository.save(m1);

		List<MemberDto> memberDto = memberRepository.findMemberDto();

		for (MemberDto dto : memberDto) {
			System.out.println("dto = " + dto);
		}
	}

	@Test
	void findNamesTest() {
		// given
		Member m1 = new Member("AAA", 20);
		Member m2 = new Member("BBB", 10);
		memberRepository.save(m1);
		memberRepository.save(m2);

		List<Member> result = memberRepository.findByNames(Arrays.asList("AAA", "BBB"));
		for (Member member : result) {
			System.out.println("member = " + member);
		}
	}

	@Test
	void returnTypeTest() {
		Member m1 = new Member("AAA", 20);
		Member m2 = new Member("BBB", 10);
		memberRepository.save(m1);
		memberRepository.save(m2);

		Member findMember = memberRepository.findMemberByUsername("AAA");
		Optional<Member> optionalMember = memberRepository.findOptionalByUsername("AAAAA");
		System.out.println("optionalMember = " + optionalMember);
	}

	@Test
	void pagingTest() {
		// given
		memberRepository.save(new Member("member1", 20));
		memberRepository.save(new Member("member2", 20));
		memberRepository.save(new Member("member3", 20));
		memberRepository.save(new Member("member4", 20));
		memberRepository.save(new Member("member5", 20));
		memberRepository.save(new Member("member6", 20));

		PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Direction.DESC, "username"));

		// when
		Page<Member> page = memberRepository.findByAge(20, pageRequest);

		// then
		List<Member> content = page.getContent();
		long totalElements = page.getTotalElements();

		for (Member member : content) {
			System.out.println("member = " + member);
		}
		System.out.println("totalElements = " + totalElements);

		assertThat(content.size()).isEqualTo(3);
		assertThat(page.getTotalElements()).isEqualTo(6);
		assertThat(page.getNumber()).isEqualTo(0);
		assertThat(page.getTotalPages()).isEqualTo(2);
		assertThat(page.isFirst()).isTrue();
		assertThat(page.hasNext()).isTrue();
	}

	@Test
	void pagingDtoTest() {
		// given
		memberRepository.save(new Member("member1", 20));
		memberRepository.save(new Member("member2", 20));
		memberRepository.save(new Member("member3", 20));
		memberRepository.save(new Member("member4", 20));
		memberRepository.save(new Member("member5", 20));
		memberRepository.save(new Member("member6", 20));

		PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Direction.DESC, "username"));

		// when
		Page<Member> page = memberRepository.findByAge(20, pageRequest);

		Page<MemberDto> toMap = page.map(m -> new MemberDto(m.getId(), m.getUsername(), null));

		for (MemberDto memberDto : toMap) {
			System.out.println("memberDto = " + memberDto);
		}

		// then
		List<Member> content = page.getContent();
		long totalElements = page.getTotalElements();

		for (Member member : content) {
			System.out.println("member = " + member);
		}
		System.out.println("totalElements = " + totalElements);

		assertThat(content.size()).isEqualTo(3);
		assertThat(page.getTotalElements()).isEqualTo(6);
		assertThat(page.getNumber()).isEqualTo(0);
		assertThat(page.getTotalPages()).isEqualTo(2);
		assertThat(page.isFirst()).isTrue();
		assertThat(page.hasNext()).isTrue();
	}

	@Test
	void sliceTest() {
		// given
		memberRepository.save(new Member("member1", 20));
		memberRepository.save(new Member("member2", 20));
		memberRepository.save(new Member("member3", 20));
		memberRepository.save(new Member("member4", 20));
		memberRepository.save(new Member("member5", 20));
		memberRepository.save(new Member("member6", 20));

		PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Direction.DESC, "username"));

		// when
		Slice<Member> page = memberRepository.findSliceByAge(20, pageRequest);

		// then
		List<Member> content = page.getContent();

		for (Member member : content) {
			System.out.println("member = " + member);
		}

		assertThat(content.size()).isEqualTo(3);
		assertThat(page.getNumber()).isEqualTo(0);
		assertThat(page.isFirst()).isTrue();
		assertThat(page.hasNext()).isTrue();
	}

	@Test
	void bulkUpdateTest() {
		// given
		memberRepository.save(new Member("member1", 10));
		memberRepository.save(new Member("member2", 19));
		memberRepository.save(new Member("member3", 21));
		memberRepository.save(new Member("member4", 24));
		memberRepository.save(new Member("member5", 40));
		memberRepository.save(new Member("member6", 30));

		// when
		int resultCount = memberRepository.bulkAgePlus(20);

		Member member5 = memberRepository.findMemberByUsername("member5");
		System.out.println("member5 = " + member5);

		// then
		assertThat(resultCount).isEqualTo(4);
	}

	@Test
	void lazyTest() throws Exception {
		// given
		// member1 -> teamA
		// member2 -> teamB

		Team teamA = new Team("teamA");
		Team teamB = new Team("teamB");
		teamRepository.save(teamA);
		teamRepository.save(teamB);
		Member member1 = new Member("member1", 10, teamA);
		Member member2 = new Member("member2", 10, teamB);
		memberRepository.save(member1);
		memberRepository.save(member2);

		em.flush();
		em.clear();

		// when N + 1
		List<Member> members = memberRepository.findAll();
		for (Member member : members) {
			System.out.println("member = " + member.getUsername());
			System.out.println("member.team = " + member.getTeam().getName());
		}
	}

	@Test
	void fetchJoinTest() throws Exception {
		// given
		// member1 -> teamA
		// member2 -> teamB

		Team teamA = new Team("teamA");
		Team teamB = new Team("teamB");
		teamRepository.save(teamA);
		teamRepository.save(teamB);
		Member member1 = new Member("member1", 10, teamA);
		Member member2 = new Member("member2", 10, teamB);
		memberRepository.save(member1);
		memberRepository.save(member2);

		em.flush();
		em.clear();

		// when N + 1
		List<Member> members = memberRepository.findMemberFetchJoin();
		for (Member member : members) {
			System.out.println("member = " + member.getUsername());
			System.out.println("member.team = " + member.getTeam().getName());
		}
	}

	@Test
	void entityGraphTest() throws Exception {
		// given
		// member1 -> teamA
		// member2 -> teamB

		Team teamA = new Team("teamA");
		Team teamB = new Team("teamB");
		teamRepository.save(teamA);
		teamRepository.save(teamB);
		Member member1 = new Member("member1", 10, teamA);
		Member member2 = new Member("member2", 10, teamB);
		memberRepository.save(member1);
		memberRepository.save(member2);

		em.flush();
		em.clear();

		// when
		List<Member> members = memberRepository.findAll();
		for (Member member : members) {
			System.out.println("member = " + member.getUsername());
			System.out.println("member.team = " + member.getTeam().getName());
		}
	}

	@Test
	void queryHintTest() {
		// given
		Member member1 = memberRepository.save(new Member("member1", 10));
		em.flush();
		em.clear();

		// when
		Member findMember = memberRepository.findReadOnlyByUsername("member1");
		findMember.setUsername("member2");

		em.flush();
	}

	@Test
	void lockTest() {
		// given
		Member member1 = memberRepository.save(new Member("member1", 10));
		em.flush();
		em.clear();

		// when
		List<Member> result = memberRepository.findLockByUsername("member1");
	}

}