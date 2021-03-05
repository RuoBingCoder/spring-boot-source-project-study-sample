package com.github.multiple.datasource.sample.service;

import com.github.multiple.datasource.sample.dao.slave.SUserMapper;
import com.github.multiple.datasource.sample.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jianlei.shi
 * @date 2021/3/1 11:52 上午
 * @description MUserServiceImpl
 */
@Service("sUserService")
public class SUserServiceImpl implements UserService {
    @Autowired
    private SUserMapper sUserMapper;
    @Override
    public User selectByPrimaryKey(Long id) {
        return sUserMapper.selectByPrimaryKey(id);
    }
}
