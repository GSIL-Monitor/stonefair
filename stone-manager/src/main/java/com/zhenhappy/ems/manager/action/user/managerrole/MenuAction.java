package com.zhenhappy.ems.manager.action.user.managerrole;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhenhappy.ems.dao.managerrole.TUserMenuDao;
import com.zhenhappy.ems.entity.managerrole.TUserInfo;
import com.zhenhappy.ems.entity.managerrole.TUserMenu;
import com.zhenhappy.ems.entity.managerrole.TUserOperation;
import com.zhenhappy.ems.entity.managerrole.TUserRole;
import com.zhenhappy.ems.manager.dto.ManagerPrinciple;
import com.zhenhappy.ems.manager.tag.StringUtil;
import com.zhenhappy.ems.manager.tag.WriterUtil;
import com.zhenhappy.ems.service.managerrole.TUserMenuService;
import com.zhenhappy.ems.service.managerrole.TUserOperationService;
import com.zhenhappy.ems.service.managerrole.TUserRoleService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by wangxd on 2016-05-16.
 */

@Controller
@RequestMapping("user")
@SessionAttributes(ManagerPrinciple.MANAGERPRINCIPLE)
public class MenuAction {
    private TUserMenu menu;
    private TUserOperation operation;
    @Autowired
    private TUserMenuService menuService;
    @Autowired
    private TUserOperationService operationService;
    @Autowired
    private TUserMenuDao userMenuDao;
    @Autowired
    private TUserRoleService roleService;

    static Logger logger = Logger.getLogger(MenuAction.class);

    @RequestMapping("treeGridMenu")
    public void treeGridMenu(HttpServletRequest request, HttpServletResponse response) {
        try {
            String parentId = request.getParameter("parentId");
            JSONArray jsonArray = getListByParentId(parentId);
            WriterUtil.write(response, jsonArray.toString());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("菜单展示错误", e);
        }
    }

