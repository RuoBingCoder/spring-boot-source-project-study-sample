package com.github.multiple.datasource.sample.dao.slave;

import com.github.multiple.datasource.sample.domain.User;

public interface SUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}