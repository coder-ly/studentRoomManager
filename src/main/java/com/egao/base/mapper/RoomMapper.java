package com.egao.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.egao.common.core.web.PageParam;
import com.egao.base.entity.Room;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 宿舍信息Mapper接口
 * Created by cy on 2020-05-06 01:33:22
 */
public interface RoomMapper extends BaseMapper<Room> {

    /**
     * 分页查询
     */
    List<Room> listPage(@Param("page") PageParam<Room> page);

    /**
     * 查询全部
     */
    List<Room> listAll(@Param("page") Map<String, Object> page);

}
