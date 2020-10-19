package com.egao.base.controller;

import com.egao.common.core.web.*;
import com.egao.common.core.annotation.OperLog;
import com.egao.base.entity.SchoolCardList;
import com.egao.base.service.SchoolCardListService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 校园卡消费表管理
 * Created by cy on 2020-05-06 01:33:22
 */
@Controller
@RequestMapping("/base/schoolCardList")
public class SchoolCardListController extends BaseController {
    @Autowired
    private SchoolCardListService schoolCardListService;

    @RequiresPermissions("base:schoolCardList:view")
    @RequestMapping()
    public String view() {
        return "base/schoolCardList.html";
    }

    /**
     * 分页查询校园卡消费表
     */
    @RequiresPermissions("base:schoolCardList:list")
    @OperLog(value = "校园卡消费表管理", desc = "分页查询")
    @ResponseBody
    @RequestMapping("/page")
    public PageResult<SchoolCardList> page(HttpServletRequest request) {
        PageParam<SchoolCardList> pageParam = new PageParam<>(request);
        return new PageResult<>(schoolCardListService.page(pageParam, pageParam.getWrapper()).getRecords(), pageParam.getTotal());
        //return schoolCardListService.listPage(pageParam);  // 使用关联查询
    }

    /**
     * 查询全部校园卡消费表
     */
    @RequiresPermissions("base:schoolCardList:list")
    @OperLog(value = "校园卡消费表管理", desc = "查询全部")
    @ResponseBody
    @RequestMapping("/list")
    public JsonResult list(HttpServletRequest request) {
        PageParam<SchoolCardList> pageParam = new PageParam<>(request);
        return JsonResult.ok().setData(schoolCardListService.list(pageParam.getOrderWrapper()));
        //List<SchoolCardList> records = schoolCardListService.listAll(pageParam.getNoPageParam());  // 使用关联查询
        //return JsonResult.ok().setData(pageParam.sortRecords(records));
    }

    /**
     * 根据id查询校园卡消费表
     */
    @RequiresPermissions("base:schoolCardList:list")
    @OperLog(value = "校园卡消费表管理", desc = "根据id查询")
    @ResponseBody
    @RequestMapping("/get")
    public JsonResult get(Integer id) {
        return JsonResult.ok().setData(schoolCardListService.getById(id));
		// 使用关联查询
        //PageParam<SchoolCardList> pageParam = new PageParam<>();
		//pageParam.put("id", id);
        //List<SchoolCardList> records = schoolCardListService.listAll(pageParam.getNoPageParam());
        //return JsonResult.ok().setData(pageParam.getOne(records));
    }

    /**
     * 添加校园卡消费表
     */
    @RequiresPermissions("base:schoolCardList:save")
    @OperLog(value = "校园卡消费表管理", desc = "添加", param = false, result = true)
    @ResponseBody
    @RequestMapping("/save")
    public JsonResult save(SchoolCardList schoolCardList) {
        if (schoolCardListService.save(schoolCardList)) {
            return JsonResult.ok("添加成功");
        }
        return JsonResult.error("添加失败");
    }

    /**
     * 修改校园卡消费表
     */
    @RequiresPermissions("base:schoolCardList:update")
    @OperLog(value = "校园卡消费表管理", desc = "修改", param = false, result = true)
    @ResponseBody
    @RequestMapping("/update")
    public JsonResult update(SchoolCardList schoolCardList) {
        if (schoolCardListService.updateById(schoolCardList)) {
            return JsonResult.ok("修改成功");
        }
        return JsonResult.error("修改失败");
    }

    /**
     * 删除校园卡消费表
     */
    @RequiresPermissions("base:schoolCardList:remove")
    @OperLog(value = "校园卡消费表管理", desc = "删除", result = true)
    @ResponseBody
    @RequestMapping("/remove")
    public JsonResult remove(Integer id) {
        if (schoolCardListService.removeById(id)) {
            return JsonResult.ok("删除成功");
        }
        return JsonResult.error("删除失败");
    }

    /**
     * 批量添加校园卡消费表
     */
    @RequiresPermissions("base:schoolCardList:save")
    @OperLog(value = "校园卡消费表管理", desc = "批量添加", param = false, result = true)
    @ResponseBody
    @RequestMapping("/saveBatch")
    public JsonResult saveBatch(@RequestBody List<SchoolCardList> list) {
        if (schoolCardListService.saveBatch(list)) {
            return JsonResult.ok("添加成功");
        }
        return JsonResult.error("添加失败");
    }

    /**
     * 批量修改校园卡消费表
     */
    @RequiresPermissions("base:schoolCardList:update")
    @OperLog(value = "校园卡消费表管理", desc = "批量修改", result = true)
    @ResponseBody
    @RequestMapping("/updateBatch")
    public JsonResult updateBatch(@RequestBody BatchParam<SchoolCardList> batchParam) {
        if (batchParam.update(schoolCardListService, "id")) {
            return JsonResult.ok("修改成功");
        }
        return JsonResult.error("修改失败");
    }

    /**
     * 批量删除校园卡消费表
     */
    @RequiresPermissions("base:schoolCardList:remove")
    @OperLog(value = "校园卡消费表管理", desc = "批量删除", result = true)
    @ResponseBody
    @RequestMapping("/removeBatch")
    public JsonResult removeBatch(@RequestBody List<Integer> ids) {
        if (schoolCardListService.removeByIds(ids)) {
            return JsonResult.ok("删除成功");
        }
        return JsonResult.error("删除失败");
    }

}
