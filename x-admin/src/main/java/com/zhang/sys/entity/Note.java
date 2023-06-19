package com.zhang.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 论文笔记;
 * </p>
 *
 * @author baomidou
 * @since 2023-06-06
 */
@TableName("note")
public class Note implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 笔记的ID
     */
    @TableId(value = "NoteID")
    private Integer noteID;

    /**
     * 外键，用户ID
     */
    @TableField(value = "author")
    private Integer author;

    /**
     * 笔记内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 外键，Tag
     */
    @TableField(value = "tag")
    private Integer tag;

    /**
     * 创建时间
     */
    @TableField(value = "CREATED_TIME")
    private LocalDateTime createdTime;

    public Integer getNoteID() {
        return noteID;
    }

    public void setNoteID(Integer noteID) {
        this.noteID = noteID;
    }
    public Integer getAuthor() {
        return author;
    }

    public void setAuthor(Integer author) {
        this.author = author;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public Integer getTag() {
        return tag;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }
    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "Note{" +
            "noteID=" + noteID +
            ", author=" + author +
            ", content=" + content +
            ", tag=" + tag +
            ", createdTime=" + createdTime +
        "}";
    }
}
