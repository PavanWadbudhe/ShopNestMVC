package com.nt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nt.entity.UserInfo;

public interface IUserInfoRepository extends JpaRepository<UserInfo, Integer> {
	public Optional<UserInfo> findByEmailId(String email);

}
