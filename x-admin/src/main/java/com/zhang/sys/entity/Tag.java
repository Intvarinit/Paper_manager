package com.zhang.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * <p>
 * Tag数据;
 * </p>
 *
 * @author baomidou
 * @since 2023-06-06
 */
@TableName("tag")
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Tag的ID
     */
    @TableId(value = "TagID")
    private Integer tagID;

    public Tag(String tagname) {
        this.tagname = tagname;
    }

    /**
     * Tag的内容
     */

    @TableField("TagName")
    private String tagname;

    public Integer getTagID() {
        return tagID;
    }

    public void setTagID(Integer tagID) {
        this.tagID = tagID;
    }
    public String getTagname() {
        return tagname;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname;
    }

    @Override
    public String toString() {
        return "Tag{" +
            "tagID=" + tagID +
            ", tagname=" + tagname +
        "}";
    }
}
