package com.zhang.sys.service.impl;

import com.zhang.sys.entity.Note;
import com.zhang.sys.mapper.NoteMapper;
import com.zhang.sys.service.INoteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 论文笔记; 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2023-06-06
 */
@Service
public class NoteServiceImpl extends ServiceImpl<NoteMapper, Note> implements INoteService {

    @Autowired
    private  NoteMapper noteMapper;

    @Override
    public int getCurrentID() {
        return noteMapper.getCurrentID();
    }
}
