package com.zhang.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.common.vo.Result;
import com.zhang.sys.entity.User;
import com.zhang.sys.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表; 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2023-06-06
 */
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping("/all")
    public Result<List<User>> getAllUser(){
        List<User> users = userService.list();
        return Result.success(users);
    }

    @PostMapping("/login")
    public Result<Map<String,Object>> login(@RequestBody User user){
        Map<String,Object> ret = userService.login(user);
        if(ret != null){
            return Result.success(ret);
        }
        return Result.fail(20002,"用户名或密码错误!");
    }

    @PostMapping("/register")
    public Result<Map<String,Object>> register(@RequestBody User user){
        Boolean status = userService.save(user);
        if(status){
            return Result.success("注册成功!");
        }
        return Result.fail(20022,"注册失败!");
    }

    @GetMapping("/info")
    public Result<Map<String,Object>> getUserInfo(@RequestParam("token") String Token){
        //根据token获取用户信息，从redis
        Map<String,Object> data = userService.getUserInfo(Token);
        if(data == null){
            return Result.fail(20003,"用户登陆信息失效，请重新登陆!");
        }
        return Result.success(data);
    }

    @PostMapping("/logout")
    public Result<Map<String,Object>> logout(@RequestHeader("X-Token") String token){
        userService.logout(token);
        return Result.success("注销登陆成功!");
    }

    @GetMapping("/list")
    public Result<Map<String,Object>> getUserList(@RequestParam(value = "username",required = false) String username,
                                                  @RequestParam(value = "phone",required = false) String phone,
                                                  @RequestParam(value = "pageNo") Long pageNo,
                                                  @RequestParam(value = "pageSize") Long pageSize){

        return userService.getUserList(username,phone,pageNo,pageSize);
    }

    @PostMapping("/add")
    public Result<?> addUser(@RequestBody User user){
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return Result.success("新增用户成功");
    }

    @PutMapping("/update")
    public Result<?> updateUser(@RequestBody User user){
//        不更新密码
        user.setPassword(null);
        userService.updateById(user);
        return Result.success("修改用户成功");
    }

    @GetMapping("/{id}")
    public Result<?> getUserByid(@PathVariable("id") Integer id){
        User user = userService.getById(id);
        return Result.success(user);
    }

    @DeleteMapping("/{id}")
    public Result<?> deleteUser(@PathVariable("id") Integer id){
        userService.removeById(id);
        return Result.success("删除用户成功！");
    }



}
