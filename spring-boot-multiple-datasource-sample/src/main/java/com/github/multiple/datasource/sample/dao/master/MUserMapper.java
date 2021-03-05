package com.github.multiple.datasource.sample.dao.master;

import com.github.multiple.datasource.sample.domain.User;

public interface MUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}