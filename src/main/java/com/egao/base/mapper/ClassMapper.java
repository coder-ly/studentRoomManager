package com.egao.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.egao.common.core.web.PageParam;
import com.egao.base.entity.ClassA;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 班级信息Mapper接口
 * Created by cy on 2020-05-06 01:33:22
 */
public interface ClassMapper extends BaseMapper<ClassA> {

    /**
     * 分页查询
     */
    List<ClassA> listPage(@Param("page") PageParam<ClassA> page);

    /**
     * 查询全部
     */
    List<ClassA> listAll(@Param("page") Map<String, Object> page);

}
