package com.egao.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.egao.base.entity.Room;
import com.egao.common.core.exception.BusinessException;
import com.egao.common.core.web.PageParam;
import com.egao.common.core.web.PageResult;
import com.egao.base.mapper.ScroomMapper;
import com.egao.base.entity.Scroom;
import com.egao.base.service.ScroomService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 教室表服务实现类
 * Created by cy on 2020-05-06 01:33:22
 */
@Service
public class ScroomServiceImpl extends ServiceImpl<ScroomMapper, Scroom> implements ScroomService {

    @Override
    public PageResult<Scroom> listPage(PageParam<Scroom> page) {
        List<Scroom> records = baseMapper.listPage(page);
        return new PageResult<>(records, page.getTotal());
    }

    @Override
    public List<Scroom> listAll(Map<String, Object> page) {
        return baseMapper.listAll(page);
    }

    @Override
    public boolean save(Scroom entity) {
        if (baseMapper.selectCount(new QueryWrapper<Scroom>().eq("floor", entity.getFloor()).eq("house_number", entity.getHouseNumber())) > 0) {
            throw new BusinessException("该教室信息已添加");
        }
        return baseMapper.insert(entity) > 0;
    }

    @Override
    public boolean updateById(Scroom entity) {
        if (baseMapper.selectCount(new QueryWrapper<Scroom>().eq("floor", entity.getFloor()).eq("house_number", entity.getHouseNumber()).ne("id", entity.getId())) > 0) {
            throw new BusinessException("该教室信息已存在");
        }
        return baseMapper.updateById(entity) > 0;
    }
}
