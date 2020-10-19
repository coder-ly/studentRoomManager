package com.egao.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.egao.common.core.web.PageParam;
import com.egao.base.entity.SchoolCardList;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 校园卡消费表Mapper接口
 * Created by cy on 2020-05-06 01:33:22
 */
public interface SchoolCardListMapper extends BaseMapper<SchoolCardList> {

    /**
     * 分页查询
     */
    List<SchoolCardList> listPage(@Param("page") PageParam<SchoolCardList> page);

    /**
     * 查询全部
     */
    List<SchoolCardList> listAll(@Param("page") Map<String, Object> page);

}
