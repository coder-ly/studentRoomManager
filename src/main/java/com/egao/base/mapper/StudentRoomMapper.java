package com.egao.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.egao.common.core.web.PageParam;
import com.egao.base.entity.StudentRoom;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 学生宿舍表Mapper接口
 * Created by cy on 2020-05-06 01:33:22
 */
public interface StudentRoomMapper extends BaseMapper<StudentRoom> {

    /**
     * 分页查询
     */
    List<StudentRoom> listPage(@Param("page") PageParam<StudentRoom> page);

    /**
     * 查询全部
     */
    List<StudentRoom> listAll(@Param("page") Map<String, Object> page);

}
