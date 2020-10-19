package com.egao.base.controller;

import com.egao.common.core.web.*;
import com.egao.common.core.annotation.OperLog;
import com.egao.base.entity.Logistics;
import com.egao.base.service.LogisticsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 后勤维修表管理
 * Created by cy on 2020-05-06 01:33:22
 */
@Controller
@RequestMapping("/base/logistics")
public class LogisticsController extends BaseController {
    @Autowired
    private LogisticsService logisticsService;

    @RequiresPermissions("base:logistics:view")
    @RequestMapping()
    public String view() {
        return "base/logistics.html";
    }

    /**
     * 分页查询后勤维修表
     */
    @RequiresPermissions("base:logistics:list")
    @OperLog(value = "后勤维修表管理", desc = "分页查询")
    @ResponseBody
    @RequestMapping("/page")
    public PageResult<Logistics> page(HttpServletRequest request) {
        PageParam<Logistics> pageParam = new PageParam<>(request);
        //return new PageResult<>(logisticsService.page(pageParam, pageParam.getWrapper()).getRecords(), pageParam.getTotal());
        return logisticsService.listPage(pageParam);  // 使用关联查询
    }

    /**
     * 查询全部后勤维修表
     */
    @RequiresPermissions("base:logistics:list")
    @OperLog(value = "后勤维修表管理", desc = "查询全部")
    @ResponseBody
    @RequestMapping("/list")
    public JsonResult list(HttpServletRequest request) {
        PageParam<Logistics> pageParam = new PageParam<>(request);
        return JsonResult.ok().setData(logisticsService.list(pageParam.getOrderWrapper()));
        //List<Logistics> records = logisticsService.listAll(pageParam.getNoPageParam());  // 使用关联查询
        //return JsonResult.ok().setData(pageParam.sortRecords(records));
    }

    /**
     * 根据id查询后勤维修表
     */
    @RequiresPermissions("base:logistics:list")
    @OperLog(value = "后勤维修表管理", desc = "根据id查询")
    @ResponseBody
    @RequestMapping("/get")
    public JsonResult get(Integer id) {
        return JsonResult.ok().setData(logisticsService.getById(id));
        // 使用关联查询
        //PageParam<Logistics> pageParam = new PageParam<>();
        //pageParam.put("id", id);
        //List<Logistics> records = logisticsService.listAll(pageParam.getNoPageParam());
        //return JsonResult.ok().setData(pageParam.getOne(records));
    }

    /**
     * 添加后勤维修表
     */
    @RequiresPermissions("base:logistics:save")
    @OperLog(value = "后勤维修表管理", desc = "添加", param = false, result = true)
    @ResponseBody
    @RequestMapping("/save")
    public JsonResult save(Logistics logistics) {
        if (logisticsService.save(logistics)) {
            return JsonResult.ok("添加成功");
        }
        return JsonResult.error("添加失败");
    }

    /**
     * 修改后勤维修表
     */
    @RequiresPermissions("base:logistics:update")
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
    @RequiresPermissions("base:logistics:remove")
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
     * 批量添加后勤维修表
     */
    @RequiresPermissions("base:logistics:save")
    @OperLog(value = "后勤维修表管理", desc = "批量添加", param = false, result = true)
    @ResponseBody
    @RequestMapping("/saveBatch")
    public JsonResult saveBatch(@RequestBody List<Logistics> list) {
        if (logisticsService.saveBatch(list)) {
            return JsonResult.ok("添加成功");
        }
        return JsonResult.error("添加失败");
    }

    /**
     * 批量修改后勤维修表
     */
    @RequiresPermissions("base:logistics:update")
    @OperLog(value = "后勤维修表管理", desc = "批量修改", result = true)
    @ResponseBody
    @RequestMapping("/updateBatch")
    public JsonResult updateBatch(@RequestBody BatchParam<Logistics> batchParam) {
        if (batchParam.update(logisticsService, "id")) {
            return JsonResult.ok("修改成功");
        }
        return JsonResult.error("修改失败");
    }

    /**
     * 批量删除后勤维修表
     */
    @RequiresPermissions("base:logistics:remove")
    @OperLog(value = "后勤维修表管理", desc = "批量删除", result = true)
    @ResponseBody
    @RequestMapping("/removeBatch")
    public JsonResult removeBatch(@RequestBody List<Integer> ids) {
        if (logisticsService.removeByIds(ids)) {
            return JsonResult.ok("删除成功");
        }
        return JsonResult.error("删除失败");
    }

    /**
     * 修改状态
     **/
    @ResponseBody
    @OperLog(value = "后勤维修管理", desc = "修改状态", result = true)
    @RequestMapping("/updateStatus")
    public JsonResult updateStatus(Logistics logistics) {
        if (logisticsService.updateById(logistics)) {
            return JsonResult.ok("操作成功");
        }
        return JsonResult.error("操作失败");
    }

}
