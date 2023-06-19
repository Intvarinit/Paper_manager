package com.zhang.sys.mapper;

import com.zhang.sys.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 用户表; Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2023-06-06
 */
public interface UserMapper extends BaseMapper<User> {

    @Select("<script>" + "select * from user"
            + "<if test='username != null'>"
            + "where username like concat('%',concat(#{username},'%'))"
            + "</if>"
            + "<if test='phone != null'>"
            + "<if test='username == null'>"
            +       "where"
            +  "</if>"
            + "<if test='username != null'>"
            +       "and"
            +  "</if>"
            + " phone like concat('%',concat(#{phone},'%'))"
            + "</if>"
            + "limit #{offset}, #{size}"
            + "</script>")
    List<User> PageSelectUser(@Param("username")String username,
                              @Param("phone")String phone,
                              @Param("offset")long offset,
                              @Param("size")long PageSize);

}
