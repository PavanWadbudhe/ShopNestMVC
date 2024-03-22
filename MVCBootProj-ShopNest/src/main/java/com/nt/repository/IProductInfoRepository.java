package com.nt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nt.entity.ProductInfo;

public interface IProductInfoRepository extends JpaRepository<ProductInfo, Integer> {

}
