package com.egao.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.egao.common.core.web.PageParam;
import com.egao.common.core.web.PageResult;
import com.egao.base.mapper.StudentCourseMapper;
import com.egao.base.entity.StudentCourse;
import com.egao.base.service.StudentCourseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 学生选课表服务实现类
 * Created by cy on 2020-05-06 01:33:22
 */
@Service
public class StudentCourseServiceImpl extends ServiceImpl<StudentCourseMapper, StudentCourse> implements StudentCourseService {

    @Override
    public PageResult<StudentCourse> listPage(PageParam<StudentCourse> page) {
        List<StudentCourse> records = baseMapper.listPage(page);
        return new PageResult<>(records, page.getTotal());
    }

    @Override
    public List<StudentCourse> listAll(Map<String, Object> page) {
        return baseMapper.listAll(page);
    }

}
