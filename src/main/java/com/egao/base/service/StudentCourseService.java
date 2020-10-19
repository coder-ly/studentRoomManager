package com.egao.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egao.common.core.web.PageParam;
import com.egao.common.core.web.PageResult;
import com.egao.base.entity.StudentCourse;

import java.util.List;
import java.util.Map;

/**
 * 学生选课表服务类
 * Created by cy on 2020-05-06 01:33:22
 */
public interface StudentCourseService extends IService<StudentCourse> {

    /**
     * 分页查询
     */
    PageResult<StudentCourse> listPage(PageParam<StudentCourse> page);

    /**
     * 查询所有
     */
    List<StudentCourse> listAll(Map<String, Object> page);

}
