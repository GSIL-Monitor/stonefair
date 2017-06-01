package com.zhenhappy.ems.manager.action;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhenhappy.ems.dto.BaseResponse;
import com.zhenhappy.ems.entity.managerrole.TUserInfo;
import com.zhenhappy.ems.entity.managerrole.TUserMenu;
import com.zhenhappy.ems.entity.managerrole.TUserRole;
import com.zhenhappy.ems.manager.dto.ManagerPrinciple;
import com.zhenhappy.ems.manager.service.AdminService;
import com.zhenhappy.ems.manager.tag.WriterUtil;
import com.zhenhappy.ems.service.managerrole.TUserInfoService;
import com.zhenhappy.ems.service.managerrole.TUserMenuService;
import com.zhenhappy.ems.service.managerrole.TUserRoleService;
import com.zhenhappy.ems.stonetime.ExhibitorTimeDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wujianbin on 2014-04-22.
 */
@Controller
@RequestMapping(value = "/")
@SessionAttributes(ManagerPrinciple.MANAGERPRINCIPLE)
public class LoginAction extends BaseAction {

    private static Logger log = Logger.getLogger(LoginAction.class);

    @Autowired
    private AdminService adminService;
    @Autowired
    private TUserInfoService userInfoService;
    @Autowired
    private TUserRoleService roleService;
    @Autowired
    private TUserMenuService userMenuService;
    @Autowired
    private ExhibitorTimeDao exhibitorTimeDao;

    /**
     * process login.
     * <p/>
     * if login success,put principle into session.
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public BaseResponse login(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletRequest request) {
        BaseResponse baseResponse = new BaseResponse();
        try {
            TUserInfo admin = adminService.login(username, password);
            if (admin != null) {
                baseResponse.setResultCode(0);
                ManagerPrinciple principle = new ManagerPrinciple();
                TUserRole tUserRole = roleService.findOneRole(admin.getRoleId());
                admin.setRoleName(tUserRole.getRoleName());
                principle.setAdmin(admin);
                principle.setCurrentOperationIds(tUserRole.getOperationIds());

                request.setAttribute("userName", username);
                request.setAttribute("password", password);
                request.getSession().setAttribute("currentUser", admin);  // 当前用户信息
                request.getSession().setAttribute("currentOperationIds", tUserRole.getOperationIds());
                request.setAttribute("currentOperationIds", tUserRole.getOperationIds());
                request.getSession().setAttribute(ManagerPrinciple.MANAGERPRINCIPLE, principle);
            } else {
                baseResponse.setResultCode(1);
            }
        } catch (Exception e) {
            log.error("login error.", e);
            baseResponse.setResultCode(1);
        }
        return baseResponse;
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login() {
        return "public/login";
    }

    /**
     * logout and remove principle from session.
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "logoutEx")
    public void logoutEx(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().removeAttribute(ManagerPrinciple.MANAGERPRINCIPLE);
        response.sendRedirect(request.getContextPath() + "/login.html");
    }

    //**************************增加权限管理相关接口**********************************
    // 加载最上级左菜单树
    @RequestMapping(value = "user/menuTree")
    public void getMenuTree(HttpServletRequest request, HttpServletResponse response,
                            @ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) {
        try {
            String parentId = request.getParameter("parentId");
            TUserInfo userInfo = (TUserInfo) principle.getAdmin();
            TUserRole userRole = roleService.findOneRole(userInfo.getRoleId());
            String[] menuIds = userRole.getMenuIds().split(",");
            JSONArray jsonArray = getMenusByParentId(parentId, menuIds);
            WriterUtil.write(response, jsonArray.toString());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("加载左菜单错误", e);
        }
    }

    // 递归加载所所有树菜单
    public JSONArray getMenusByParentId(String parentId, String[] menuIds) throws Exception {
        JSONArray jsonArray = this.getMenuByParentId(parentId, menuIds);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if ("open".equals(jsonObject.getString("state"))) {
                continue;
            } else {
                jsonObject.put("children", getMenusByParentId(jsonObject.getString("id"), menuIds));
            }
        }
        return jsonArray;
    }

    // 将所有的树菜单放入easyui要求格式的json
    public JSONArray getMenuByParentId(String parentId, String[] menuIds) throws Exception {
        JSONArray jsonArray = new JSONArray();
        List<TUserMenu> list = userMenuService.findMenuByParendIdAndIds(Integer.parseInt(parentId), menuIds);
        if (list != null) {
            for (TUserMenu menu : list) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", menu.getMenuId());
                jsonObject.put("text", menu.getMenuName());
                jsonObject.put("iconCls", menu.getIconCls());
                JSONObject attributeObject = new JSONObject();
                attributeObject.put("menuUrl", menu.getMenuUrl());
                if (!hasChildren(menu.getMenuId(), menuIds)) {
                    jsonObject.put("state", "open");
                } else {
                    jsonObject.put("state", menu.getState());
                }
                jsonObject.put("attributes", attributeObject);
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }

    // 判断是不是有子孩子，人工结束递归树
    public boolean hasChildren(Integer parentId, String[] menuIds) {
        boolean flag = false;
        try {
            Map map = new HashMap();
            List<TUserMenu> list = userMenuService.findMenuByParendIdAndIds(parentId, menuIds);
            if (list == null || list.size() == 0) {
                flag = false;
            } else {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("加载左菜单时判断是不是有子孩子错误", e);
        }
        return flag;
    }

    // 更新密码
    @RequestMapping("user/updatePassword")
    public void updatePassword(HttpServletRequest request, HttpServletResponse response) {
        JSONObject result = new JSONObject();
        try {
            String userId = request.getParameter("userId");
            String newPassword = request.getParameter("newPassword");
            TUserInfo userInfo = userInfoService.findOneUserInfo(Integer.parseInt(userId));
            if(userInfo != null){
                userInfo.setId(Integer.parseInt(userId));
                userInfo.setPassword(newPassword);
                userInfoService.updateUserInfo(userInfo);
                result.put("success", "true");
            } else {
                result.put("success", "true");
                result.put("errorMsg", "对不起！用户查找错误！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("密码更新失败", e);
            result.put("success", "true");
            result.put("errorMsg", "对不起！密码修改失败");
        }
        WriterUtil.write(response, result.toString());
    }

    //安全退出
    @RequestMapping("user/logout")
    private void logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 清空session
        HttpSession session = request.getSession();
        session.invalidate();

        // 清空cookie
        Cookie[] cookies = request.getCookies();
        for (int i = 0; i < cookies.length; i++) {
            Cookie cookie = new Cookie(cookies[i].getName(), null);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }

        response.sendRedirect("public/login.html");
    }
}
