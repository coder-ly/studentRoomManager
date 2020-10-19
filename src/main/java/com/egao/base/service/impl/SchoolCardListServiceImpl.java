package com.egao.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.egao.common.core.web.PageParam;
import com.egao.common.core.web.PageResult;
import com.egao.base.mapper.SchoolCardListMapper;
import com.egao.base.entity.SchoolCardList;
import com.egao.base.service.SchoolCardListService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 校园卡消费表服务实现类
 * Created by cy on 2020-05-06 01:33:22
 */
@Service
public class SchoolCardListServiceImpl extends ServiceImpl<SchoolCardListMapper, SchoolCardList> implements SchoolCardListService {

    @Override
    public PageResult<SchoolCardList> listPage(PageParam<SchoolCardList> page) {
        List<SchoolCardList> records = baseMapper.listPage(page);
        return new PageResult<>(records, page.getTotal());
    }

    @Override
    public List<SchoolCardList> listAll(Map<String, Object> page) {
        return baseMapper.listAll(page);
    }

}
