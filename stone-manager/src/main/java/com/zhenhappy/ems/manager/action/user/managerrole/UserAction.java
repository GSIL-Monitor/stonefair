package com.zhenhappy.ems.manager.action.user.managerrole;

import com.alibaba.fastjson.JSONObject;
import com.zhenhappy.ems.entity.TTag;
import com.zhenhappy.ems.entity.managerrole.TUserInfo;
import com.zhenhappy.ems.manager.dto.ManagerPrinciple;
import com.zhenhappy.ems.manager.service.TagManagerService;
import com.zhenhappy.ems.manager.tag.StringUtil;
import com.zhenhappy.ems.manager.tag.WriterUtil;
import com.zhenhappy.ems.service.managerrole.TUserInfoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by wangxd on 2016-05-16.
 */

@Controller
@RequestMapping("user")
@SessionAttributes(value = ManagerPrinciple.MANAGERPRINCIPLE)
public class UserAction {
    static Logger logger = Logger.getLogger(UserAction.class);

    private TUserInfo user;
    @Autowired
    private TUserInfoService userService;
    @Autowired
    private TagManagerService tagManagerService;

    @RequestMapping("userList")
    public void userList(HttpServletRequest request, HttpServletResponse response) {
        try {
            user = new TUserInfo();
            user.setUserName(request.getParameter("userName"));
            String roleId = request.getParameter("roleId");
            if (StringUtil.isNotEmpty(roleId)) {
                user.setRoleId(Integer.parseInt(roleId));
            } else {
                user.setRoleId(null);
            }
            List<TUserInfo> userInfoList = userService.loadAllUserInfo();
            int total = userService.countUserInfo(user);
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("total", total);
            jsonObj.put("rows", userInfoList);
            WriterUtil.write(response, jsonObj.toString());
        } catch (Exception e) {
            System.out.println("--e: " + e);
            e.printStackTrace();
            logger.error("用户展示错误", e);
        }
    }

    // 新增或修改
    @RequestMapping("reserveUser")
    public void reserveUser(HttpServletRequest request, TUserInfo user, HttpServletResponse response) {
        String userId = request.getParameter("userId");
        JSONObject result = new JSONObject();
        try {
            if (StringUtil.isNotEmpty(userId)) {// userId不为空 说明是修改
                user.setId(Integer.parseInt(userId));
                userService.updateUserInfo(user);
                result.put("success", true);
            } else {   // 添加
                // 没有重复可以添加
                TUserInfo tUserInfo = userService.existUserInfoWithUserName(user.getUserName());
                if (tUserInfo == null) {
                    //若在Tag列表里有对应的姓名，则将ownerId和shareId设置成对应的tag id，表示拥有资料库
                    TTag tag = tagManagerService.loadTagByName(user.getName());
                    if(tag != null){
                        user.setOwnerId(tag.getId());
                        user.setShareId(String.valueOf(tag.getId()));
                    }else{
                        //针对国内管理员：历史数据，进行特殊处理
                        user.setOwnerId(-1);
                        user.setShareId("-1");
                    }
                    userService.addUserInfo(user);
                    result.put("success", true);
                } else {
                    result.put("success", true);
                    result.put("errorMsg", "该用户名被使用");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("保存用户信息错误", e);
            result.put("success", true);
            result.put("errorMsg", "对不起，操作失败");
        }
        WriterUtil.write(response, result.toString());
    }

    @RequestMapping("deleteUser")
    public void delUser(HttpServletRequest request, HttpServletResponse response) {
        JSONObject result = new JSONObject();
        try {
            String[] ids = request.getParameter("ids").split(",");
            for (String id : ids) {
                userService.deleteUserInfo(Integer.parseInt(id));
            }
            result.put("success", true);
            result.put("delNums", ids.length);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除用户信息错误", e);
            result.put("errorMsg", "对不起，删除失败");
        }
        WriterUtil.write(response, result.toString());
    }
}
