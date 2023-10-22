package com.zhang.sys.mapper;

import com.zhang.sys.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 用户评论; Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2023-06-06
 */
public interface CommentMapper extends BaseMapper<Comment> {
    @Select("select * from comment c where c.NoteID = #{NoteID}")
    List<Comment> getAllByNoteIDCommentList(@Param("NoteID") int NoteID);
}