    public JSONArray getListByParentId(String parentId) throws Exception {
        JSONArray jsonArray = this.getTreeGridMenuByParentId(parentId);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if ("open".equals(jsonObject.getString("state"))) {
                continue;
            } else {
                jsonObject.put("children", getListByParentId(jsonObject.getString("id")));
            }
        }
        return jsonArray;
    }

    public JSONArray getTreeGridMenuByParentId(String parentId) throws Exception {
        JSONArray jsonArray = new JSONArray();
        menu = new TUserMenu();
        menu.setParentId(Integer.parseInt(parentId));
        List<TUserMenu> list = menuService.findUserMenuByParentId(menu.getParentId());
        if(list != null) {
            for (TUserMenu menu : list) {
                JSONObject jsonObject = new JSONObject();
                Integer menuId = menu.getMenuId();
                jsonObject.put("id", menuId);
                jsonObject.put("text", menu.getMenuName());
                jsonObject.put("iconCls", menu.getIconCls());
                jsonObject.put("state", menu.getState());
                jsonObject.put("seq", menu.getSeq());
                jsonObject.put("menuUrl", menu.getMenuUrl());
                jsonObject.put("menuDescription", menu.getMenuDescription());

                // 加上该页面菜单下面的按钮
                operation = new TUserOperation();
                operation.setMenuId(menuId);
                List<TUserOperation> operaList = operationService.findOperationByMenuId(menuId);
                if (operaList != null && operaList.size() > 0) {
                    String string = "";
                    for (TUserOperation o : operaList) {
                        string += o.getOperationName() + ",";
                    }
                    jsonObject.put("operationNames", string.substring(0, string.length() - 1));
                } else {
                    jsonObject.put("operationNames", "");
                }
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }

    @RequestMapping("reserveMenu")
    public void reserveMenu(HttpServletRequest request, HttpServletResponse response, TUserMenu menu) {
        String menuId = request.getParameter("menuId");
        String parentId = request.getParameter("parentId");
        Integer menuId1 = menu.getMenuId();
        Integer parentId2 = menu.getParentId();
        JSONObject result = new JSONObject();
        try {
            if (StringUtil.isNotEmpty(menuId)) {  //更新操作
                TUserMenu userMenu = menuService.findOneUserMenu(Integer.parseInt(menuId));
                if(userMenu != null){
                    userMenu.setMenuName(menu.getMenuName());
                    userMenu.setMenuId(Integer.parseInt(menuId));
                    userMenu.setMenuDescription(menu.getMenuDescription());
                    userMenu.setIconCls(menu.getIconCls());
                    userMenu.setMenuUrl(menu.getMenuUrl());
                    userMenu.setSeq(menu.getSeq());
                    menuService.updateUserMenu(userMenu);
                }
            } else {
                menu.setParentId(Integer.parseInt(parentId));
                if(menu.getMenuUrl().equals("")){
                    menu.setState("closed");
                } else{
                    menu.setState("open");
                }
                if (isLeafEx(menu)) {
                    // 添加操作
                    menuService.addUserMenu(menu);

                    // 更新他上级状态。变成CLOSED
                    TUserMenu menuParent = new TUserMenu();
                    menuParent.setMenuId(Integer.parseInt(parentId));
                    menuParent.setMenuName(menu.getMenuName());
                    menuParent.setIconCls(menu.getIconCls());
                    menuParent.setMenuDescription(menu.getMenuDescription());
                    menuParent.setMenuUrl(menu.getMenuUrl());
                    menuParent.setParentId(menu.getParentId());
                    menuParent.setSeq(menu.getSeq());
                    menuParent.setState("closed");
                    menuService.updateUserMenu(menuParent);
                } else {
                    menuService.addUserMenu(menu);
                }
            }
            result.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("菜单保存错误", e);
            result.put("success", true);
            result.put("errorMsg", "对不起，操作失败！");
        }
        WriterUtil.write(response, result.toString());
    }

    // 判断是不是叶子节点
    public boolean isLeaf(String menuId) {
        boolean flag = false;
        try {
            menu = new TUserMenu();
            menu.setParentId(Integer.parseInt(menuId));
            List<TUserMenu> list = menuService.findUserMenuByParentId(Integer.parseInt(menuId));
            if (list == null || list.size() == 0) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("判断菜单是否叶子节点错误", e);
        }
        return flag;
    }

    // 判断是不是叶子节点
    public boolean isLeafEx(TUserMenu menu) {
        boolean flag = false;
        try {
            List<TUserMenu> list = new ArrayList<TUserMenu>();
            if(menu.getState().equals("closed")){
                list = menuService.findUserMenuByParentId(menu.getParentId());
            } else {
                list = menuService.findOneUserMenuByMenuId(menu.getParentId());
            }
            if (list == null || list.size() == 0) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("判断菜单是否叶子节点错误", e);
        }
        return flag;
    }

    @RequestMapping("deleteMenu")
    public void deleteMenu(HttpServletRequest request, HttpServletResponse response) {
        JSONObject result = new JSONObject();
        try {
            String menuId = request.getParameter("menuId");
            String parentId = request.getParameter("parentId");
            if (!isLeaf(menuId)) {  //不是叶子节点，说明有子菜单，不能删除
                result.put("errorMsg", "该菜单下面有子菜单，不能直接删除");
            } else {
                menu = new TUserMenu();
                menu.setParentId(Integer.parseInt(parentId));
                int sonNum = menuService.countUserMenuByParentId(Integer.parseInt(parentId));
                if (sonNum == 1) {
                    // 只有一个孩子，删除该孩子，且把父亲状态置为open
                    menu = new TUserMenu();
                    menu.setMenuId(Integer.parseInt(parentId));
                    menu.setState("open");
                    menuService.updateUserMenu(menu);

                    menuService.deleteUserMenu(Integer.parseInt(menuId));
                } else {
                    //不只一个孩子，直接删除
                    menuService.deleteUserMenu(Integer.parseInt(menuId));
                }
                result.put("success", true);
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("菜单删除错误", e);
            result.put("errorMsg", "对不起，删除失败！");
        }
        WriterUtil.write(response, result.toString());
    }

    @RequestMapping("changeMenuLevel")
    public void changeMenuLevel(HttpServletRequest request, HttpServletResponse response,
                                @ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) {
        JSONObject result = new JSONObject();
        try {
            Integer sourceId = Integer.parseInt(request.getParameter("sourceId"));
            Integer targetId = Integer.parseInt(request.getParameter("targetId"));
            String point = request.getParameter("point");
            TUserMenu sourceMenu = menuService.findOneUserMenu(sourceId);
            List<TUserMenu> sourceList = userMenuDao.queryByHql("from TUserMenu where parentId = ? ", new Object[]{sourceId});
            TUserMenu targetMenu = menuService.findOneUserMenu(targetId);
            List<TUserMenu> targetList = userMenuDao.queryByHql("from TUserMenu where parentId = ? ", new Object[]{targetId});

            StringBuffer sourceBuf = new StringBuffer();
            sourceBuf.append("," + sourceMenu.getMenuId());
            for (TUserMenu menu : sourceList) {
                sourceBuf.append("," + menu.getMenuId());
            }

            StringBuffer targetBuf = new StringBuffer();
            targetBuf.append(targetMenu.getMenuId());
            for (TUserMenu menu : targetList) {
                targetBuf.append("," + menu.getMenuId());
            }

            TUserInfo userInfo = (TUserInfo) principle.getAdmin();
            TUserRole userRole = roleService.findOneRole(userInfo.getRoleId());
            String menuIds = userRole.getMenuIds();
            int targetIndex = menuIds.indexOf(String.valueOf(targetId));
            String menuIdsTemp = menuIds.substring(0,targetIndex);
            menuIdsTemp = menuIdsTemp.replace(sourceBuf.toString(), "");
            StringBuffer menuIdsBuffer = new StringBuffer(menuIdsTemp);
            String menuReslut = menuIds;

            menuIdsBuffer.append(sourceBuf);
            if (StringUtil.isNotEmpty(menuIds)){
                String[] menuIdsArray = menuIds.split(",");
                if(StringUtil.isNotEmpty(menuIdsBuffer.toString())){
                    String[] menuIdsBufferArray = menuIdsBuffer.toString().split(",");
                    String[] resultArray = minus(menuIdsArray, menuIdsBufferArray);
                    System.out.println("menuIdsBuffer: " + menuIdsBuffer);
                    for(int i = 0; i < resultArray.length; i++){
                        menuIdsBuffer.append("," + resultArray[i]);
                    }
                    menuReslut = menuIdsBuffer.toString();
                }
            }

            menuReslut = menuReslut.replaceAll(",,",",");
            if(sourceMenu != null){
                if("append".equals(point)){
                    sourceMenu.setParentId(targetMenu.getMenuId());
                    menuService.updateUserMenu(sourceMenu);
                } else {
                    if(1 == targetMenu.getParentId()
                            && sourceMenu.getParentId() != targetMenu.getParentId()) {
                        sourceMenu.setParentId(targetMenu.getParentId());
                        menuService.updateUserMenu(sourceMenu);
                        userRole.setMenuIds(menuReslut);
                        roleService.updateRole(userRole);
                    } else {
                        sourceMenu.setParentId(targetMenu.getMenuId());
                        menuService.updateUserMenu(sourceMenu);
                        targetMenu.setState("closed");
                        menuService.updateUserMenu(targetMenu);
                    }
                }
            }
            System.out.println("sourceId: " + sourceId + ", targetId: " + targetId + ", point: " + point);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("菜单移动错误", e);
            result.put("errorMsg", "对不起，移动失败！");
        }
        WriterUtil.write(response, result.toString());
    }

    public static String[] minus(String[] arr1, String[] arr2) {
        LinkedList<String> list = new LinkedList<String>();
        LinkedList<String> history = new LinkedList<String>();
        String[] longerArr = arr1;
        String[] shorterArr = arr2;
        //找出较长的数组来减较短的数组
        if (arr1.length > arr2.length) {
            longerArr = arr2;
            shorterArr = arr1;
        }
        for (String str : longerArr) {
            if (!list.contains(str)) {
                list.add(str);
            }
        }
        for (String str : shorterArr) {
            if (list.contains(str)) {
                history.add(str);
                list.remove(str);
            } else {
                if (!history.contains(str)) {
                    list.add(str);
                }
            }
        }

        String[] result = {};
        return list.toArray(result);
    }

    public static void main(String[] args)
    {
        String s="1,2,3,4,5,6,7,21,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,53,54,55,58,59,60,70,71,72,62,63,64,65,66,";
        System.out.println(s);
        s = s.replace("70,71,72","");
        System.out.println(s);
    }
}