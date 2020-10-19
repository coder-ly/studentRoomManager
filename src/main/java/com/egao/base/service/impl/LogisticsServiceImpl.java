package com.egao.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.egao.common.core.web.PageParam;
import com.egao.common.core.web.PageResult;
import com.egao.base.mapper.LogisticsMapper;
import com.egao.base.entity.Logistics;
import com.egao.base.service.LogisticsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 后勤维修表服务实现类
 * Created by cy on 2020-05-06 01:33:22
 */
@Service
public class LogisticsServiceImpl extends ServiceImpl<LogisticsMapper, Logistics> implements LogisticsService {

    @Override
    public PageResult<Logistics> listPage(PageParam<Logistics> page) {
        List<Logistics> records = baseMapper.listPage(page);
        return new PageResult<>(records, page.getTotal());
    }

    @Override
    public List<Logistics> listAll(Map<String, Object> page) {
        return baseMapper.listAll(page);
    }

}
