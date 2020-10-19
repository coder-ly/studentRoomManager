package com.egao.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egao.common.core.web.PageParam;
import com.egao.common.core.web.PageResult;
import com.egao.base.entity.ClassA;

import java.util.List;
import java.util.Map;

/**
 * 班级信息服务类
 * Created by cy on 2020-05-06 01:33:22
 */
public interface ClassService extends IService<ClassA> {

    /**
     * 分页查询
     */
    PageResult<ClassA> listPage(PageParam<ClassA> page);

    /**
     * 查询所有
     */
    List<ClassA> listAll(Map<String, Object> page);

    boolean updateStatus(Integer scroom, Integer id);
}
