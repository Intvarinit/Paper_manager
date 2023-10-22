package com.zhang.sys.controller;

import com.zhang.common.vo.Result;
import com.zhang.sys.entity.Comment;
import com.zhang.sys.service.ICommentService;
import com.zhang.sys.service.impl.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.awt.image.IndexColorModel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户评论; 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2023-06-06
 */
@CrossOrigin
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private ICommentService commentService;

    @GetMapping("/{id}")
    public Result<Map<String,Object>> getCommentByNoteID(@PathVariable("id") String NoteId){
        List<Comment> commentList= commentService.getCommentsByNoteId(Integer.parseInt(NoteId));
        Map<String,Object> data = new HashMap<>();
        data.put("total",commentList.size());
        data.put("data",commentList);
        return Result.success(data);
    }

    @PostMapping("/publish")
    public Result<?> PublishComment(@RequestBody Comment comment){
        Boolean ret = commentService.publishComment(comment);
        if(!ret){
            return Result.fail("发布评论失败");
        }
        return Result.success(null,"发布评论成功");
    }
}
