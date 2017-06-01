package com.zhenhappy.ems.manager.action.user.managerrole;

import com.zhenhappy.ems.manager.action.BaseAction;
import com.zhenhappy.ems.manager.dto.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by wangxd on 2016-05-18.
 */
@Controller
@RequestMapping(value = "user")
@SessionAttributes(value = ManagerPrinciple.MANAGERPRINCIPLE)
public class ManagerMenuAction extends BaseAction {
    //前台展商时间参数设置
    @RequestMapping(value = "resetExhibitorTime")
    public String resetExhibitorTime(){
        return "user/managerreset/exhibitor_time";
    }

    //重置展商列表颜色
    @RequestMapping(value = "managerReset")
    public String managerReset(){
        return "user/managerreset/resetview";
    }

    //展商列表菜单
    @RequestMapping(value = "exhibitorIndex")
    public String exhibitorIndex(){
        return "/user/exhibitor/exhibitor";
    }

    //展团列表菜单
    @RequestMapping(value = "exhibitorGroupIndex")
    public String exhibitorGroupIndex(){
        return "/user/group/group";
    }

    //展位预向列表
    @RequestMapping(value = "intetionXicecMap")
    public String intetionXicecMap(){
        return "user/xicecmap/xicecmapintetionpage";
    }

    //国内客商菜单
    @RequestMapping(value = "inlandCustomerIndex")
    public String inlandCustomerIndex(){
        return "/user/customer/inlandCustomer";
    }

    //参观团菜单
    @RequestMapping(value="visitorGroupIndex")
    public String visitorGroupIndex(){return "user/customer/visitorGroupPage";}

    //国外客商菜单
    @RequestMapping(value = "foreignCustomerIndex")
    public String foreignCustomerIndex(){
        return "/user/customer/foreignCustomer";
    }

    //邮件发送失败列表菜单
    @RequestMapping(value = "emailSendFailurePageIndex")
    public String emailSendFailurePageIndex(){
        return "/user/customer/emailSendFailureOfCustomer";
    }

    //邮件申请列表菜单
    @RequestMapping(value = "emailApplyPageIndex")
    public String emailApplyPageIndex(){
        return "/user/customer/emailApplyPage";
    }

    //境外客商问卷调查菜单
    @RequestMapping(value = "foreignCustomerFeedBackPageIndex")
    public String foreignCustomerFeedBackPageIndex(){
        return "/user/customer/foreignCustomerFeedBack";
    }

    //境外客商问卷调查统计
    @RequestMapping(value = "foreignCustomerFeedBackReportPage")
    public String foreignCustomerFeedBackReportPage(){
        return "/user/customer/feedBackReport";
    }

    //短信申请列表菜单
    @RequestMapping(value = "msgApplyPageIndex")
    public String msgApplyPageIndex(){
        return "/user/customer/msgApplyPage";
    }

    //展商VISA菜单
    @RequestMapping(value = "exhibitorVisaIndex")
    public String exhibitorVisaIndex(){
        return "/user/visa/tvisa";
    }

    //客商VISA菜单
    @RequestMapping(value = "wcustomerVisaIndex")
    public String wcustomerVisaIndex(){
        return "/user/visa/wvisa";
    }

    //标签列表菜单
    @RequestMapping(value = "tagIndex")
    public String tagIndex(){
        return "/user/tag/tag";
    }

    //公告管理菜单
    @RequestMapping(value = "articleIndex")
    public String articleIndex(){
        return "/user/article/article";
    }

    //邮件模板管理菜单
    @RequestMapping(value = "emailTemplateIndex")
    public String emailTemplateIndex(){
        return "/user/email/emailTemplate";
    }

    //短信模板管理菜单
    @RequestMapping(value = "msgTemplateIndex")
    public String msgTemplateIndex(){
        return "/user/email/messageTemplate";
    }

    //数据报表菜单
    @RequestMapping(value = "dataReportIndex")
    public String dataReportIndex(){
        return "/user/report/dataReport";
    }

    //数据分布菜单
    @RequestMapping(value = "dataDistributeIndex")
    public String dataDistributeIndex(){
        return "/user/report/dataDistribute";
    }

    //菜单管理
    @RequestMapping("menuIndex")
    public String menuIndex(){
        return "user/managerrole/menu";
    }

    //角色管理
    @RequestMapping("roleIndex")
    public String roleIndex(){
        return "user/managerrole/role";
    }

    //用户管理
    @RequestMapping(value = "userIndex")
    public String userIndex(){
        return "user/managerrole/userinfo";
    }

    //数据备分
    @RequestMapping(value="dataBackupIndex")
    public String dataBackupIndex(){return "user/databackup/data_backuppage";}

    //展商数据备分
    @RequestMapping(value="exhibitorBackupIndex")
    public String exhibitorBackupIndex(){return "user/databackup/exhibitor_backup";}

    //VISA数据备分
    @RequestMapping(value="visaBackupIndex")
    public String visaBackupIndex(){return "user/databackup/visa_backup";}

    //客商数据备分
    @RequestMapping(value="customerBackupIndex")
    public String customerBackupIndex(){return "user/databackup/customer_backup";}
}
