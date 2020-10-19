package com.egao.base.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.egao.base.entity.Scroom;
import com.egao.base.service.ScroomService;
import com.egao.common.core.web.*;
import com.egao.common.core.annotation.OperLog;
import com.egao.base.entity.ClassA;
import com.egao.base.service.ClassService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 班级信息管理
 * Created by cy on 2020-05-06 01:33:22
 */
@Controller
@RequestMapping("/base/class")
public class ClassController extends BaseController {
    @Autowired
    private ClassService classService;

    @Autowired
    private ScroomService scroomService;

    @RequiresPermissions("base:class:view")
    @RequestMapping()
    public String view(Model model) {
        QueryWrapper<Scroom> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        queryWrapper.eq("status", "0");
        List<Scroom> list = scroomService.list(queryWrapper);
        model.addAttribute("scrommList", list);
        return "base/class.html";
    }

    /**
     * 分页查询班级信息
     */
    @RequiresPermissions("base:class:list")
    @OperLog(value = "班级信息管理", desc = "分页查询")
    @ResponseBody
    @RequestMapping("/page")
    public PageResult<ClassA> page(HttpServletRequest request) {
        PageParam<ClassA> pageParam = new PageParam<>(request);
        //return new PageResult<>(classService.page(pageParam, pageParam.getWrapper()).getRecords(), pageParam.getTotal());
        return classService.listPage(pageParam);  // 使用关联查询
    }

    /**
     * 查询全部班级信息
     */
    @RequiresPermissions("base:class:list")
    @OperLog(value = "班级信息管理", desc = "查询全部")
    @ResponseBody
    @RequestMapping("/list")
    public JsonResult list(HttpServletRequest request) {
        PageParam<ClassA> pageParam = new PageParam<>(request);
        return JsonResult.ok().setData(classService.list(pageParam.getOrderWrapper()));
        //List<Class> records = classService.listAll(pageParam.getNoPageParam());  // 使用关联查询
        //return JsonResult.ok().setData(pageParam.sortRecords(records));
    }

    /**
     * 根据id查询班级信息
     */
    @RequiresPermissions("base:class:list")
    @OperLog(value = "班级信息管理", desc = "根据id查询")
    @ResponseBody
    @RequestMapping("/get")
    public JsonResult get(Integer id) {
        return JsonResult.ok().setData(classService.getById(id));
        // 使用关联查询
        //PageParam<Class> pageParam = new PageParam<>();
        //pageParam.put("id", id);
        //List<Class> records = classService.listAll(pageParam.getNoPageParam());
        //return JsonResult.ok().setData(pageParam.getOne(records));
    }

    /**
     * 添加班级信息
     */
    @RequiresPermissions("base:class:save")
    @OperLog(value = "班级信息管理", desc = "添加", param = false, result = true)
    @ResponseBody
    @RequestMapping("/save")
    public JsonResult save(ClassA classA) {
        if (classService.save(classA)) {
            return JsonResult.ok("添加成功");
        }
        return JsonResult.error("添加失败");
    }

    /**
     * 修改班级信息
     */
    @RequiresPermissions("base:class:update")
    @OperLog(value = "班级信息管理", desc = "修改", param = false, result = true)
    @ResponseBody
    @RequestMapping("/update")
    public JsonResult update(ClassA classA) {
        if (classService.updateById(classA)) {
            return JsonResult.ok("修改成功");
        }
        return JsonResult.error("修改失败");
    }

    /**
     * 删除班级信息
     */
    @RequiresPermissions("base:class:remove")
    @OperLog(value = "班级信息管理", desc = "删除", result = true)
    @ResponseBody
    @RequestMapping("/remove")
    public JsonResult remove(Integer id) {
        if (classService.removeById(id)) {
            return JsonResult.ok("删除成功");
        }
        return JsonResult.error("删除失败");
    }

    /**
     * 批量添加班级信息
     */
    @RequiresPermissions("base:class:save")
    @OperLog(value = "班级信息管理", desc = "批量添加", param = false, result = true)
    @ResponseBody
    @RequestMapping("/saveBatch")
    public JsonResult saveBatch(@RequestBody List<ClassA> list) {
        if (classService.saveBatch(list)) {
            return JsonResult.ok("添加成功");
        }
        return JsonResult.error("添加失败");
    }

    /**
     * 批量修改班级信息
     */
    @RequiresPermissions("base:class:update")
    @OperLog(value = "班级信息管理", desc = "批量修改", result = true)
    @ResponseBody
    @RequestMapping("/updateBatch")
    public JsonResult updateBatch(@RequestBody BatchParam<ClassA> batchParam) {
        if (batchParam.update(classService, "id")) {
            return JsonResult.ok("修改成功");
        }
        return JsonResult.error("修改失败");
    }

    /**
     * 批量删除班级信息
     */
    @RequiresPermissions("base:class:remove")
    @OperLog(value = "班级信息管理", desc = "批量删除", result = true)
    @ResponseBody
    @RequestMapping("/removeBatch")
    public JsonResult removeBatch(@RequestBody List<Integer> ids) {
        if (classService.removeByIds(ids)) {
            return JsonResult.ok("删除成功");
        }
        return JsonResult.error("删除失败");
    }


    @ResponseBody
    @RequestMapping("/updateStatus")
    public JsonResult updateStatus(@RequestParam Integer scroom, @RequestParam Integer id) {
        if (classService.updateStatus(scroom, id)) {
            return JsonResult.ok("修改成功");
        }
        return JsonResult.error("修改失败");
    }
}
