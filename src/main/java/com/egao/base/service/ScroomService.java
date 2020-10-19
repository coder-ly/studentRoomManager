package com.egao.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egao.common.core.web.PageParam;
import com.egao.common.core.web.PageResult;
import com.egao.base.entity.Scroom;

import java.util.List;
import java.util.Map;

/**
 * 教室表服务类
 * Created by cy on 2020-05-06 01:33:22
 */
public interface ScroomService extends IService<Scroom> {

    /**
     * 分页查询
     */
    PageResult<Scroom> listPage(PageParam<Scroom> page);

    /**
     * 查询所有
     */
    List<Scroom> listAll(Map<String, Object> page);

}
