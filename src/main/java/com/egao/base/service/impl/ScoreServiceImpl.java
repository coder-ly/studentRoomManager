package com.egao.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.egao.base.entity.*;
import com.egao.common.core.exception.BusinessException;
import com.egao.common.core.web.PageParam;
import com.egao.common.core.web.PageResult;
import com.egao.base.mapper.ScoreMapper;
import com.egao.base.service.ScoreService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 成绩信息表服务实现类
 * Created by cy on 2020-05-06 01:33:22
 */
@Service
public class ScoreServiceImpl extends ServiceImpl<ScoreMapper, Score> implements ScoreService {

    @Override
    public PageResult<Score> listPage(PageParam<Score> page) {
        List<Score> records = baseMapper.listPage(page);
        return new PageResult<>(records, page.getTotal());
    }

    @Override
    public List<Score> listAll(Map<String, Object> page) {
        return baseMapper.listAll(page);
    }

    @Override
    public List<Course> selectCourseList(Integer studentId, Integer teacherNo) {
        return baseMapper.selectCourseList(studentId, teacherNo);
    }

    @Override
    public boolean save(Score entity) {
        if (baseMapper.selectCount(new QueryWrapper<Score>().eq("course_no", entity.getCourseNo()).eq("student_no", entity.getStudentNo()).eq("trem", entity.getTrem())) > 0) {
            throw new BusinessException("该成绩信息已添加！");
        }
        return baseMapper.insert(entity) > 0;
    }
}
