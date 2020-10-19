package com.egao.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egao.common.core.web.PageParam;
import com.egao.common.core.web.PageResult;
import com.egao.base.entity.Logistics;

import java.util.List;
import java.util.Map;

/**
 * 后勤维修表服务类
 * Created by cy on 2020-05-06 01:33:22
 */
public interface LogisticsService extends IService<Logistics> {

    /**
     * 分页查询
     */
    PageResult<Logistics> listPage(PageParam<Logistics> page);

    /**
     * 查询所有
     */
    List<Logistics> listAll(Map<String, Object> page);

}
