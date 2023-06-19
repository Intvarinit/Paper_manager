package com.zhang.sys.service.impl;

import com.zhang.sys.entity.Comment;
import com.zhang.sys.mapper.CommentMapper;
import com.zhang.sys.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
