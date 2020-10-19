package com.egao.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egao.common.core.web.PageParam;
import com.egao.common.core.web.PageResult;
import com.egao.base.entity.Information;

import java.util.List;
import java.util.Map;

/**
 * 信息发布表服务类
 * Created by cy on 2020-05-06 01:33:22
 */
public interface InformationService extends IService<Information> {

    /**
     * 分页查询
     */
    PageResult<Information> listPage(PageParam<Information> page);

    /**
     * 查询所有
     */
    List<Information> listAll(Map<String, Object> page);

}
