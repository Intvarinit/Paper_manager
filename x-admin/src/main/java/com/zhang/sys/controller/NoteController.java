package com.zhang.sys.controller;

import com.zhang.common.vo.Result;
import com.zhang.sys.entity.Note;
import com.zhang.sys.entity.Paper;
import com.zhang.sys.entity.Tag;
import com.zhang.sys.entity.User;
import com.zhang.sys.mapper.NoteMapper;
import com.zhang.sys.service.impl.NoteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * <p>
 * 论文笔记; 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2023-06-06
 */
@CrossOrigin
@RestController
@RequestMapping("/note")
public class NoteController {
    @Autowired
    private NoteServiceImpl noteService;

    @GetMapping("/{id}")
    public Result<Note> getNoteById(@PathVariable("id") String id){
        Note data = noteService.getById(Integer.parseInt(id));
        return Result.success(data);
    }

    @PostMapping("/new")
    public Result<?> addUser(@RequestBody Paper paperInfo){
        int id = noteService.getCurrentID();
        Note note = new Note();
        note.setNoteID(id);
        note.setAuthor(paperInfo.getPublishID());
        note.setTag(paperInfo.getTag());
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).replace(' ','T');
        note.setCreatedTime(LocalDateTime.parse(time));
        noteService.save(note);
        return Result.success(id,"新增笔记成功!");
    }

    @PutMapping("/update")
    public Result<?> updateUser(@RequestBody Note note){
        noteService.updateById(note);
        return Result.success("修改笔记成功!");
    }

}
