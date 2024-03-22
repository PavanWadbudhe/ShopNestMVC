package com.nt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nt.entity.UserPurchaseInfo;

public interface IUserPurchseInfoRepository extends JpaRepository<UserPurchaseInfo, Integer> {

}
