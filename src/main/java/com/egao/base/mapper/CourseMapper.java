package com.egao.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.egao.common.core.web.PageParam;
import com.egao.base.entity.Course;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 课程信息表Mapper接口
 * Created by cy on 2020-05-06 01:33:22
 */
public interface CourseMapper extends BaseMapper<Course> {

    /**
     * 分页查询
     */
    List<Course> listPage(@Param("page") PageParam<Course> page);

    /**
     * 查询全部
     */
    List<Course> listAll(@Param("page") Map<String, Object> page);

}
