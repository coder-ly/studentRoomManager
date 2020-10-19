package com.egao.student.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.egao.base.entity.*;
import com.egao.base.service.*;
import com.egao.common.core.annotation.OperLog;
import com.egao.common.core.web.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

/**
 * 选课信息管理
 * Created by cy on 2020-05-06 01:33:22
 */
@Controller
@RequestMapping("/student/course")
public class StudentCoursesController extends BaseController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentCourseService studentCourseService;

    @RequiresPermissions("student:course:view")
    @RequestMapping()
    public String view() {
        return "student/course.html";
    }

    /**
     * 分页查询课程信息表
     */
    @RequiresPermissions("student:course:list")
    @OperLog(value = "课程信息表管理", desc = "分页查询")
    @ResponseBody
    @RequestMapping("/page")
    public PageResult<Course> page(HttpServletRequest request) {
        PageParam<Course> pageParam = new PageParam<>(request);
        return new PageResult<>(courseService.page(pageParam, pageParam.getWrapper()).getRecords(), pageParam.getTotal());
        //return courseService.listPage(pageParam);  // 使用关联查询
    }

    /**
     * 批量删除课程信息表
     */
    @OperLog(value = "课程信息表管理", desc = "批量选课", result = true)
    @ResponseBody
    @RequestMapping("/electiveBatch")
    public JsonResult electiveBatch(@RequestBody List<String> ids) {
        //去重，避免重复选课
        List<StudentCourse> studentCourseList = studentCourseService.list(new QueryWrapper<StudentCourse>().eq("student_no", getLoginUserId()));

        List<String> courseNoList = studentCourseList.stream().map(StudentCourse::getCourseNo).collect(Collectors.toList());
        ids.removeAll(courseNoList);

        List<StudentCourse> list = new ArrayList<>();
        ids.forEach(ele -> {
            StudentCourse studentCourse = new StudentCourse();
            studentCourse.setCourseNo(ele + "");
            studentCourse.setStudentNo(getLoginUserId() + "");
            list.add(studentCourse);
        });
        if (list.size() > 0) {
            if (studentCourseService.saveBatch(list)) {
                return JsonResult.ok("选课成功");
            }
        } else {
            return JsonResult.ok("选课成功");
        }
        return JsonResult.error("选课失败");
    }

}
