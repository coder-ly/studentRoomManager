package com.egao.base.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.egao.base.entity.*;
import com.egao.base.service.*;
import com.egao.common.core.annotation.OperLog;
import com.egao.common.core.web.*;
import com.egao.common.system.entity.Role;
import com.egao.common.system.entity.User;
import com.egao.common.system.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yicui
 * @date 2020-05-06 下午9:09
 */
@Controller
@RequestMapping("/base/student")
public class StudentController extends BaseController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private ClassService classService;

    @Autowired
    private StudentRoomService studentRoomService;

    @RequiresPermissions("sys:user:view")
    @RequestMapping()
    public String view(Model model) {
        QueryWrapper<Room> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", "0");
        List<Room> list = roomService.list(queryWrapper);
        model.addAttribute("roomList", list);
        model.addAttribute("isAdmin", getLoginUser().getEmailVerified());

        List<ClassA> classList = classService.list();
        model.addAttribute("classList", classList);
        return "base/student.html";
    }

    /**
     * 分页查询用户
     */
    @OperLog(value = "学生管理", desc = "分页查询")
    @RequiresPermissions("sys:user:list")
    @ResponseBody
    @RequestMapping("/page")
    public PageResult<User> page(HttpServletRequest request) {
        //查询角色为学生的信息
        PageParam<User> pageParam = new PageParam<>(request);
        pageParam.setDefaultOrder(null, new String[]{"create_time"});
        List<User> list = userService.page(pageParam, pageParam.getWrapper().eq("email_verified", 1)).getRecords();
        list.forEach(ele -> {
            ClassA classA = classService.getById(ele.getClassNo());
            if (classA != null) {
                ele.setClassName(classA.getClassNo() + "");
            }

            StudentRoom studentRoom = studentRoomService.getOne(new QueryWrapper<StudentRoom>().eq("student_no", ele.getIdCard()));
            if (studentRoom != null) {
                ele.setRoomName(studentRoom.getComments());
            }

        });
        return new PageResult<>(list, pageParam.getTotal());
    }

    /**
     * 添加用户
     */
    @OperLog(value = "学生管理", desc = "添加", param = false, result = true)
    @RequiresPermissions("sys:user:save")
    @ResponseBody
    @RequestMapping("/save")
    public JsonResult save(@RequestBody User user) {
        user.setState(0);
        user.setPassword(userService.encodePsw(user.getPassword()));
        user.setNickName(user.getTrueName());
        //设置为学生角色
        user.setEmailVerified(1);
        List<Integer> roleList = new ArrayList<>();
        roleList.add(4);
        user.setRoleIds(roleList);
        if (userService.saveUser(user)) {
            return JsonResult.ok("添加成功");
        }
        return JsonResult.error("添加失败");
    }

    /**
     * 修改用户
     */
    @OperLog(value = "学生管理", desc = "修改", param = false, result = true)
    @RequiresPermissions("sys:user:update")
    @ResponseBody
    @RequestMapping("/update")
    public JsonResult update(@RequestBody User user) {
        user.setState(null);  // 状态不能修改
        user.setPassword(null);  // 密码不能修改
        user.setUsername(null);  // 账号不能修改
        if (userService.updateUser(user)) {
            return JsonResult.ok("修改成功");
        }
        return JsonResult.error("修改失败");
    }

    /**
     * 添加学生宿舍表
     */
    @OperLog(value = "学生班级管理", desc = "学生班级管理", param = false, result = true)
    @ResponseBody
    @RequestMapping("/updateClassNo")
    public JsonResult updateClassNo(User user) {
        if (userService.updateById(user)) {
            return JsonResult.ok("班级分配成功");
        }
        return JsonResult.error("班级分配失败");
    }

}
