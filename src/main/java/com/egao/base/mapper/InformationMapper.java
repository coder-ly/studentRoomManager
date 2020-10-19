package com.egao.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.egao.common.core.web.PageParam;
import com.egao.base.entity.Information;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 信息发布表Mapper接口
 * Created by cy on 2020-05-06 01:33:22
 */
public interface InformationMapper extends BaseMapper<Information> {

    /**
     * 分页查询
     */
    List<Information> listPage(@Param("page") PageParam<Information> page);

    /**
     * 查询全部
     */
    List<Information> listAll(@Param("page") Map<String, Object> page);

}
