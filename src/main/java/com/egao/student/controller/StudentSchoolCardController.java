package com.egao.student.controller;

import com.egao.base.entity.SchoolCard;
import com.egao.base.service.SchoolCardService;
import com.egao.common.core.annotation.OperLog;
import com.egao.common.core.web.*;
import com.egao.common.system.entity.User;
import com.egao.common.system.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * 学生校园卡管理
 * Created by cy on 2020-05-06 01:33:22
 */
@Controller
@RequestMapping("/student/schoolCard")
public class StudentSchoolCardController extends BaseController {
    @Autowired
    private SchoolCardService schoolCardService;

    @Autowired
    private UserService userService;

    @RequiresPermissions("student:schoolCard:view")
    @RequestMapping()
    public String view() {
        return "student/schoolCard.html";
    }

    /**
     * 分页查询校园卡表
     */
    @RequiresPermissions("student:schoolCard:list")
    @OperLog(value = "校园卡表管理", desc = "分页查询")
    @ResponseBody
    @RequestMapping("/page")
    public PageResult<SchoolCard> page(HttpServletRequest request) {
        PageParam<SchoolCard> pageParam = new PageParam<>(request);
        return new PageResult<>(schoolCardService.page(pageParam, pageParam.getWrapper().eq("student_no", getLoginUser().getIdCard())).getRecords(), pageParam.getTotal());
        //return schoolCardService.listPage(pageParam);  // 使用关联查询
    }

    /**
     * 查询全部校园卡表
     */
    @RequiresPermissions("student:schoolCard:list")
    @OperLog(value = "校园卡表管理", desc = "查询全部")
    @ResponseBody
    @RequestMapping("/list")
    public JsonResult list(HttpServletRequest request) {
        PageParam<SchoolCard> pageParam = new PageParam<>(request);
        return JsonResult.ok().setData(schoolCardService.list(pageParam.getOrderWrapper()));
        //List<SchoolCard> records = schoolCardService.listAll(pageParam.getNoPageParam());  // 使用关联查询
        //return JsonResult.ok().setData(pageParam.sortRecords(records));
    }

    /**
     * 根据id查询校园卡表
     */
    @RequiresPermissions("student:schoolCard:list")
    @OperLog(value = "校园卡表管理", desc = "根据id查询")
    @ResponseBody
    @RequestMapping("/get")
    public JsonResult get(Integer id) {
        return JsonResult.ok().setData(schoolCardService.getById(id));
        // 使用关联查询
        //PageParam<SchoolCard> pageParam = new PageParam<>();
        //pageParam.put("id", id);
        //List<SchoolCard> records = schoolCardService.listAll(pageParam.getNoPageParam());
        //return JsonResult.ok().setData(pageParam.getOne(records));
    }

    /**
     * 添加校园卡表
     */
    @RequiresPermissions("student:schoolCard:save")
    @OperLog(value = "校园卡表管理", desc = "添加", param = false, result = true)
    @ResponseBody
    @RequestMapping("/save")
    public JsonResult save(SchoolCard schoolCard) {
        schoolCard.setBalance(new BigDecimal("0"));
        schoolCard.setPassword(userService.encodePsw(schoolCard.getPassword()));
        //获取当前登录用户信息
        User loginUser = getLoginUser();
        if (schoolCardService.save(schoolCard, loginUser)) {
            return JsonResult.ok("添加成功");
        }
        return JsonResult.error("添加失败");
    }

    /**
     * 修改校园卡表
     */
    @RequiresPermissions("student:schoolCard:update")
    @OperLog(value = "校园卡表管理", desc = "修改", param = false, result = true)
    @ResponseBody
    @RequestMapping("/update")
    public JsonResult update(SchoolCard schoolCard) {
        schoolCard.setPassword(userService.encodePsw(schoolCard.getPassword()));
        if (schoolCardService.updateById(schoolCard)) {
            return JsonResult.ok("修改成功");
        }
        return JsonResult.error("修改失败");
    }

    /**
     * 删除校园卡表
     */
    @RequiresPermissions("student:schoolCard:remove")
    @OperLog(value = "校园卡表管理", desc = "删除", result = true)
    @ResponseBody
    @RequestMapping("/remove")
    public JsonResult remove(Integer id) {
        if (schoolCardService.removeById(id)) {
            return JsonResult.ok("删除成功");
        }
        return JsonResult.error("删除失败");
    }

    /**
     * 批量添加校园卡表
     */
    @RequiresPermissions("student:schoolCard:save")
    @OperLog(value = "校园卡表管理", desc = "批量添加", param = false, result = true)
    @ResponseBody
    @RequestMapping("/saveBatch")
    public JsonResult saveBatch(@RequestBody List<SchoolCard> list) {
        if (schoolCardService.saveBatch(list)) {
            return JsonResult.ok("添加成功");
        }
        return JsonResult.error("添加失败");
    }

    /**
     * 批量修改校园卡表
     */
    @RequiresPermissions("student:schoolCard:update")
    @OperLog(value = "校园卡表管理", desc = "批量修改", result = true)
    @ResponseBody
    @RequestMapping("/updateBatch")
    public JsonResult updateBatch(@RequestBody BatchParam<SchoolCard> batchParam) {
        if (batchParam.update(schoolCardService, "id")) {
            return JsonResult.ok("修改成功");
        }
        return JsonResult.error("修改失败");
    }

    /**
     * 批量删除校园卡表
     */
    @RequiresPermissions("student:schoolCard:remove")
    @OperLog(value = "校园卡表管理", desc = "批量删除", result = true)
    @ResponseBody
    @RequestMapping("/removeBatch")
    public JsonResult removeBatch(@RequestBody List<Integer> ids) {
        if (schoolCardService.removeByIds(ids)) {
            return JsonResult.ok("删除成功");
        }
        return JsonResult.error("删除失败");
    }

    /**
     * 充值校园卡
     */
    @OperLog(value = "校园卡管理", desc = "充值", param = false, result = true)
    @ResponseBody
    @RequestMapping("/cz")
    public JsonResult cz(SchoolCard schoolCard) {
        schoolCard.setPassword(userService.encodePsw(schoolCard.getPassword()));
        if (schoolCardService.cz(schoolCard)) {
            return JsonResult.ok("充值成功");
        }
        return JsonResult.error("充值失败");
    }
}
