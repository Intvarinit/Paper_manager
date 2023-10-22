package com.zhang.sys.service.impl;

import com.zhang.common.vo.Result;
import com.zhang.sys.entity.Comment;
import com.zhang.sys.mapper.CommentMapper;
import com.zhang.sys.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户评论; 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2023-06-06
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Comment> getCommentsByNoteId(int NoteId) {
        List<Comment> ret = commentMapper.getAllByNoteIDCommentList(NoteId);
        return ret;
    }

    @Override
    public Boolean publishComment(Comment comment) {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).replace(' ','T');
        comment.setCreatedTime(LocalDateTime.parse(time));
        int status = commentMapper.insert(comment);
        return status != -1;
    }

}
