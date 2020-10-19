package com.egao.student.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.egao.base.entity.Room;
import com.egao.base.entity.StudentRoom;
import com.egao.base.service.RoomService;
import com.egao.base.service.StudentRoomService;
import com.egao.common.core.annotation.OperLog;
import com.egao.common.core.web.*;
import com.egao.common.system.entity.User;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 宿舍信息管理
 * Created by cy on 2020-05-06 01:33:22
 */
@Controller
@RequestMapping("/student/room")
public class StudentRoomViewController extends BaseController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private StudentRoomService studentRoomService;

    @RequiresPermissions("student:room:view")
    @RequestMapping()
    public String view() {
        return "student/room.html";
    }

    /**
     * 分页查询宿舍信息
     */
    @RequiresPermissions("student:room:list")
    @OperLog(value = "宿舍信息管理", desc = "分页查询")
    @ResponseBody
    @RequestMapping("/page")
    public PageResult<Room> page(HttpServletRequest request) {
        PageParam<Room> pageParam = new PageParam<>(request);
        User loginUser = getLoginUser();
        //根据学号查询宿舍
        StudentRoom studentRoom = studentRoomService.getOne(new QueryWrapper<StudentRoom>().eq("student_no", loginUser.getIdCard()));
        String roomNo = studentRoom == null ? "" : studentRoom.getRoomNo();
        return new PageResult<>(roomService.page(pageParam, pageParam.getWrapper().eq("id", roomNo)).getRecords(), pageParam.getTotal());
    }
}
