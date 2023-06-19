package com.zhang.sys.controller;

import com.zhang.common.vo.Result;
import com.zhang.sys.entity.Paper;
import com.zhang.sys.entity.User;
import com.zhang.sys.service.IPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import javax.print.attribute.standard.JobKOctets;
import java.util.Map;

/**
 * <p>
 * 论文信息表; 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2023-06-06
 */
@CrossOrigin
@RestController
@RequestMapping("/paper")
public class PaperController {
    @Autowired
    private IPaperService paperService;

    @GetMapping("/list")
    public Result<Map<String,Object>> getPaperList(@RequestParam(value = "id") int id,
                                                   @RequestParam(value = "Title",required = false) String title,
                                                   @RequestParam(value = "Tag",required = false) String tag,
                                                   @RequestParam(value = "pageNo") Long pageNo,
                                                   @RequestParam(value = "pageSize") Long pageSize){

        return paperService.getPaperList(id,title,tag,pageNo,pageSize);
    }

    @GetMapping("/public")
    public Result<Map<String,Object>> getPublicPaperList(@RequestParam(value = "Title",required = false) String title,
                                                   @RequestParam(value = "pageNo") Long pageNo,
                                                   @RequestParam(value = "pageSize") Long pageSize){

        return paperService.getPublicPaperList(title,pageNo,pageSize);
    }

    @PostMapping("/add")
    public Result<?> addUser(@RequestBody Paper paper){
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println(paper);
        paperService.save(paper);
        return Result.success("新增论文信息成功");
    }

    @PutMapping("/update")
    public Result<?> updateUser(@RequestBody Paper paper){
//        不更新密码
        paperService.updateById(paper);
        return Result.success("修改论文信息成功");
    }

    @GetMapping("/{id}")
    public Result<?> getPaperByid(@PathVariable("id") Integer id){
        Paper paper = paperService.getById(id);
        return Result.success(paper);
    }

    @DeleteMapping("/{id}")
    public Result<?> deletePaper(@PathVariable("id") Integer id){
        paperService.removeById(id);
        return Result.success("删除论文信息成功！");
    }
}
