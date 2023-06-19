package com.zhang.sys.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 论文信息表;
 * </p>
 *
 * @author baomidou
 * @since 2023-06-06
 */
@TableName("paper")
public class Paper implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 论文ID
     */
    @TableId(value = "PaperID", type = IdType.AUTO)
    private Integer paperID;

    /**
     * 论文发布者ID
     */
    @TableField(value = "publishID")
    private Integer PublishID;

    /**
     * 论文标签
     */
    private Integer tag;

    /**
     * 论文标题
     */
    private String title;

    /**
     * 论文概要
     */
    private String summary;

    /**
     * 论文笔记
     */
    @TableField(value = "noteID")
    private Integer noteID;

    /**
     * 论文网址
     */
    @TableField(value = "paperUrl")
    private String paperUrl;

    /**
     * 论文参考文献样式
     */
    @TableField(value = "paperRef")
    private String paperRef;

    public Integer getPublic() {
        return Public;
    }

    public void setPublic(Integer aPublic) {
        Public = aPublic;
    }

    /**
     * 是否公开
     */
    @TableField(value = "public")
    private Integer Public;

    /**
     * 创建时间
     */
    @TableField(value = "CREATED_TIME", update = "now()")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    @TableField(value = "UPDATED_TIME", update = "now()")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedTime;

    public Integer getPaperID() {
        return paperID;
    }

    public void setPaperID(Integer paperID) {
        this.paperID = paperID;
    }
    public Integer getTag() {
        return tag;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
    public Integer getNoteID() {
        return noteID;
    }

    public void setNoteID(Integer noteID) {
        this.noteID = noteID;
    }
    public String getPaperUrl() {
        return paperUrl;
    }

    public void setPaperUrl(String paperUrl) {
        this.paperUrl = paperUrl;
    }
    public String getPaperRef() {
        return paperRef;
    }

    public void setPaperRef(String paperRef) {
        this.paperRef = paperRef;
    }
    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }
    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }

    @Override
    public String toString() {
        return "Paper{" +
            "paperID=" + paperID +
            ", PublishID" + PublishID +
            ", tag=" + tag +
            ", title=" + title +
            ", summary=" + summary +
            ", noteID=" + noteID +
            ", paperUrl=" + paperUrl +
            ", paperRef=" + paperRef +
            ", createdTime=" + createdTime +
            ", updatedTime=" + updatedTime +
        "}";
    }

    public Integer getPublishID() {
        return PublishID;
    }

    public void setPublishID(Integer publishID) {
        PublishID = publishID;
    }
}
