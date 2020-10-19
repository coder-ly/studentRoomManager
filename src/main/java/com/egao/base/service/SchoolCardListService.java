package com.egao.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egao.common.core.web.PageParam;
import com.egao.common.core.web.PageResult;
import com.egao.base.entity.SchoolCardList;

import java.util.List;
import java.util.Map;

/**
 * 校园卡消费表服务类
 * Created by cy on 2020-05-06 01:33:22
 */
public interface SchoolCardListService extends IService<SchoolCardList> {

    /**
     * 分页查询
     */
    PageResult<SchoolCardList> listPage(PageParam<SchoolCardList> page);

    /**
     * 查询所有
     */
    List<SchoolCardList> listAll(Map<String, Object> page);

}
