package com.egao.base.controller;

import com.egao.common.core.web.*;
import com.egao.common.core.annotation.OperLog;
import com.egao.base.entity.Information;
import com.egao.base.service.InformationService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 信息发布表管理
 * Created by cy on 2020-05-06 01:33:22
 */
@Controller
@RequestMapping("/base/information")
public class InformationController extends BaseController {
    @Autowired
    private InformationService informationService;

    @RequiresPermissions("base:information:view")
    @RequestMapping()
    public String view() {
        return "base/information.html";
    }

    /**
     * 分页查询信息发布表
     */
    @RequiresPermissions("base:information:list")
    @OperLog(value = "信息发布表管理", desc = "分页查询")
    @ResponseBody
    @RequestMapping("/page")
    public PageResult<Information> page(HttpServletRequest request) {
        PageParam<Information> pageParam = new PageParam<>(request);
        pageParam.setDefaultOrder(null, new String[]{"create_time"});
        return new PageResult<>(informationService.page(pageParam, pageParam.getWrapper()).getRecords(), pageParam.getTotal());
        //return informationService.listPage(pageParam);  // 使用关联查询
    }

    /**
     * 查询全部信息发布表
     */
    @RequiresPermissions("base:information:list")
    @OperLog(value = "信息发布表管理", desc = "查询全部")
    @ResponseBody
    @RequestMapping("/list")
    public JsonResult list(HttpServletRequest request) {
        PageParam<Information> pageParam = new PageParam<>(request);
        return JsonResult.ok().setData(informationService.list(pageParam.getOrderWrapper()));
        //List<Information> records = informationService.listAll(pageParam.getNoPageParam());  // 使用关联查询
        //return JsonResult.ok().setData(pageParam.sortRecords(records));
    }

    /**
     * 根据id查询信息发布表
     */
    //@RequiresPermissions("base:information:list")
    @OperLog(value = "信息发布表管理", desc = "根据id查询")
    @ResponseBody
    @RequestMapping("/get")
    public JsonResult get(Integer id) {
        return JsonResult.ok().setData(informationService.getById(id));
        // 使用关联查询
        //PageParam<Information> pageParam = new PageParam<>();
        //pageParam.put("id", id);
        //List<Information> records = informationService.listAll(pageParam.getNoPageParam());
        //return JsonResult.ok().setData(pageParam.getOne(records));
    }

    /**
     * 添加信息发布表
     */
    @RequiresPermissions("base:information:save")
    @OperLog(value = "信息发布表管理", desc = "添加", param = false, result = true)
    @ResponseBody
    @RequestMapping("/save")
    public JsonResult save(Information information) {
        information.setUserNo(getLoginUser().getUserId() + "");
        if (getLoginUser().getEmailVerified() == 0) {
            information.setPubUser(getLoginUser().getTrueName());
        } else {
            information.setPubUser("管理员");
        }
        if (informationService.save(information)) {
            return JsonResult.ok("添加成功");
        }
        return JsonResult.error("添加失败");
    }

    /**
     * 修改信息发布表
     */
    @RequiresPermissions("base:information:update")
    @OperLog(value = "信息发布表管理", desc = "修改", param = false, result = true)
    @ResponseBody
    @RequestMapping("/update")
    public JsonResult update(Information information) {
        if (informationService.updateById(information)) {
            return JsonResult.ok("修改成功");
        }
        return JsonResult.error("修改失败");
    }

    /**
     * 删除信息发布表
     */
    @RequiresPermissions("base:information:remove")
    @OperLog(value = "信息发布表管理", desc = "删除", result = true)
    @ResponseBody
    @RequestMapping("/remove")
    public JsonResult remove(Integer id) {
        if (informationService.removeById(id)) {
            return JsonResult.ok("删除成功");
        }
        return JsonResult.error("删除失败");
    }

    /**
     * 批量添加信息发布表
     */
    @RequiresPermissions("base:information:save")
    @OperLog(value = "信息发布表管理", desc = "批量添加", param = false, result = true)
    @ResponseBody
    @RequestMapping("/saveBatch")
    public JsonResult saveBatch(@RequestBody List<Information> list) {
        if (informationService.saveBatch(list)) {
            return JsonResult.ok("添加成功");
        }
        return JsonResult.error("添加失败");
    }

    /**
     * 批量修改信息发布表
     */
    @RequiresPermissions("base:information:update")
    @OperLog(value = "信息发布表管理", desc = "批量修改", result = true)
    @ResponseBody
    @RequestMapping("/updateBatch")
    public JsonResult updateBatch(@RequestBody BatchParam<Information> batchParam) {
        if (batchParam.update(informationService, "id")) {
            return JsonResult.ok("修改成功");
        }
        return JsonResult.error("修改失败");
    }

    /**
     * 批量删除信息发布表
     */
    @RequiresPermissions("base:information:remove")
    @OperLog(value = "信息发布表管理", desc = "批量删除", result = true)
    @ResponseBody
    @RequestMapping("/removeBatch")
    public JsonResult removeBatch(@RequestBody List<Integer> ids) {
        if (informationService.removeByIds(ids)) {
            return JsonResult.ok("删除成功");
        }
        return JsonResult.error("删除失败");
    }

}
