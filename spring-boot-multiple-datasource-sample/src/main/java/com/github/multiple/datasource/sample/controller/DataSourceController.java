package com.github.multiple.datasource.sample.controller;

import com.github.http.ModelResult;
import com.github.multiple.datasource.sample.domain.User;
import com.github.multiple.datasource.sample.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author jianlei.shi
 * @date 2021/3/1 11:55 上午
 * @description DataSourceController
 */
@RestController
@RequestMapping("/datasource")
public class DataSourceController {
    @Resource(name = "mUserService")
    private UserService mUserService;
    @Resource(name = "sUserService")
    private UserService sUserService;
    @GetMapping("/m_query/{id}")
    @ResponseBody
    public ModelResult<User> mQuery(@PathVariable("id") Long id) {
        //572807057384669184
        ModelResult<User> result=new ModelResult<>();
        final User user = mUserService.selectByPrimaryKey(id);
        result.setData(user);
        return result;
    }

    @GetMapping("/s_query/{id}")
    @ResponseBody
    public ModelResult<User> sQuery(@PathVariable("id") Long id) {
        //572807057867014145
        ModelResult<User> result=new ModelResult<>();
        final User user = sUserService.selectByPrimaryKey(id);
        result.setData(user);
        return result;
    }
}
