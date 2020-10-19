package com.egao.base.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.egao.base.entity.ClassA;
import com.egao.base.service.ClassService;
import com.egao.common.core.web.*;
import com.egao.common.core.annotation.OperLog;
import com.egao.base.entity.Score;
import com.egao.base.service.ScoreService;
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
@RequestMapping("/base/score")
public class ScoreController extends BaseController {
    @Autowired
    private ScoreService scoreService;

    @Autowired
    private DictionaryDataService dictionaryDataService;

    @Autowired
    private UserService userService;

    @Autowired
    private ClassService classService;

    @RequiresPermissions("base:score:view")
    @RequestMapping()
    public String view(Model model) {
        model.addAttribute("semesterList", dictionaryDataService.listByDictCode("semester"));
        model.addAttribute("classList", classService.list());

        return "base/score.html";
    }

    /**
     * 分页查询成绩信息表
     */
    @RequiresPermissions("base:score:list")
    @OperLog(value = "成绩信息管理", desc = "分页查询")
    @ResponseBody
    @RequestMapping("/page")
    public PageResult<Score> page(HttpServletRequest request) {
        PageParam<Score> pageParam = new PageParam<>(request);
        //return new PageResult<>(scoreService.page(pageParam, pageParam.getWrapper()).getRecords(), pageParam.getTotal());
        if (getLoginUser().getEmailVerified() == 0) {
            pageParam.put("teacherNo", getLoginUserId());
        }
        return scoreService.listPage(pageParam);  // 使用关联查询
    }

    /**
     * 查询全部成绩信息表
     */
    @RequiresPermissions("base:score:list")
    @OperLog(value = "成绩信息表管理", desc = "查询全部")
    @ResponseBody
    @RequestMapping("/list")
    public JsonResult list(HttpServletRequest request) {
        PageParam<Score> pageParam = new PageParam<>(request);
        return JsonResult.ok().setData(scoreService.list(pageParam.getOrderWrapper()));
        //List<Score> records = scoreService.listAll(pageParam.getNoPageParam());  // 使用关联查询
        //return JsonResult.ok().setData(pageParam.sortRecords(records));
    }

    /**
     * 根据id查询成绩信息表
     */
    @RequiresPermissions("base:score:list")
    @OperLog(value = "成绩信息表管理", desc = "根据id查询")
    @ResponseBody
    @RequestMapping("/get")
    public JsonResult get(Integer id) {
        return JsonResult.ok().setData(scoreService.getById(id));
        // 使用关联查询
        //PageParam<Score> pageParam = new PageParam<>();
        //pageParam.put("id", id);
        //List<Score> records = scoreService.listAll(pageParam.getNoPageParam());
        //return JsonResult.ok().setData(pageParam.getOne(records));
    }

    /**
     * 添加成绩信息表
     */
    @RequiresPermissions("base:score:save")
    @OperLog(value = "成绩信息表管理", desc = "添加", param = false, result = true)
    @ResponseBody
    @RequestMapping("/save")
    public JsonResult save(Score score) {
        if (scoreService.save(score)) {
            return JsonResult.ok("添加成功");
        }
        return JsonResult.error("添加失败");
    }

    /**
     * 修改成绩信息表
     */
    @RequiresPermissions("base:score:update")
    @OperLog(value = "成绩信息表管理", desc = "修改", param = false, result = true)
    @ResponseBody
    @RequestMapping("/update")
    public JsonResult update(Score score) {
        if (scoreService.updateById(score)) {
            return JsonResult.ok("修改成功");
        }
        return JsonResult.error("修改失败");
    }

    /**
     * 删除成绩信息表
     */
    @RequiresPermissions("base:score:remove")
    @OperLog(value = "成绩信息表管理", desc = "删除", result = true)
    @ResponseBody
    @RequestMapping("/remove")
    public JsonResult remove(Integer id) {
        if (scoreService.removeById(id)) {
            return JsonResult.ok("删除成功");
        }
        return JsonResult.error("删除失败");
    }

    /**
     * 批量添加成绩信息表
     */
    @RequiresPermissions("base:score:save")
    @OperLog(value = "成绩信息表管理", desc = "批量添加", param = false, result = true)
    @ResponseBody
    @RequestMapping("/saveBatch")
    public JsonResult saveBatch(@RequestBody List<Score> list) {
        if (scoreService.saveBatch(list)) {
            return JsonResult.ok("添加成功");
        }
        return JsonResult.error("添加失败");
    }

    /**
     * 批量修改成绩信息表
     */
    @RequiresPermissions("base:score:update")
    @OperLog(value = "成绩信息表管理", desc = "批量修改", result = true)
    @ResponseBody
    @RequestMapping("/updateBatch")
    public JsonResult updateBatch(@RequestBody BatchParam<Score> batchParam) {
        if (batchParam.update(scoreService, "id")) {
            return JsonResult.ok("修改成功");
        }
        return JsonResult.error("修改失败");
    }

    /**
     * 批量删除成绩信息表
     */
    @RequiresPermissions("base:score:remove")
    @OperLog(value = "成绩信息表管理", desc = "批量删除", result = true)
    @ResponseBody
    @RequestMapping("/removeBatch")
    public JsonResult removeBatch(@RequestBody List<Integer> ids) {
        if (scoreService.removeByIds(ids)) {
            return JsonResult.ok("删除成功");
        }
        return JsonResult.error("删除失败");
    }

    /**
     * 查询全部成绩信息表
     */
    @OperLog(value = "成绩信息管理", desc = "查询班级下的全部学生")
    @ResponseBody
    @RequestMapping("/studentList")
    public JsonResult list(@RequestParam String classId) {
        return JsonResult.ok().setData(userService.list(new QueryWrapper<User>().eq("class_no", classId)));
    }

    /**
     * 查询全部成绩信息表
     */
    @OperLog(value = "成绩信息管理", desc = "查询学生下全部课程")
    @ResponseBody
    @RequestMapping("/courseList")
    public JsonResult courseList(@RequestParam Integer studentId) {
        if (getLoginUser().getEmailVerified() == 0) {
            return JsonResult.ok().setData(scoreService.selectCourseList(studentId, getLoginUserId()));
        }
        return JsonResult.ok().setData(scoreService.selectCourseList(studentId, null));
    }
}
