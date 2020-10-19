package com.egao.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egao.base.entity.*;
import com.egao.common.core.web.PageParam;
import com.egao.common.core.web.PageResult;

import java.util.List;
import java.util.Map;

/**
 * 成绩信息表服务类
 * Created by cy on 2020-05-06 01:33:22
 */
public interface ScoreService extends IService<Score> {

    /**
     * 分页查询
     */
    PageResult<Score> listPage(PageParam<Score> page);

    /**
     * 查询所有
     */
    List<Score> listAll(Map<String, Object> page);

    /**
     * 查询学生的选课信息
     *
     * @param studentId
     * @return
     */
    List<Course> selectCourseList(Integer studentId,Integer teacherNo);
}
