package com.zhang.sys.service;

import com.zhang.sys.entity.Note;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 论文笔记; 服务类
 * </p>
 *
 * @author baomidou
 * @since 2023-06-06
 */
public interface INoteService extends IService<Note> {

    int getCurrentID();
}
