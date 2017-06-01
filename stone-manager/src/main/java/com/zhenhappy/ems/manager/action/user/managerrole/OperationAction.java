package com.zhenhappy.ems.manager.action.user.managerrole;

import com.alibaba.fastjson.JSONObject;
import com.zhenhappy.ems.entity.managerrole.TUserOperation;
import com.zhenhappy.ems.manager.tag.StringUtil;
import com.zhenhappy.ems.manager.tag.WriterUtil;
import com.zhenhappy.ems.service.managerrole.TUserOperationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by wangxd on 2016-05-16.
 */

@Controller
@RequestMapping("user")
public class OperationAction {
    private TUserOperation operation;
    @Autowired
    private TUserOperationService operationService;
    static Logger logger = Logger.getLogger(OperationAction.class);

    @RequestMapping("operationList")
    public void list(HttpServletRequest request, HttpServletResponse response) {
        try {
            operation.setMenuId(Integer.parseInt(request.getParameter("menuId")));
            List<TUserOperation> list = operationService.findOperationByMenuId(operation.getMenuId());
            int total = operationService.countOperation(operation);
            JSONObject jsonObj = new JSONObject();//new一个JSON
            jsonObj.put("total", total);
            jsonObj.put("rows", list);
            WriterUtil.write(response, jsonObj.toString());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("按钮展示错误", e);
        }
    }

    @RequestMapping("reserveOperation")
    public void reserveMenu(HttpServletRequest request, HttpServletResponse response, TUserOperation operation) {
        String operationId = request.getParameter("operationId");
        JSONObject result = new JSONObject();
        try {
            if (StringUtil.isNotEmpty(operationId)) {  //更新操作
                operation.setMenuId(Integer.parseInt(operationId));
                operationService.updateOperation(operation);
            } else {
                operationService.addOperation(operation);
            }
            result.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("按钮保存错误", e);
            result.put("success", true);
            result.put("errorMsg", "对不起，操作失败！");
        }
        WriterUtil.write(response, result.toString());
    }

    @RequestMapping("deleteOperation")
    public void delUser(HttpServletRequest request, HttpServletResponse response) {
        JSONObject result = new JSONObject();
        try {
            String[] ids = request.getParameter("ids").split(",");
            for (String id : ids) {
                operationService.deleteOperation(Integer.parseInt(id));
            }
            result.put("success", true);
            result.put("delNums", ids.length);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除按钮错误", e);
            result.put("errorMsg", "对不起，删除失败");
        }
        WriterUtil.write(response, result.toString());
    }
}
