package com.github.sharding.sample.repository;

import com.github.sharding.sample.po.LouDong;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface LouDongRepository {
	
	Long addLouDong(LouDong louDong);
	
	List<LouDong> list();
}
