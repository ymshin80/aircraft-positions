package com.dev.aircraft_positions.repository;

import org.springframework.data.repository.CrudRepository;

import com.dev.aircraft_positions.domain.Member;

public interface MemberRepository extends CrudRepository<Member, Long> {

	Member findByEmail(String email);

}
