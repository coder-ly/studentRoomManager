package com.egao.student.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.egao.base.entity.*;
import com.egao.base.service.*;
import com.egao.common.core.annotation.OperLog;
import com.egao.common.core.web.*;
import com.egao.common.system.entity.User;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 后勤维修表管理
 * Created by cy on 2020-05-06 01:33:22
 */
@Controller
@RequestMapping("/student/logistics")
public class StudentLogisticsController extends BaseController {
    @Autowired
    private LogisticsService logisticsService;

    @Autowired
    private StudentRoomService studentRoomService;

    @Autowired
    private RoomService roomService;

    @RequiresPermissions("student:logistics:view")
    @RequestMapping()
    public String view() {
        return "student/logistics.html";
    }

    /**
     * 分页查询后勤维修表
     */
    @RequiresPermissions("student:logistics:list")
    @OperLog(value = "后勤维修表管理", desc = "分页查询")
    @ResponseBody
    @RequestMapping("/page")
    public PageResult<Logistics> page(HttpServletRequest request) {
        PageParam<Logistics> pageParam = new PageParam<>(request);
        List<Logistics> list = logisticsService.page(pageParam, pageParam.getWrapper().eq("student_no", getLoginUser().getIdCard())).getRecords();

        list.forEach(ele -> {
            Room room = roomService.getById(ele.getRoomNo());
            if (room != null) {
                ele.setRoomName(room.getRoomNo());
            }
        });

        return new PageResult<>(list, pageParam.getTotal());
    }

    /**
     * 添加后勤维修表
     */
    @RequiresPermissions("student:logistics:save")
    @OperLog(value = "后勤维修表管理", desc = "添加", param = false, result = true)
    @ResponseBody
    @RequestMapping("/save")
    public JsonResult save(Logistics logistics) {
        User loginUser = getLoginUser();
        //查询宿舍编号
        StudentRoom studentRoom = studentRoomService.getOne(new QueryWrapper<StudentRoom>().eq("student_no", loginUser.getIdCard()));
        if (studentRoom == null) {
            return JsonResult.error("还未分配宿舍，请联系管理员或者教师分配宿舍");
        }
        logistics.setStudentNo(loginUser.getIdCard());
        logistics.setStatus("0");
        logistics.setRoomNo(Integer.parseInt(studentRoom.getRoomNo()));
        if (logisticsService.save(logistics)) {
            return JsonResult.ok("添加成功");
        }
        return JsonResult.error("添加失败");
    }

    /**
     * 修改后勤维修表
     */
    @RequiresPermissions("student:logistics:update")
    @OperLog(value = "后勤维修表管理", desc = "修改", param = false, result = true)
    @ResponseBody
    @RequestMapping("/update")
    public JsonResult update(Logistics logistics) {
        if (logisticsService.updateById(logistics)) {
            return JsonResult.ok("修改成功");
        }
        return JsonResult.error("修改失败");
    }

    /**
     * 删除后勤维修表
     */
    @RequiresPermissions("student:logistics:remove")
    @OperLog(value = "后勤维修表管理", desc = "删除", result = true)
    @ResponseBody
    @RequestMapping("/remove")
    public JsonResult remove(Integer id) {
        if (logisticsService.removeById(id)) {
            return JsonResult.ok("删除成功");
        }
        return JsonResult.error("删除失败");
    }

    /**
     * 批量删除后勤维修表
     */
    @RequiresPermissions("student:logistics:remove")
    @OperLog(value = "后勤维修表管理", desc = "批量删除", result = true)
    @ResponseBody
    @RequestMapping("/removeBatch")
    public JsonResult removeBatch(@RequestBody List<Integer> ids) {
        if (logisticsService.removeByIds(ids)) {
            return JsonResult.ok("删除成功");
        }
        return JsonResult.error("删除失败");
    }

}
