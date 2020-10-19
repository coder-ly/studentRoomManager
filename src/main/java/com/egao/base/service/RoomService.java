package com.egao.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egao.common.core.web.PageParam;
import com.egao.common.core.web.PageResult;
import com.egao.base.entity.Room;

import java.util.List;
import java.util.Map;

/**
 * 宿舍信息服务类
 * Created by cy on 2020-05-06 01:33:22
 */
public interface RoomService extends IService<Room> {

    /**
     * 分页查询
     */
    PageResult<Room> listPage(PageParam<Room> page);

    /**
     * 查询所有
     */
    List<Room> listAll(Map<String, Object> page);

}
