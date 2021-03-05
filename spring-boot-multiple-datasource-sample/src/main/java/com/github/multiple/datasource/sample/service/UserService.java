package com.github.multiple.datasource.sample.service;

import com.github.multiple.datasource.sample.domain.User;

/**
 * @author jianlei.shi
 * @date 2021/3/1 11:52 上午
 * @description: MUserService
 */
public interface UserService {

    User selectByPrimaryKey(Long id);

}
