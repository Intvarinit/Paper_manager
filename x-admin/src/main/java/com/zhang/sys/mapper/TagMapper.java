package com.zhang.sys.mapper;

import com.zhang.sys.entity.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * Tag数据; Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2023-06-06
 */
public interface TagMapper extends BaseMapper<Tag> {
    @Select("select * from tag where TagName=#{tagName}")
    Tag getTagIdByName(@Param("tagName") String tagName);

    @Select("select tagname from tag where TagID=#{tagID}")
    String getTagNameById(@Param("tagID") int tagID);

    @Select("select count(*) from paper where tag=#{tagID}")
    String countPaperByTag(@Param("tagID") int tagID);
}
