package com.egao.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.egao.common.core.web.PageParam;
import com.egao.base.entity.Scroom;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 教室表Mapper接口
 * Created by cy on 2020-05-06 01:33:22
 */
public interface ScroomMapper extends BaseMapper<Scroom> {

    /**
     * 分页查询
     */
    List<Scroom> listPage(@Param("page") PageParam<Scroom> page);

    /**
     * 查询全部
     */
    List<Scroom> listAll(@Param("page") Map<String, Object> page);

}
