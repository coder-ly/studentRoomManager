package com.egao.base.controller;

import com.egao.common.core.web.*;
import com.egao.common.core.annotation.OperLog;
import com.egao.base.entity.Scroom;
import com.egao.base.service.ScroomService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 教室表管理
 * Created by cy on 2020-05-06 01:33:22
 */
@Controller
@RequestMapping("/base/scroom")
public class ScroomController extends BaseController {
    @Autowired
    private ScroomService scroomService;

    @RequiresPermissions("base:scroom:view")
    @RequestMapping()
    public String view() {
        return "base/scroom.html";
    }

    /**
     * 分页查询教室表
     */
    @RequiresPermissions("base:scroom:list")
    @OperLog(value = "教室表管理", desc = "分页查询")
    @ResponseBody
    @RequestMapping("/page")
    public PageResult<Scroom> page(HttpServletRequest request) {
        PageParam<Scroom> pageParam = new PageParam<>(request);
        pageParam.setDefaultOrder(null, new String[]{"create_time"});
        return new PageResult<>(scroomService.page(pageParam, pageParam.getWrapper()).getRecords(), pageParam.getTotal());
        //return scroomService.listPage(pageParam);  // 使用关联查询
    }

    /**
     * 查询全部教室表
     */
    @RequiresPermissions("base:scroom:list")
    @OperLog(value = "教室表管理", desc = "查询全部")
    @ResponseBody
    @RequestMapping("/list")
    public JsonResult list(HttpServletRequest request) {
        PageParam<Scroom> pageParam = new PageParam<>(request);
        return JsonResult.ok().setData(scroomService.list(pageParam.getOrderWrapper()));
        //List<Scroom> records = scroomService.listAll(pageParam.getNoPageParam());  // 使用关联查询
        //return JsonResult.ok().setData(pageParam.sortRecords(records));
    }

    /**
     * 根据id查询教室表
     */
    @RequiresPermissions("base:scroom:list")
    @OperLog(value = "教室表管理", desc = "根据id查询")
    @ResponseBody
    @RequestMapping("/get")
    public JsonResult get(Integer id) {
        return JsonResult.ok().setData(scroomService.getById(id));
        // 使用关联查询
        //PageParam<Scroom> pageParam = new PageParam<>();
        //pageParam.put("id", id);
        //List<Scroom> records = scroomService.listAll(pageParam.getNoPageParam());
        //return JsonResult.ok().setData(pageParam.getOne(records));
    }

    /**
     * 添加教室表
     */
    @RequiresPermissions("base:scroom:save")
    @OperLog(value = "教室表管理", desc = "添加", param = false, result = true)
    @ResponseBody
    @RequestMapping("/save")
    public JsonResult save(Scroom scroom) {
        //设置空闲状态
        scroom.setStatus("0");
        scroom.setComments(scroom.getFloor() + "栋－" + scroom.getHouseNumber());
        if (scroomService.save(scroom)) {
            return JsonResult.ok("添加成功");
        }
        return JsonResult.error("添加失败");
    }

    /**
     * 修改教室表
     */
    @RequiresPermissions("base:scroom:update")
    @OperLog(value = "教室表管理", desc = "修改", param = false, result = true)
    @ResponseBody
    @RequestMapping("/update")
    public JsonResult update(Scroom scroom) {
        scroom.setComments(scroom.getFloor() + "栋－" + scroom.getHouseNumber());
        if (scroomService.updateById(scroom)) {
            return JsonResult.ok("修改成功");
        }
        return JsonResult.error("修改失败");
    }

    /**
     * 删除教室表
     */
    @RequiresPermissions("base:scroom:remove")
    @OperLog(value = "教室表管理", desc = "删除", result = true)
    @ResponseBody
    @RequestMapping("/remove")
    public JsonResult remove(Integer id) {
        if (scroomService.removeById(id)) {
            return JsonResult.ok("删除成功");
        }
        return JsonResult.error("删除失败");
    }

    /**
     * 批量添加教室表
     */
    @RequiresPermissions("base:scroom:save")
    @OperLog(value = "教室表管理", desc = "批量添加", param = false, result = true)
    @ResponseBody
    @RequestMapping("/saveBatch")
    public JsonResult saveBatch(@RequestBody List<Scroom> list) {
        if (scroomService.saveBatch(list)) {
            return JsonResult.ok("添加成功");
        }
        return JsonResult.error("添加失败");
    }

    /**
     * 批量修改教室表
     */
    @RequiresPermissions("base:scroom:update")
    @OperLog(value = "教室表管理", desc = "批量修改", result = true)
    @ResponseBody
    @RequestMapping("/updateBatch")
    public JsonResult updateBatch(@RequestBody BatchParam<Scroom> batchParam) {
        if (batchParam.update(scroomService, "id")) {
            return JsonResult.ok("修改成功");
        }
        return JsonResult.error("修改失败");
    }

    /**
     * 批量删除教室表
     */
    @RequiresPermissions("base:scroom:remove")
    @OperLog(value = "教室表管理", desc = "批量删除", result = true)
    @ResponseBody
    @RequestMapping("/removeBatch")
    public JsonResult removeBatch(@RequestBody List<Integer> ids) {
        if (scroomService.removeByIds(ids)) {
            return JsonResult.ok("删除成功");
        }
        return JsonResult.error("删除失败");
    }

}
