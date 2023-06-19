package com.zhang.sys.service.impl;

import com.zhang.common.vo.Result;
import com.zhang.sys.entity.Paper;
import com.zhang.sys.mapper.PaperMapper;
import com.zhang.sys.service.IPaperService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 论文信息表; 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2023-06-06
 */
@Service
public class PaperServiceImpl extends ServiceImpl<PaperMapper, Paper> implements IPaperService {

    @Autowired
    private PaperMapper paperMapper;

    @Override
    public Result<Map<String, Object>> getPaperList(int id,String title, String tag, Long pageNo, Long pageSize) {
        Map<String,Object> data = new HashMap<>();
        long offset = (pageNo-1)*pageSize;
        List<Paper> papers = paperMapper.PageSelectPaper(id,title,tag,offset,pageSize);
        Integer total = paperMapper.getTotal(id,title,tag,offset,pageSize);
        data.put("total",total);
        data.put("rows",papers);
        return Result.success(data);
    }

    @Override
    public Result<Map<String, Object>> getPublicPaperList(String title,Long pageNo, Long pageSize) {
        Map<String,Object> data = new HashMap<>();
        long offset = (pageNo-1)*pageSize;
        List<Paper> papers = paperMapper.PagePublicPaper(title,offset,pageSize);
        Integer total = paperMapper.getPublicTotal(title,offset,pageSize);
        data.put("total",total);
        data.put("rows",papers);
        return Result.success(data);
    }
}
