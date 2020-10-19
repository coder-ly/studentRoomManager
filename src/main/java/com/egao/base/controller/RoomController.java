package com.egao.base.controller;

import com.egao.common.core.web.*;
import com.egao.common.core.annotation.OperLog;
import com.egao.base.entity.Room;
import com.egao.base.service.RoomService;
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
@RequestMapping("/base/room")
public class RoomController extends BaseController {
    @Autowired
    private RoomService roomService;

    @RequiresPermissions("base:room:view")
    @RequestMapping()
    public String view() {
        return "base/room.html";
    }

    /**
     * 分页查询宿舍信息
     */
    @RequiresPermissions("base:room:list")
    @OperLog(value = "宿舍信息管理", desc = "分页查询")
    @ResponseBody
    @RequestMapping("/page")
    public PageResult<Room> page(HttpServletRequest request) {
        PageParam<Room> pageParam = new PageParam<>(request);
        pageParam.setDefaultOrder(null, new String[]{"create_time"});
        return new PageResult<>(roomService.page(pageParam, pageParam.getWrapper()).getRecords(), pageParam.getTotal());
        //return roomService.listPage(pageParam);  // 使用关联查询
    }

    /**
     * 查询全部宿舍信息
     */
    @RequiresPermissions("base:room:list")
    @OperLog(value = "宿舍信息管理", desc = "查询全部")
    @ResponseBody
    @RequestMapping("/list")
    public JsonResult list(HttpServletRequest request) {
        PageParam<Room> pageParam = new PageParam<>(request);
        return JsonResult.ok().setData(roomService.list(pageParam.getOrderWrapper()));
        //List<Room> records = roomService.listAll(pageParam.getNoPageParam());  // 使用关联查询
        //return JsonResult.ok().setData(pageParam.sortRecords(records));
    }

    /**
     * 根据id查询宿舍信息
     */
    @RequiresPermissions("base:room:list")
    @OperLog(value = "宿舍信息管理", desc = "根据id查询")
    @ResponseBody
    @RequestMapping("/get")
    public JsonResult get(Integer id) {
        return JsonResult.ok().setData(roomService.getById(id));
		// 使用关联查询
        //PageParam<Room> pageParam = new PageParam<>();
		//pageParam.put("id", id);
        //List<Room> records = roomService.listAll(pageParam.getNoPageParam());
        //return JsonResult.ok().setData(pageParam.getOne(records));
    }

    /**
     * 添加宿舍信息
     */
    @RequiresPermissions("base:room:save")
    @OperLog(value = "宿舍信息管理", desc = "添加", param = false, result = true)
    @ResponseBody
    @RequestMapping("/save")
    public JsonResult save(Room room) {
        if (roomService.save(room)) {
            return JsonResult.ok("添加成功");
        }
        return JsonResult.error("添加失败");
    }

    /**
     * 修改宿舍信息
     */
    @RequiresPermissions("base:room:update")
    @OperLog(value = "宿舍信息管理", desc = "修改", param = false, result = true)
    @ResponseBody
    @RequestMapping("/update")
    public JsonResult update(Room room) {
        if (roomService.updateById(room)) {
            return JsonResult.ok("修改成功");
        }
        return JsonResult.error("修改失败");
    }

    /**
     * 删除宿舍信息
     */
    @RequiresPermissions("base:room:remove")
    @OperLog(value = "宿舍信息管理", desc = "删除", result = true)
    @ResponseBody
    @RequestMapping("/remove")
    public JsonResult remove(Integer id) {
        if (roomService.removeById(id)) {
            return JsonResult.ok("删除成功");
        }
        return JsonResult.error("删除失败");
    }

    /**
     * 批量添加宿舍信息
     */
    @RequiresPermissions("base:room:save")
    @OperLog(value = "宿舍信息管理", desc = "批量添加", param = false, result = true)
    @ResponseBody
    @RequestMapping("/saveBatch")
    public JsonResult saveBatch(@RequestBody List<Room> list) {
        if (roomService.saveBatch(list)) {
            return JsonResult.ok("添加成功");
        }
        return JsonResult.error("添加失败");
    }

    /**
     * 批量修改宿舍信息
     */
    @RequiresPermissions("base:room:update")
    @OperLog(value = "宿舍信息管理", desc = "批量修改", result = true)
    @ResponseBody
    @RequestMapping("/updateBatch")
    public JsonResult updateBatch(@RequestBody BatchParam<Room> batchParam) {
        if (batchParam.update(roomService, "id")) {
            return JsonResult.ok("修改成功");
        }
        return JsonResult.error("修改失败");
    }

    /**
     * 批量删除宿舍信息
     */
    @RequiresPermissions("base:room:remove")
    @OperLog(value = "宿舍信息管理", desc = "批量删除", result = true)
    @ResponseBody
    @RequestMapping("/removeBatch")
    public JsonResult removeBatch(@RequestBody List<Integer> ids) {
        if (roomService.removeByIds(ids)) {
            return JsonResult.ok("删除成功");
        }
        return JsonResult.error("删除失败");
    }

}
