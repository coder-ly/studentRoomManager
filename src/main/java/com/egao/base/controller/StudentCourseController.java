package com.egao.base.controller;

import com.egao.common.core.web.*;
import com.egao.common.core.annotation.OperLog;
import com.egao.base.entity.StudentCourse;
import com.egao.base.service.StudentCourseService;
import com.egao.common.system.entity.User;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 学生选课表管理
 * Created by cy on 2020-05-06 01:33:22
 */
@Controller
@RequestMapping("/base/studentCourse")
public class StudentCourseController extends BaseController {
    @Autowired
    private StudentCourseService studentCourseService;

    @RequiresPermissions("base:studentCourse:view")
    @RequestMapping()
    public String view() {
        return "base/studentCourse.html";
    }

    /**
     * 分页查询学生选课表
     */
    @RequiresPermissions("base:studentCourse:list")
    @OperLog(value = "学生选课表管理", desc = "分页查询")
    @ResponseBody
    @RequestMapping("/page")
    public PageResult<StudentCourse> page(HttpServletRequest request) {
        PageParam<StudentCourse> pageParam = new PageParam<>(request);
        User loginUser = getLoginUser();
        if (1 == loginUser.getEmailVerified()) {
            pageParam.put("studentNo", getLoginUserId());
        }
        //return new PageResult<>(studentCourseService.page(pageParam, pageParam.getWrapper()).getRecords(), pageParam.getTotal());
        return studentCourseService.listPage(pageParam);  // 使用关联查询
    }

    /**
     * 查询全部学生选课表
     */
    @RequiresPermissions("base:studentCourse:list")
    @OperLog(value = "学生选课表管理", desc = "查询全部")
    @ResponseBody
    @RequestMapping("/list")
    public JsonResult list(HttpServletRequest request) {
        PageParam<StudentCourse> pageParam = new PageParam<>(request);
        return JsonResult.ok().setData(studentCourseService.list(pageParam.getOrderWrapper()));
        //List<StudentCourse> records = studentCourseService.listAll(pageParam.getNoPageParam());  // 使用关联查询
        //return JsonResult.ok().setData(pageParam.sortRecords(records));
    }

    /**
     * 根据id查询学生选课表
     */
    @RequiresPermissions("base:studentCourse:list")
    @OperLog(value = "学生选课表管理", desc = "根据id查询")
    @ResponseBody
    @RequestMapping("/get")
    public JsonResult get(Integer id) {
        return JsonResult.ok().setData(studentCourseService.getById(id));
        // 使用关联查询
        //PageParam<StudentCourse> pageParam = new PageParam<>();
        //pageParam.put("id", id);
        //List<StudentCourse> records = studentCourseService.listAll(pageParam.getNoPageParam());
        //return JsonResult.ok().setData(pageParam.getOne(records));
    }

    /**
     * 添加学生选课表
     */
    @RequiresPermissions("base:studentCourse:save")
    @OperLog(value = "学生选课表管理", desc = "添加", param = false, result = true)
    @ResponseBody
    @RequestMapping("/save")
    public JsonResult save(StudentCourse studentCourse) {
        if (studentCourseService.save(studentCourse)) {
            return JsonResult.ok("添加成功");
        }
        return JsonResult.error("添加失败");
    }

    /**
     * 修改学生选课表
     */
    @RequiresPermissions("base:studentCourse:update")
    @OperLog(value = "学生选课表管理", desc = "修改", param = false, result = true)
    @ResponseBody
    @RequestMapping("/update")
    public JsonResult update(StudentCourse studentCourse) {
        if (studentCourseService.updateById(studentCourse)) {
            return JsonResult.ok("修改成功");
        }
        return JsonResult.error("修改失败");
    }

    /**
     * 删除学生选课表
     */
    @RequiresPermissions("base:studentCourse:remove")
    @OperLog(value = "学生选课表管理", desc = "删除", result = true)
    @ResponseBody
    @RequestMapping("/remove")
    public JsonResult remove(Integer id) {
        if (studentCourseService.removeById(id)) {
            return JsonResult.ok("删除成功");
        }
        return JsonResult.error("删除失败");
    }

    /**
     * 批量添加学生选课表
     */
    @RequiresPermissions("base:studentCourse:save")
    @OperLog(value = "学生选课表管理", desc = "批量添加", param = false, result = true)
    @ResponseBody
    @RequestMapping("/saveBatch")
    public JsonResult saveBatch(@RequestBody List<StudentCourse> list) {
        if (studentCourseService.saveBatch(list)) {
            return JsonResult.ok("添加成功");
        }
        return JsonResult.error("添加失败");
    }

    /**
     * 批量修改学生选课表
     */
    @RequiresPermissions("base:studentCourse:update")
    @OperLog(value = "学生选课表管理", desc = "批量修改", result = true)
    @ResponseBody
    @RequestMapping("/updateBatch")
    public JsonResult updateBatch(@RequestBody BatchParam<StudentCourse> batchParam) {
        if (batchParam.update(studentCourseService, "id")) {
            return JsonResult.ok("修改成功");
        }
        return JsonResult.error("修改失败");
    }

    /**
     * 批量删除学生选课表
     */
    @RequiresPermissions("base:studentCourse:remove")
    @OperLog(value = "学生选课表管理", desc = "批量删除", result = true)
    @ResponseBody
    @RequestMapping("/removeBatch")
    public JsonResult removeBatch(@RequestBody List<Integer> ids) {
        if (studentCourseService.removeByIds(ids)) {
            return JsonResult.ok("删除成功");
        }
        return JsonResult.error("删除失败");
    }

}
