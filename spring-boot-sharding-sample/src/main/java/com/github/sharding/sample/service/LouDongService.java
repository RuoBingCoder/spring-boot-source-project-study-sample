package com.github.sharding.sample.service;


import com.github.sharding.sample.po.LouDong;

import java.util.List;

public interface LouDongService {

	List<LouDong> list();
	
	Long addLouDong(LouDong louDong);
		
}
