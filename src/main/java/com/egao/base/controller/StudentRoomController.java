package com.egao.base.controller;

import com.egao.common.core.web.*;
import com.egao.common.core.annotation.OperLog;
import com.egao.base.entity.StudentRoom;
import com.egao.base.service.StudentRoomService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 学生宿舍表管理
 * Created by cy on 2020-05-06 01:33:22
 */
@Controller
@RequestMapping("/base/studentRoom")
public class StudentRoomController extends BaseController {
    @Autowired
    private StudentRoomService studentRoomService;

    @RequiresPermissions("base:studentRoom:view")
    @RequestMapping()
    public String view() {
        return "base/studentRoom.html";
    }

    /**
     * 分页查询学生宿舍表
     */
    @RequiresPermissions("base:studentRoom:list")
    @OperLog(value = "学生宿舍表管理", desc = "分页查询")
    @ResponseBody
    @RequestMapping("/page")
    public PageResult<StudentRoom> page(HttpServletRequest request) {
        PageParam<StudentRoom> pageParam = new PageParam<>(request);
        return new PageResult<>(studentRoomService.page(pageParam, pageParam.getWrapper()).getRecords(), pageParam.getTotal());
        //return studentRoomService.listPage(pageParam);  // 使用关联查询
    }

    /**
     * 查询全部学生宿舍表
     */
    @RequiresPermissions("base:studentRoom:list")
    @OperLog(value = "学生宿舍表管理", desc = "查询全部")
    @ResponseBody
    @RequestMapping("/list")
    public JsonResult list(HttpServletRequest request) {
        PageParam<StudentRoom> pageParam = new PageParam<>(request);
        return JsonResult.ok().setData(studentRoomService.list(pageParam.getOrderWrapper()));
        //List<StudentRoom> records = studentRoomService.listAll(pageParam.getNoPageParam());  // 使用关联查询
        //return JsonResult.ok().setData(pageParam.sortRecords(records));
    }

    /**
     * 根据id查询学生宿舍表
     */
    @RequiresPermissions("base:studentRoom:list")
    @OperLog(value = "学生宿舍表管理", desc = "根据id查询")
    @ResponseBody
    @RequestMapping("/get")
    public JsonResult get(Integer id) {
        return JsonResult.ok().setData(studentRoomService.getById(id));
		// 使用关联查询
        //PageParam<StudentRoom> pageParam = new PageParam<>();
		//pageParam.put("id", id);
        //List<StudentRoom> records = studentRoomService.listAll(pageParam.getNoPageParam());
        //return JsonResult.ok().setData(pageParam.getOne(records));
    }

    /**
     * 添加学生宿舍表
     */
    @RequiresPermissions("base:studentRoom:save")
    @OperLog(value = "学生宿舍表管理", desc = "添加", param = false, result = true)
    @ResponseBody
    @RequestMapping("/save")
    public JsonResult save(StudentRoom studentRoom) {
        if (studentRoomService.save(studentRoom)) {
            return JsonResult.ok("添加成功");
        }
        return JsonResult.error("添加失败");
    }

    /**
     * 修改学生宿舍表
     */
    @RequiresPermissions("base:studentRoom:update")
    @OperLog(value = "学生宿舍表管理", desc = "修改", param = false, result = true)
    @ResponseBody
    @RequestMapping("/update")
    public JsonResult update(StudentRoom studentRoom) {
        if (studentRoomService.updateById(studentRoom)) {
            return JsonResult.ok("修改成功");
        }
        return JsonResult.error("修改失败");
    }

    /**
     * 删除学生宿舍表
     */
    @RequiresPermissions("base:studentRoom:remove")
    @OperLog(value = "学生宿舍表管理", desc = "删除", result = true)
    @ResponseBody
    @RequestMapping("/remove")
    public JsonResult remove(Integer id) {
        if (studentRoomService.removeById(id)) {
            return JsonResult.ok("删除成功");
        }
        return JsonResult.error("删除失败");
    }

    /**
     * 批量添加学生宿舍表
     */
    @RequiresPermissions("base:studentRoom:save")
    @OperLog(value = "学生宿舍表管理", desc = "批量添加", param = false, result = true)
    @ResponseBody
    @RequestMapping("/saveBatch")
    public JsonResult saveBatch(@RequestBody List<StudentRoom> list) {
        if (studentRoomService.saveBatch(list)) {
            return JsonResult.ok("添加成功");
        }
        return JsonResult.error("添加失败");
    }

    /**
     * 批量修改学生宿舍表
     */
    @RequiresPermissions("base:studentRoom:update")
    @OperLog(value = "学生宿舍表管理", desc = "批量修改", result = true)
    @ResponseBody
    @RequestMapping("/updateBatch")
    public JsonResult updateBatch(@RequestBody BatchParam<StudentRoom> batchParam) {
        if (batchParam.update(studentRoomService, "id")) {
            return JsonResult.ok("修改成功");
        }
        return JsonResult.error("修改失败");
    }

    /**
     * 批量删除学生宿舍表
     */
    @RequiresPermissions("base:studentRoom:remove")
    @OperLog(value = "学生宿舍表管理", desc = "批量删除", result = true)
    @ResponseBody
    @RequestMapping("/removeBatch")
    public JsonResult removeBatch(@RequestBody List<Integer> ids) {
        if (studentRoomService.removeByIds(ids)) {
            return JsonResult.ok("删除成功");
        }
        return JsonResult.error("删除失败");
    }

}
