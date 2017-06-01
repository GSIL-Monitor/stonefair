package com.zhenhappy.ems.manager.action.user.managerrole;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhenhappy.ems.entity.managerrole.TUserMenu;
import com.zhenhappy.ems.entity.managerrole.TUserOperation;
import com.zhenhappy.ems.entity.managerrole.TUserRole;
import com.zhenhappy.ems.manager.tag.StringUtil;
import com.zhenhappy.ems.manager.tag.WriterUtil;
import com.zhenhappy.ems.service.managerrole.TUserInfoService;
import com.zhenhappy.ems.service.managerrole.TUserMenuService;
import com.zhenhappy.ems.service.managerrole.TUserOperationService;
import com.zhenhappy.ems.service.managerrole.TUserRoleService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by wangxd on 2016-05-16.
 */

@Controller
@RequestMapping("user")
@SuppressWarnings("unchecked")
public class RoleAction {
    private TUserRole role;
    private TUserOperation operation;
    @Autowired
    private TUserInfoService userService;
    @Autowired
    private TUserRoleService roleService;
    private Map map;
    private TUserMenu menu;
    @Autowired
    private TUserMenuService menuService;
    @Autowired
    private TUserOperationService operationService;
    static Logger logger = Logger.getLogger(RoleAction.class);

    @RequestMapping("roleList")
    public void roleList(HttpServletRequest request, HttpServletResponse response) {
        try {
            role = new TUserRole();
            role.setRoleName(request.getParameter("roleName"));
            List<TUserRole> list = findAllRole(role);
            //int total = roleService.countRoleByName(role.getRoleName());
            int total = 0;
            if(list != null) {
                total = list.size();
            }
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("total", total);
            jsonObj.put("rows", list);
            WriterUtil.write(response, jsonObj.toString());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("角色展示错误", e);
        }
    }

