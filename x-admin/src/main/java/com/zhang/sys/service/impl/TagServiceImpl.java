package com.zhang.sys.service.impl;

import com.zhang.common.vo.Result;
import com.zhang.sys.entity.Tag;
import com.zhang.sys.mapper.TagMapper;
import com.zhang.sys.service.ITagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Tag数据; 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2023-06-06
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {
    @Autowired
    private TagMapper tagMapper;

    @Override
    public Result<Map<String,Object>> getTagIdByName(String tagName) {      //在查询的ID对应不上时可以将其存入数据库
        Map<String,Object> data = new HashMap<>();
        if(tagName == ""){
            return Result.fail("不存在对应数据!");
        }
        Tag tag = tagMapper.getTagIdByName(tagName);
        if(tag == null){    //数据库存入对应数据
            this.save(new Tag(tagName));
        }
//        返回修改后的ID
        Tag new_tag = tagMapper.getTagIdByName(tagName);
        data.put("id",new_tag.getTagID());
        return Result.success(data);
    }

    @Override
    public Result<Integer> countPaperById(int parseInt) {
        int count = Integer.parseInt(tagMapper.countPaperByTag(parseInt));
        return Result.success(count);
    }

}
