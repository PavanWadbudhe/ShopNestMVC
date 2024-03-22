package com.nt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nt.entity.UserRoleInfo;

public interface IUserRoleInfoRepository extends JpaRepository<UserRoleInfo, Integer> {

}
