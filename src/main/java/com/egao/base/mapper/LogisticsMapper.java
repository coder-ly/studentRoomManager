package com.egao.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.egao.common.core.web.PageParam;
import com.egao.base.entity.Logistics;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 后勤维修表Mapper接口
 * Created by cy on 2020-05-06 01:33:22
 */
public interface LogisticsMapper extends BaseMapper<Logistics> {

    /**
     * 分页查询
     */
    List<Logistics> listPage(@Param("page") PageParam<Logistics> page);

    /**
     * 查询全部
     */
    List<Logistics> listAll(@Param("page") Map<String, Object> page);

}
