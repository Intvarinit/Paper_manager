package com.zhang.sys.service;

import com.zhang.common.vo.Result;
import com.zhang.sys.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * Tag数据; 服务类
 * </p>
 *
 * @author baomidou
 * @since 2023-06-06
 */
public interface ITagService extends IService<Tag> {

    Result<Map<String,Object>> getTagIdByName(String tagName);

    Result<Integer> countPaperById(int parseInt);
}
