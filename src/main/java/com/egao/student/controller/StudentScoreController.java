package com.egao.student.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.egao.base.entity.Score;
import com.egao.base.service.ClassService;
import com.egao.base.service.ScoreService;
import com.egao.common.core.annotation.OperLog;
import com.egao.common.core.web.*;
import com.egao.common.system.entity.User;
import com.egao.common.system.service.DictionaryDataService;
import com.egao.common.system.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 成绩信息表管理
 * Created by cy on 2020-05-06 01:33:22
 */
@Controller
@RequestMapping("/student/score")
public class StudentScoreController extends BaseController {
    @Autowired
    private ScoreService scoreService;

    @Autowired
    private DictionaryDataService dictionaryDataService;

    @RequiresPermissions("student:score:view")
    @RequestMapping()
    public String view(Model model) {
        model.addAttribute("semesterList", dictionaryDataService.listByDictCode("semester"));
        return "student/score.html";
    }

    /**
     * 分页查询成绩信息表
     */
    @RequiresPermissions("student:score:list")
    @OperLog(value = "成绩信息管理", desc = "分页查询")
    @ResponseBody
    @RequestMapping("/page")
    public PageResult<Score> page(HttpServletRequest request) {
        PageParam<Score> pageParam = new PageParam<>(request);
        //获取该学生所在班级
        pageParam.put("studentNo", getLoginUser().getIdCard());
        return scoreService.listPage(pageParam);  // 使用关联查询
    }
}
