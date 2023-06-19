package com.zhang.sys.service;

import com.zhang.common.vo.Result;
import com.zhang.sys.entity.Paper;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 论文信息表; 服务类
 * </p>
 *
 * @author baomidou
 * @since 2023-06-06
 */
public interface IPaperService extends IService<Paper> {

    Result<Map<String, Object>> getPaperList(int id,String title, String tag, Long pageNo, Long pageSize);

    Result<Map<String, Object>> getPublicPaperList(String title, Long pageNo, Long pageSize);
}
