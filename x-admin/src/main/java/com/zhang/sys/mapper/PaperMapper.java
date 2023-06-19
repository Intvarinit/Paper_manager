package com.zhang.sys.mapper;

import com.zhang.sys.entity.Paper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhang.sys.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 论文信息表; Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2023-06-06
 */
public interface PaperMapper extends BaseMapper<Paper> {

//    select p.paperid,t.tagname,p.title,p.summary,p.noteid,p.paperurl,p.paperref from paper p,tag t where p.tag=t.tagid limit 0, 2;
    @Select("<script>" + "select p.* from paper p left join tag t on p.tag = t.tagID"
            + "<if test='title != null'>"
            + " where p.title like concat('%',concat(#{title},'%'))"
            + "</if>"
            + "<if test='tagName != null'>"
            +   "<if test='title != null'>"
            +       "and"
            +   "</if>"
            +   "<if test='title == null'>"
            +       "where"
            +   "</if>"
            +   " t.tagName like concat('%',concat(#{tagName},'%'))"
            + "</if>"
            + "<if test='id != null'>"
            +   "<if test='title == null &amp;&amp; tagName == null'>"
            +       "where "
            +   "</if>"
            +   "<if test='title != null || tagName != null'>"
            +       "and "
            +   "</if>"
            +   "p.publishid = #{id}"
            + "</if>"
            + " limit #{offset}, #{size}"
            + "</script>")
    List<Paper> PageSelectPaper(@Param("id")int id,
                               @Param("title")String title,
                              @Param("tagName")String tag,
                              @Param("offset")long offset,
                              @Param("size")long PageSize);

    @Select("<script>" + "select count(*) from paper p left join tag t on p.tag = t.tagID"
            + "<if test='title != null'>"
            + " where p.title like concat('%',concat(#{title},'%'))"
            + "</if>"
            + "<if test='tagName != null'>"
            +   "<if test='title != null'>"
            +       "and"
            +   "</if>"
            +   "<if test='title == null'>"
            +       "where"
            +   "</if>"
            +   " t.tagName like concat('%',concat(#{tagName},'%'))"
            + "</if>"
            + "<if test='id != null'>"
            +   "<if test='title == null &amp;&amp; tagName == null'>"
            +       "where "
            +   "</if>"
            +   "<if test='title != null || tagName != null'>"
            +       "and "
            +   "</if>"
            +   "p.publishid = #{id}"
            + "</if>"
            + " limit #{offset}, #{size}"
            + "</script>")
    Integer getTotal(@Param("id")int id,
                     @Param("title")String title,
                     @Param("tagName")String tag,
                     @Param("offset")long offset,
                     @Param("size")long PageSize);

    @Select("<script> select * from paper where public=1 and noteID is not null"
            + "<if test='title != null'>"
            +   "and title like concat('%',concat(#{title},'%'))"
            + "</if>"
            + " limit #{offset},#{size}"
            + "</script>")
    List<Paper> PagePublicPaper(@Param("title")String title,
                                @Param("offset")long offset,
                                @Param("size")long PageSize);

    @Select("<script> select count(*) from paper where public=1 and noteID is not null "
            + "<if test='title != null'>"
            +   "and title like concat('%',concat(#{title},'%'))"
            + "</if>"
            + " limit #{offset},#{size}"
            + "</script>")
    Integer getPublicTotal(@Param("title")String title,
                                @Param("offset")long offset,
                                @Param("size")long PageSize);
}
