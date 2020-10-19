package com.egao.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egao.common.core.web.PageParam;
import com.egao.common.core.web.PageResult;
import com.egao.base.entity.StudentRoom;

import java.util.List;
import java.util.Map;

/**
 * 学生宿舍表服务类
 * Created by cy on 2020-05-06 01:33:22
 */
public interface StudentRoomService extends IService<StudentRoom> {

    /**
     * 分页查询
     */
    PageResult<StudentRoom> listPage(PageParam<StudentRoom> page);

    /**
     * 查询所有
     */
    List<StudentRoom> listAll(Map<String, Object> page);



}
