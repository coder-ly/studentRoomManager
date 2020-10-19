package com.egao.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.egao.common.core.web.PageParam;
import com.egao.base.entity.SchoolCard;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 校园卡表Mapper接口
 * Created by cy on 2020-05-06 01:33:22
 */
public interface SchoolCardMapper extends BaseMapper<SchoolCard> {

    /**
     * 分页查询
     */
    List<SchoolCard> listPage(@Param("page") PageParam<SchoolCard> page);

    /**
     * 查询全部
     */
    List<SchoolCard> listAll(@Param("page") Map<String, Object> page);

}