    private List<TUserRole> findAllRole(TUserRole role) {
        try {
            return roleService.findRoleByName(role.getRoleName());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping("roleCombobox")
    public void roleCombobox(HttpServletRequest request, HttpServletResponse response) {
        try {
            JSONArray jsonArray = new JSONArray();
            List<TUserRole> list = findAllRole(new TUserRole());
            jsonArray.addAll(list);
            WriterUtil.write(response, jsonArray.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("reserveRole")
    public void addUser(HttpServletRequest request, TUserRole role, HttpServletResponse response) {
        String roleId = request.getParameter("roleId");
        JSONObject result = new JSONObject();
        try {
            if (StringUtil.isNotEmpty(roleId)) {
                TUserRole userRole = roleService.findOneRole(Integer.parseInt(roleId));
                if(userRole != null){
                    userRole.setMenuIds(role.getMenuIds());
                    userRole.setRoleName(role.getRoleName());
                    userRole.setOperationIds(role.getOperationIds());
                    userRole.setRoleDescription(role.getRoleDescription());
                    roleService.updateRole(role);
                    result.put("success", true);
                }
            } else {
                if (roleService.existRoleWithRoleName(role.getRoleName()) == null) {  // 没有重复可以添加
                    roleService.addRole(role);
                    result.put("success", true);
                } else {
                    result.put("success", true);
                    result.put("errorMsg", "该角色名被使用");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("角色保存错误", e);
            result.put("success", true);
            result.put("errorMsg", "对不起，操作失败");
        }
        WriterUtil.write(response, result.toString());
    }

    @RequestMapping("deleteRole")
    public void delRole(HttpServletRequest request, HttpServletResponse response) {
        JSONObject result = new JSONObject();
        try {
            String[] roleIds = request.getParameter("ids").split(",");
            for (int i = 0; i < roleIds.length; i++) {
                boolean b = userService.existUserWithRoleId(Integer.parseInt(roleIds[i])) == null; //该角色下面没有用户
                if (!b) {
                    result.put("errorIndex", i);
                    result.put("errorMsg", "有角色下面有用户，不能删除");
                    WriterUtil.write(response, result.toString());
                    return;
                }
            }
            if (roleIds.length == 1) {
                roleService.deleteRole(Integer.parseInt(roleIds[0]));
            } else {
                if (roleIds != null && roleIds.length > 0) {
                    for (int i = 0; i < roleIds.length; i++) {
                        roleService.deleteRoleByRoleIds(Integer.parseInt(roleIds[i]));
                    }
                }
            }
            result.put("success", true);
            result.put("delNums", roleIds.length);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("角色删除错误", e);
            result.put("errorMsg", "对不起，删除失败");
        }
        WriterUtil.write(response, result.toString());
    }

    @RequestMapping("chooseMenu")
    public void chooseMenu(HttpServletRequest request, HttpServletResponse response) {
        try {
            String parentId = request.getParameter("parentId");
            String roleId = request.getParameter("roleId");
            role = roleService.findOneRole(Integer.parseInt(roleId));
            String menuIds = role.getMenuIds();
            String operationIds = role.getOperationIds();
            JSONArray jsonArray = getCheckedMenusByParentId(parentId, menuIds, operationIds);
            WriterUtil.write(response, jsonArray.toString());
            System.out.println(jsonArray.toString());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("加载权限菜单树错误", e);
        }
    }

    // 选中已有的角色
    public JSONArray getCheckedMenusByParentId(String parentId, String menuIds, String operationIds) throws Exception {
        JSONArray jsonArray = this.getCheckedMenuByParentId(parentId, menuIds, operationIds);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if ("open".equals(jsonObject.getString("state"))) {
                continue;
            } else {
                jsonObject.put("children", getCheckedMenusByParentId(jsonObject.getString("id"), menuIds, operationIds));
            }
        }
        return jsonArray;
    }

    public JSONArray getCheckedMenuByParentId(String parentId, String menuIds, String operationIds) throws Exception {
        JSONArray jsonArray = new JSONArray();
        menu = new TUserMenu();
        menu.setParentId(Integer.parseInt(parentId));
        List<TUserMenu> list = menuService.findUserMenuByParentId(Integer.parseInt(parentId));
        if(list != null){
            for (TUserMenu menu : list) {
                JSONObject jsonObject = new JSONObject();
                int menuId = menu.getMenuId();
                jsonObject.put("id", menuId);
                jsonObject.put("text", menu.getMenuName());
                jsonObject.put("iconCls", menu.getIconCls());
                jsonObject.put("state", menu.getState());
                if (StringUtil.isNotEmpty(menuIds)) {
                    if (StringUtil.existStrArr(menuId + "", menuIds.split(","))) {
                        jsonObject.put("checked", true);
                    }
                }
                jsonObject.put("children", getOperationJsonArray(menuId, operationIds));
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }

    public JSONArray getOperationJsonArray(int menuId, String operationIds) {
        JSONArray jsonArray = new JSONArray();
        try {
            operation = new TUserOperation();
            operation.setMenuId(menuId);
            List<TUserOperation> list = operationService.findOperationByMenuId(menuId);
            if(list != null){
                for (TUserOperation operation : list) {
                    JSONObject jsonObject = new JSONObject();
                    int operationId = operation.getOperationId();
                    jsonObject.put("id", operationId);
                    jsonObject.put("text", operation.getOperationName());
                    jsonObject.put("iconCls", "");
                    jsonObject.put("state", "open");
                    if (StringUtil.isNotEmpty(operationIds)) {
                        if (StringUtil.existStrArr(operationId + "", operationIds.split(","))) {
                            jsonObject.put("checked", true);
                        }
                    }
                    jsonArray.add(jsonObject);
                }
            }
            return jsonArray;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping("updateRoleMenu")
    public void updateRoleMenu(HttpServletRequest request, HttpServletResponse response) {
        JSONObject result = new JSONObject();
        try {
            String roleId = request.getParameter("roleId");
            String[] ids = request.getParameter("menuIds").split(",");
            String menuIds = "";
            String operationIds = "";
            /**
             * 采用的方案是在菜单递归之后，再加上各菜单下的按钮
             * 采用easyui的解析方式所以字段都用的是id和text。
             * 为了区别两者，我们规定operationId自增从10000开始
             * menuId从1开始，在上传过来的ids中是这样的形式
             * 2,10000,3,4,7,10004,10006,45 这样的菜单ID和按钮ID的混合形式
             * 所以通过与10000的比较来确定哪些是菜单的哪些是按钮的
             *
             */
            for (int i = 0; i < ids.length; i++) {
                int id = Integer.parseInt(ids[i]);
                if (id >= 10000) {
                    operationIds += ("," + id);
                } else {
                    menuIds += ("," + id);
                }
            }
            role = roleService.findOneRole(Integer.parseInt(roleId));
            if(role != null){
                role.setRoleId(Integer.parseInt(roleId));
                if (menuIds.length() != 0) {
                    role.setMenuIds(menuIds.substring(1));
                }
                if (operationIds.length() != 0) {
                    role.setOperationIds(operationIds.substring(1));
                }
                roleService.updateRole(role);
                result.put("success", true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("授权失败", e);
            result.put("errorMsg", "对不起，授权失败");
        }
        WriterUtil.write(response, result.toString());
    }
}
