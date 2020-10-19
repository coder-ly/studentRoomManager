package com.egao.common.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.egao.base.entity.Information;
import com.egao.base.service.InformationService;
import com.egao.common.core.exception.BusinessException;
import com.egao.common.core.web.BaseController;
import com.egao.common.core.web.JsonResult;
import com.egao.common.system.entity.*;
import com.egao.common.system.service.*;
import com.wf.captcha.utils.CaptchaUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 首页、登录、验证码等
 * Created by wangfan on 2018-12-24 16:10
 */
@Controller
public class MainController extends BaseController implements ErrorController {
    @Autowired
    private MenuService menuService;

    @Autowired
    private LoginRecordService loginRecordService;

    @Autowired
    private UserService userService;

    @Autowired
    private InformationService informationService;

    /**
     * 用户登录
     */
    @ResponseBody
    @PostMapping("/login")
    public JsonResult login(String username, String password, String code, Boolean remember, HttpServletRequest request) {
        if (username == null || username.trim().isEmpty()) return JsonResult.error("请输入账号");
        //if (!CaptchaUtil.ver(code, request)) {
        //    loginRecordService.saveAsync(username, LoginRecord.TYPE_ERROR, "验证码错误", request);
        //    return JsonResult.error("验证码不正确");
        //}
        try {
            if (remember == null) remember = false;
            SecurityUtils.getSubject().login(new UsernamePasswordToken(username, password, remember));
            loginRecordService.saveAsync(username, request);
            return JsonResult.ok("登录成功");
        } catch (IncorrectCredentialsException ice) {
            loginRecordService.saveAsync(username, LoginRecord.TYPE_ERROR, "密码错误", request);
            return JsonResult.error("密码错误");
        } catch (UnknownAccountException uae) {
            loginRecordService.saveAsync(username, LoginRecord.TYPE_ERROR, "账号不存在", request);
            return JsonResult.error("账号不存在");
        } catch (LockedAccountException e) {
            loginRecordService.saveAsync(username, LoginRecord.TYPE_ERROR, "账号被锁定", request);
            return JsonResult.error("账号被锁定");
        } catch (ExcessiveAttemptsException eae) {
            loginRecordService.saveAsync(username, LoginRecord.TYPE_ERROR, "操作频繁", request);
            return JsonResult.error("操作频繁，请稍后再试");
        }
    }

    /**
     * 登录页
     */
    @GetMapping("/login")
    public String login() {
        if (getLoginUser() != null) return "redirect:index";
        return "login.html";
    }

    /**
     * 主页
     */
    @RequestMapping({"/", "/index"})
    public String index(Model model) {
        // 左侧菜单
        List<Menu> menus = menuService.getUserMenu(getLoginUserId(), Menu.TYPE_MENU);
        model.addAttribute("menus", menuService.toMenuTree(menus, 0));
        return "index.html";
    }

    /**
     * 图形验证码
     */
    @RequestMapping("/assets/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) {
        try {
            CaptchaUtil.out(5, request, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 主页弹窗页面
     */
    @RequestMapping("/tpl/{name}")
    public String tpl(@PathVariable("name") String name, Model model) {
        if ("message".equals(name)) {
            QueryWrapper<Information> queryWrapper = new QueryWrapper<>();
            queryWrapper.orderByDesc("create_time");
            //查询消息
            List<Information> list = informationService.list(queryWrapper);
            model.addAttribute("messageList", list);
            model.addAttribute("isNotEmpty", list.size() > 0);
        }
        return "index/" + name + ".html";
    }

    /**
     * 错误页
     */
    @RequestMapping("/error")
    public String error() {
        return "error/404.html";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

    /**
     * 控制台
     */
    @RequestMapping("/console")
    public String console() {
        return "index/console.html";
    }

    /**
     * 注册页面
     */
    @GetMapping("/reg")
    public String reg() {
        return "reg.html";
    }

    /**
     * 注册
     */
    @ResponseBody
    @PostMapping("/reg")
    public JsonResult reg(User user, String code, HttpServletRequest request) {
        try {
            //if (!CaptchaUtil.ver(code, request)) {
            //    return JsonResult.error("验证码不正确");
            //}
            //加密密码
            user.setPassword(userService.encodePsw(user.getPassword()));

            //设置角色
            List<Integer> roleList = new ArrayList<>();
            if (user.getEmailVerified() == 0) {
                //教师注册
                roleList.add(5);
            } else {
                //学生注册
                roleList.add(4);
            }
            user.setNickName(user.getTrueName());
            user.setRoleIds(roleList);
            if (userService.saveUser(user)) {
                return JsonResult.ok("注册成功");
            }
            return JsonResult.error("注册失败");
        } catch (BusinessException e) {
            return JsonResult.error(e.getMessage());
        }
    }
}
