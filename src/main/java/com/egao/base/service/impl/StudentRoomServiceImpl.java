package com.egao.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.egao.base.entity.Room;
import com.egao.base.mapper.RoomMapper;
import com.egao.common.core.exception.BusinessException;
import com.egao.common.core.web.PageParam;
import com.egao.common.core.web.PageResult;
import com.egao.base.mapper.StudentRoomMapper;
import com.egao.base.entity.StudentRoom;
import com.egao.base.service.StudentRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 学生宿舍表服务实现类
 * Created by cy on 2020-05-06 01:33:22
 */
@Service
public class StudentRoomServiceImpl extends ServiceImpl<StudentRoomMapper, StudentRoom> implements StudentRoomService {

    @Autowired
    private RoomMapper roomMapper;

    @Override
    public PageResult<StudentRoom> listPage(PageParam<StudentRoom> page) {
        List<StudentRoom> records = baseMapper.listPage(page);
        return new PageResult<>(records, page.getTotal());
    }

    @Override
    public List<StudentRoom> listAll(Map<String, Object> page) {
        return baseMapper.listAll(page);
    }

    @Override
    public boolean save(StudentRoom entity) {
        //查询宿舍状态
        Room newRoom = roomMapper.selectOne(new QueryWrapper<Room>().eq("id", entity.getRoomNo()));
        if ("1".equals(newRoom.getStatus())) {
            throw new BusinessException("该宿舍已经满员");
        }

        List<StudentRoom> selectList = baseMapper.selectList(new QueryWrapper<StudentRoom>().eq("student_no", entity.getStudentNo()));
        //查询是否已经分配宿舍了
        if (selectList.size() > 0) {
            //已经存在宿舍了．则删除重新添加
            baseMapper.delete(new QueryWrapper<StudentRoom>().eq("student_no", entity.getStudentNo()));
            //修改宿舍状态
            Room room = new Room();
            room.setId(Integer.parseInt(selectList.get(0).getRoomNo()));
            room.setStatus("0");
            roomMapper.updateById(room);
        }

        entity.setComments(newRoom.getRoomNo());
        //保存
        boolean result = baseMapper.insert(entity) > 0;

        //修改宿舍状态
        //查询当前宿舍人数
        Integer newCount = baseMapper.selectCount(new QueryWrapper<StudentRoom>().eq("room_no", newRoom.getId()));
        if (newCount >= Integer.parseInt(newRoom.getSperno())) {
            Room room = new Room();
            room.setId(newRoom.getId());
            room.setStatus("1");
            roomMapper.updateById(room);
        }
        return result;
    }
}
