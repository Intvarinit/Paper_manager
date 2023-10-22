package com.zhang.sys.service;

import com.zhang.common.vo.Result;
import com.zhang.sys.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户评论; 服务类
 * </p>
 *
 * @author baomidou
 * @since 2023-06-06
 */
public interface ICommentService extends IService<Comment> {

    List<Comment> getCommentsByNoteId(int NoteId);

    Boolean publishComment(Comment comment);
}
