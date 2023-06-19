package com.zhang.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户评论;
 * </p>
 *
 * @author baomidou
 * @since 2023-06-06
 */
@TableName("comment")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 评论ID
     */
    @TableId(value = "CommentID")
    private Integer commentID;

    /**
     * 外键，笔记ID
     */
    private Integer noteID;

    /**
     * 父评论ID
     */
    private Integer fatherID;

    /**
     * 评论者
     */
    private Integer commenter;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 点赞数
     */
    private Integer likes;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    public Integer getCommentID() {
        return commentID;
    }

    public void setCommentID(Integer commentID) {
        this.commentID = commentID;
    }
    public Integer getNoteID() {
        return noteID;
    }

    public void setNoteID(Integer noteID) {
        this.noteID = noteID;
    }
    public Integer getFatherID() {
        return fatherID;
    }

    public void setFatherID(Integer fatherID) {
        this.fatherID = fatherID;
    }
    public Integer getCommenter() {
        return commenter;
    }

    public void setCommenter(Integer commenter) {
        this.commenter = commenter;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }
    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "Comment{" +
            "commentID=" + commentID +
            ", noteID=" + noteID +
            ", fatherID=" + fatherID +
            ", commenter=" + commenter +
            ", content=" + content +
            ", likes=" + likes +
            ", createdTime=" + createdTime +
        "}";
    }
}
