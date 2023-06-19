package com.zhang.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhang.common.vo.Result;
import com.zhang.sys.entity.Tag;
import com.zhang.sys.service.impl.TagServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Tag数据; 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2023-06-06
 */
@CrossOrigin
@RestController
@RequestMapping("/tag")
public class TagController {
    @Autowired
    private TagServiceImpl tagService;

    @GetMapping("/{id}")
    public Result<Map<String,Object>> getTagNameById(@PathVariable("id") String tagId){
        Tag tag = tagService.getById(Integer.parseInt(tagId));
        Map<String ,Object> data = new HashMap<>();
        data.put("data",tag.getTagname());
        return Result.success(data);
    }

    @PutMapping("/update")
    public Result<?> updateUser(@RequestBody Tag tag){
        tagService.updateById(tag);
        return Result.success("修改tag成功");
    }

    @PostMapping("/{tagName}")
    public Result<Map<String,Object>> getTagIdByName(@PathVariable("tagName") String tagName){
        return tagService.getTagIdByName(tagName);
    }

    @GetMapping("/list")
    public Result<List<Tag>> getAllTag() {
        List<Tag> data = tagService.list();
        return Result.success(data);
    }

    @PostMapping("/add")
    public Result<?> addTag(@RequestBody Tag tag){
        tagService.save(tag);
        return Result.success("设置标签成功!");
    }

    @GetMapping("/count/{id}")
    public Result<Integer> countPaperByTag(@PathVariable("id") String tagID){
        return tagService.countPaperById(Integer.parseInt(tagID));
    }

//    删除失效tag的功能
    @DeleteMapping("/{tagId}")
    public Result<?> deleteTagByName(@PathVariable("tagId") String tagId){
        try {
            tagService.removeById(Integer.parseInt(tagId));
        }catch (EmptyResultDataAccessException e){

        }
        return Result.success("删除tag信息成功");
    }
 }
