package com.egao.student.controller;

import com.egao.base.entity.Scroom;
import com.egao.base.service.ScroomService;
import com.egao.common.core.annotation.OperLog;
import com.egao.common.core.web.*;
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
@RequestMapping("/student/scroom")
public class StudentScroomController extends BaseController {
    @Autowired
    private ScroomService scroomService;

    @RequiresPermissions("student:scroom:view")
    @RequestMapping()
    public String view() {
        return "student/scroom.html";
    }

    /**
     * 分页查询教室表
     */
    @RequiresPermissions("student:scroom:list")
    @OperLog(value = "教室管理", desc = "分页查询")
    @ResponseBody
    @RequestMapping("/page")
    public PageResult<Scroom> page(HttpServletRequest request) {
        PageParam<Scroom> pageParam = new PageParam<>(request);
        pageParam.setDefaultOrder(null, new String[]{"create_time"});
        return new PageResult<>(scroomService.page(pageParam, pageParam.getWrapper()).getRecords(), pageParam.getTotal());
        //return scroomService.listPage(pageParam);  // 使用关联查询
    }
}
