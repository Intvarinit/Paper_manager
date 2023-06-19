package com.zhang.sys.mapper;

import com.zhang.sys.entity.Note;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 论文笔记; Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2023-06-06
 */
public interface NoteMapper extends BaseMapper<Note> {
    @Select("select auto_increment from information_schema.tables where table_schema='paper_manager' and table_name='note'")
    Integer getCurrentID();
}
