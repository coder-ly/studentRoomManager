package com.egao.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.egao.common.core.web.PageParam;
import com.egao.base.entity.StudentCourse;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 学生选课表Mapper接口
 * Created by cy on 2020-05-06 01:33:22
 */
public interface StudentCourseMapper extends BaseMapper<StudentCourse> {

    /**
     * 分页查询
     */
    List<StudentCourse> listPage(@Param("page") PageParam<StudentCourse> page);

    /**
     * 查询全部
     */
    List<StudentCourse> listAll(@Param("page") Map<String, Object> page);

}
