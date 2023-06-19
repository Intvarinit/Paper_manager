package com.zhang.sys.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.injector.methods.SelectPage;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mysql.cj.log.Log;
import com.zhang.common.vo.Result;
import com.zhang.sys.entity.User;
import com.zhang.sys.mapper.TagMapper;
import com.zhang.sys.mapper.UserMapper;
import com.zhang.sys.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 用户表; 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2023-06-06
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Map<String, Object> login(User user) {
        //根据用户名和密码查询
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername,user.getUsername());
        wrapper.eq(User::getPassword,user.getPassword());
        User LoginUser = this.baseMapper.selectOne(wrapper);
        //结果不为空则返回token,并且将用户信息存入redis
        if(LoginUser != null){
//            生成token
            String key = "user:" + UUID.randomUUID();
            Map<String,Object> data = new HashMap<>();
            //存入redis
            LoginUser.setPassword(null);        //清除敏感信息
            redisTemplate.opsForValue().set(key,LoginUser,30, TimeUnit.MINUTES);         //存入redis
//            生成返回结果
            data.put("token",key);
            return data;
        }
        return null;
    }

    @Override
    public Map<String, Object> getUserInfo(String token) {
//        根据token获取用户信息
        Object obj = redisTemplate.opsForValue().get(token);
        if(obj != null){
            User LoginUser = JSON.parseObject(JSON.toJSONString(obj),User.class);
            Map<String,Object> data = new HashMap<>();
            data.put("name", LoginUser.getUsername());
            data.put("id", LoginUser.getUserID());
            data.put("status", LoginUser.getStatus());
//            data.put("phone",LoginUser.getPhone());
            return data;
        }
        return null;
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete(token);
    }

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TagServiceImpl tagService;

    @Override
    public Result<Map<String, Object>> getUserList(String username, String phone, long pageNo, long pageSize) {
        Map<String,Object> data = new HashMap<>();
        long offset = (pageNo-1)*pageSize;
        List<User> users = userMapper.PageSelectUser(username,phone,offset,pageSize);
        data.put("total",userMapper.selectCount(null));
        data.put("rows",users);
        return Result.success(data);
    }

}
