package com.alsif.tingting.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alsif.tingting.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findBySeq(Long seq);
}
