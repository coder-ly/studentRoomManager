package com.egao.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.egao.common.core.web.PageParam;
import com.egao.common.core.web.PageResult;
import com.egao.base.mapper.InformationMapper;
import com.egao.base.entity.Information;
import com.egao.base.service.InformationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 信息发布表服务实现类
 * Created by cy on 2020-05-06 01:33:22
 */
@Service
public class InformationServiceImpl extends ServiceImpl<InformationMapper, Information> implements InformationService {

    @Override
    public PageResult<Information> listPage(PageParam<Information> page) {
        List<Information> records = baseMapper.listPage(page);
        return new PageResult<>(records, page.getTotal());
    }

    @Override
    public List<Information> listAll(Map<String, Object> page) {
        return baseMapper.listAll(page);
    }

}
