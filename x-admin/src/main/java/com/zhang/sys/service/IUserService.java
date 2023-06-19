package com.zhang.sys.service;

import com.zhang.common.vo.Result;
import com.zhang.sys.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 用户表; 服务类
 * </p>
 *
 * @author baomidou
 * @since 2023-06-06
 */
public interface IUserService extends IService<User> {

    Map<String, Object> login(User user);

    Map<String, Object> getUserInfo(String token);

    void logout(String token);

    Result<Map<String,Object>> getUserList(String username,
                                           String phone,
                                           long pageNo,
                                           long pageSize);
}
