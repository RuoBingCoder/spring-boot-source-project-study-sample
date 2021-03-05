package com.github.multiple.datasource.sample.service;

import com.github.multiple.datasource.sample.dao.master.MUserMapper;
import com.github.multiple.datasource.sample.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jianlei.shi
 * @date 2021/3/1 11:52 上午
 * @description MUserServiceImpl
 */
@Service("mUserService")
public class MUserServiceImpl implements UserService {
    @Autowired
    private MUserMapper mUserMapper;
    @Override
    public User selectByPrimaryKey(Long id) {
        return mUserMapper.selectByPrimaryKey(id);
    }
}
