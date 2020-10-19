package com.egao.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.egao.base.entity.*;
import com.egao.base.mapper.ScroomMapper;
import com.egao.base.service.ScroomService;
import com.egao.common.core.web.PageParam;
import com.egao.common.core.web.PageResult;
import com.egao.base.mapper.CourseMapper;
import com.egao.base.service.CourseService;
import com.egao.common.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 课程信息表服务实现类
 * Created by cy on 2020-05-06 01:33:22
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
    @Autowired
    private UserService userService;

    @Autowired
    private ScroomService scroomService;

    @Autowired
    private ScroomMapper scroomMapper;

    @Override
    public PageResult<Course> listPage(PageParam<Course> page) {
        List<Course> records = baseMapper.listPage(page);
        return new PageResult<>(records, page.getTotal());
    }

    @Override
    public List<Course> listAll(Map<String, Object> page) {
        return baseMapper.listAll(page);
    }

    @Override
    public boolean save(Course entity) {
        entity.setTeacherName(userService.getById(entity.getTeacherNo()).getTrueName());
        entity.setClassName(scroomService.getById(entity.getClassNo()).getComments());
        //将选择的教室修改为使用
        updateScroom(entity.getClassNo(), "1");
        return baseMapper.insert(entity) > 0;
    }

    @Override
    public boolean updateById(Course entity) {
        entity.setTeacherName(userService.getById(entity.getTeacherNo()).getTrueName());
        entity.setClassName(scroomService.getById(entity.getClassNo()).getComments());
        //将原教室修改为空闲状态
        Course obj = baseMapper.selectById(entity.getId());
        updateScroom(obj.getClassNo(), "0");
        //将现在使用的教室
        updateScroom(entity.getClassNo(), "1");
        return baseMapper.updateById(entity) > 0;
    }

    @Override
    public boolean teacherSave(Course entity) {
        entity.setClassName(scroomService.getById(entity.getClassNo()).getComments());
        updateScroom(entity.getClassNo(), "1");
        return baseMapper.insert(entity) > 0;
    }

    @Override
    public boolean teacherUpdate(Course entity) {
        entity.setClassName(scroomService.getById(entity.getClassNo()).getComments());
        //将原教室修改为空闲状态
        Course obj = baseMapper.selectById(entity.getId());
        updateScroom(obj.getClassNo(), "0");
        //将现在使用的教室
        updateScroom(entity.getClassNo(), "1");
        return baseMapper.updateById(entity) > 0;
    }

    /**
     * 修改教室使用状态
     *
     * @param scroomId
     */
    private void updateScroom(Integer scroomId, String status) {
        Scroom scroom = new Scroom();
        scroom.setId(scroomId);
        scroom.setStatus(status);
        scroomMapper.updateById(scroom);
    }

    @Override
    public boolean updateStatus(Integer classNo, Integer id) {
        updateScroom(classNo, "0");
        Course course = new Course();
        course.setClassName(" ");
        course.setClassNo(0);
        course.setId(id);
        return baseMapper.updateById(course) > 0;
    }
}
