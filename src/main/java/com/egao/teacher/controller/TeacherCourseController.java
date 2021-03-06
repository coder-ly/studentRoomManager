package com.egao.teacher.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.egao.base.entity.Course;
import com.egao.base.entity.Scroom;
import com.egao.base.service.CourseService;
import com.egao.base.service.ScroomService;
import com.egao.common.core.annotation.OperLog;
import com.egao.common.core.web.*;
import com.egao.common.system.entity.User;
import com.egao.common.system.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 课程信息表管理
 * Created by cy on 2020-05-06 01:33:22
 */
@Controller
@RequestMapping("/teacher/course")
public class TeacherCourseController extends BaseController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private ScroomService scroomService;

    @RequiresPermissions("teacher:course:view")
    @RequestMapping()
    public String view(Model model) {
        List<Scroom> scroomList = scroomService.list(new QueryWrapper<Scroom>().eq("status", "0"));
        model.addAttribute("scroomList", scroomList);
        return "teacher/course.html";
    }

    /**
     * 分页查询课程信息表
     */
    @RequiresPermissions("teacher:course:list")
    @OperLog(value = "课程信息表管理", desc = "分页查询")
    @ResponseBody
    @RequestMapping("/page")
    public PageResult<Course> page(HttpServletRequest request) {
        PageParam<Course> pageParam = new PageParam<>(request);
        List<Course> list = courseService.page(pageParam, pageParam.getWrapper().eq("teacher_no", getLoginUserId())).getRecords();
        return new PageResult<>(list, pageParam.getTotal());
        //return courseService.listPage(pageParam);  // 使用关联查询
    }

    /**
     * 查询全部课程信息表
     */
    @RequiresPermissions("teacher:course:list")
    @OperLog(value = "课程信息表管理", desc = "查询全部")
    @ResponseBody
    @RequestMapping("/list")
    public JsonResult list(HttpServletRequest request) {
        PageParam<Course> pageParam = new PageParam<>(request);
        return JsonResult.ok().setData(courseService.list(pageParam.getOrderWrapper()));
        //List<Course> records = courseService.listAll(pageParam.getNoPageParam());  // 使用关联查询
        //return JsonResult.ok().setData(pageParam.sortRecords(records));
    }

    /**
     * 根据id查询课程信息表
     */
    @RequiresPermissions("teacher:course:list")
    @OperLog(value = "课程信息表管理", desc = "根据id查询")
    @ResponseBody
    @RequestMapping("/get")
    public JsonResult get(Integer id) {
        return JsonResult.ok().setData(courseService.getById(id));
        // 使用关联查询
        //PageParam<Course> pageParam = new PageParam<>();
        //pageParam.put("id", id);
        //List<Course> records = courseService.listAll(pageParam.getNoPageParam());
        //return JsonResult.ok().setData(pageParam.getOne(records));
    }

    /**
     * 添加课程信息表
     */
    @RequiresPermissions("teacher:course:save")
    @OperLog(value = "课程信息表管理", desc = "添加", param = false, result = true)
    @ResponseBody
    @RequestMapping("/save")
    public JsonResult save(Course course) {
        course.setTeacherNo(getLoginUserId());
        course.setTeacherName(getLoginUser().getTrueName());
        if (courseService.teacherSave(course)) {
            return JsonResult.ok("添加成功");
        }
        return JsonResult.error("添加失败");
    }

    /**
     * 修改课程信息表
     */
    @RequiresPermissions("teacher:course:update")
    @OperLog(value = "课程信息表管理", desc = "修改", param = false, result = true)
    @ResponseBody
    @RequestMapping("/update")
    public JsonResult update(Course course) {
        course.setTeacherNo(getLoginUserId());
        course.setTeacherName(getLoginUser().getTrueName());
        if (courseService.teacherUpdate(course)) {
            return JsonResult.ok("修改成功");
        }
        return JsonResult.error("修改失败");
    }

    /**
     * 删除课程信息表
     */
    @RequiresPermissions("teacher:course:remove")
    @OperLog(value = "课程信息表管理", desc = "删除", result = true)
    @ResponseBody
    @RequestMapping("/remove")
    public JsonResult remove(Integer id) {
        if (courseService.removeById(id)) {
            return JsonResult.ok("删除成功");
        }
        return JsonResult.error("删除失败");
    }

    /**
     * 批量添加课程信息表
     */
    @RequiresPermissions("teacher:course:save")
    @OperLog(value = "课程信息表管理", desc = "批量添加", param = false, result = true)
    @ResponseBody
    @RequestMapping("/saveBatch")
    public JsonResult saveBatch(@RequestBody List<Course> list) {
        if (courseService.saveBatch(list)) {
            return JsonResult.ok("添加成功");
        }
        return JsonResult.error("添加失败");
    }

    /**
     * 批量修改课程信息表
     */
    @RequiresPermissions("teacher:course:update")
    @OperLog(value = "课程信息表管理", desc = "批量修改", result = true)
    @ResponseBody
    @RequestMapping("/updateBatch")
    public JsonResult updateBatch(@RequestBody BatchParam<Course> batchParam) {
        if (batchParam.update(courseService, "id")) {
            return JsonResult.ok("修改成功");
        }
        return JsonResult.error("修改失败");
    }

    /**
     * 批量删除课程信息表
     */
    @RequiresPermissions("teacher:course:remove")
    @OperLog(value = "课程信息表管理", desc = "批量删除", result = true)
    @ResponseBody
    @RequestMapping("/removeBatch")
    public JsonResult removeBatch(@RequestBody List<Integer> ids) {
        if (courseService.removeByIds(ids)) {
            return JsonResult.ok("删除成功");
        }
        return JsonResult.error("删除失败");
    }

}
