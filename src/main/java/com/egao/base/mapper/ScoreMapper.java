package com.egao.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.egao.base.entity.Course;
import com.egao.common.core.web.PageParam;
import com.egao.base.entity.Score;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 成绩信息表Mapper接口
 * Created by cy on 2020-05-06 01:33:22
 */
public interface ScoreMapper extends BaseMapper<Score> {

    /**
     * 分页查询
     */
    List<Score> listPage(@Param("page") PageParam<Score> page);

    /**
     * 查询全部
     */
    List<Score> listAll(@Param("page") Map<String, Object> page);

    /**
     * 查询学生选课信息
     *
     * @param studentId
     * @return
     */
    List<Course> selectCourseList(@Param("studentId") Integer studentId, @Param("teacherNo") Integer teacherNo);
}
