package com.egao.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.egao.base.entity.SchoolCard;
import com.egao.common.core.exception.BusinessException;
import com.egao.common.core.web.PageParam;
import com.egao.common.core.web.PageResult;
import com.egao.base.mapper.RoomMapper;
import com.egao.base.entity.Room;
import com.egao.base.service.RoomService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 宿舍信息服务实现类
 * Created by cy on 2020-05-06 01:33:22
 */
@Service
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements RoomService {

    @Override
    public PageResult<Room> listPage(PageParam<Room> page) {
        List<Room> records = baseMapper.listPage(page);
        return new PageResult<>(records, page.getTotal());
    }

    @Override
    public List<Room> listAll(Map<String, Object> page) {
        return baseMapper.listAll(page);
    }

    @Override
    public boolean save(Room entity) {
        if (baseMapper.selectCount(new QueryWrapper<Room>().eq("shno", entity.getShno()).eq("spno", entity.getSpno())) > 0) {
            throw new BusinessException("该宿舍信息已添加");
        }
        entity.setStatus("0");
        entity.setRoomNo(entity.getShno() + "栋-" + entity.getSpno());
        return baseMapper.insert(entity) > 0;
    }

    @Override
    public boolean updateById(Room entity) {
        Room room = baseMapper.selectById(entity.getId());
        if (baseMapper.selectCount(new QueryWrapper<Room>().eq("shno", entity.getShno()).eq("spno", entity.getSpno()).ne("id", entity.getId())) > 0) {
            throw new BusinessException("该宿舍信息已添加");
        }
        entity.setRoomNo(entity.getShno() + "栋-" + entity.getSpno());
        if (Integer.parseInt(entity.getSperno()) > Integer.parseInt(room.getSperno())) {
            entity.setStatus("0");
        }
        return baseMapper.updateById(entity) > 0;
    }
}
