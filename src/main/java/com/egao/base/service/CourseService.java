package com.egao.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egao.common.core.web.PageParam;
import com.egao.common.core.web.PageResult;
import com.egao.base.entity.Course;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * 课程信息表服务类
 * Created by cy on 2020-05-06 01:33:22
 */
public interface CourseService extends IService<Course> {

    /**
     * 分页查询
     */
    PageResult<Course> listPage(PageParam<Course> page);

    /**
     * 查询所有
     */
    List<Course> listAll(Map<String, Object> page);

    boolean teacherSave(Course entity);

    boolean teacherUpdate(Course entity);

    boolean updateStatus(Integer classNo,Integer id);
}
