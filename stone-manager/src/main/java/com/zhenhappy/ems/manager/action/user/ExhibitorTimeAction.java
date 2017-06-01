package com.zhenhappy.ems.manager.action.user;

import com.zhenhappy.ems.dto.BaseResponse;
import com.zhenhappy.ems.entity.TTag;
import com.zhenhappy.ems.manager.action.BaseAction;
import com.zhenhappy.ems.manager.dto.*;
import com.zhenhappy.ems.manager.exception.DuplicateTagException;
import com.zhenhappy.ems.manager.service.TagManagerService;
import com.zhenhappy.ems.stonetime.ExhibitorTimeDao;
import com.zhenhappy.ems.stonetime.ExhibitorTimeManagerService;
import com.zhenhappy.ems.stonetime.TExhibitorTime;
import com.zhenhappy.util.Page;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wangxd on 2016-06-28.
 */
@Controller
@RequestMapping(value = "user")
@SessionAttributes(value = ManagerPrinciple.MANAGERPRINCIPLE)
public class ExhibitorTimeAction extends BaseAction {
	
	@Autowired
    private ExhibitorTimeManagerService exhibitorTimeManagerService;

    @Autowired
    private ExhibitorTimeDao exhibitorTimeDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;
	
    private static Logger log = Logger.getLogger(ExhibitorTimeAction.class);
    
    /**
     * 分页查询所有时间对象
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryAllExhibitorTime")
    public QueryExhibitorTimeResponse queryAllExhibitorTime(@ModelAttribute QueryExhibitorTimeRequest request) {
        QueryExhibitorTimeResponse response = new QueryExhibitorTimeResponse();
        try {
            Page page = new Page();
            page.setPageSize(request.getRows());
            page.setPageIndex(request.getPage());
            List<TExhibitorTime> tExhibitorTimes = exhibitorTimeDao.queryPageByHQL("select count(*) from TExhibitorTime", "from TExhibitorTime", new Object[]{}, page);
            response.setResultCode(0);
            response.setRows(tExhibitorTimes);
            response.setTotal(page.getTotalCount());
        } catch (Exception e) {
            response.setResultCode(1);
            log.error("query exhibitor time error.", e);
        }
        return response;
    }

    /**
     * 更新时间对象
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "modifyExhibitorTime")
    public BaseResponse modifyExhibitorTime(@ModelAttribute QueryExhibitorTimeRequest request,
                                            @RequestParam(value = "company_Info_Submit_Deadline_Zh", defaultValue = "") String company_Info_Submit_Deadline_Zh,
                                            @RequestParam(value = "company_Info_Submit_Deadline_En", defaultValue = "") String company_Info_Submit_Deadline_En,
                                            @RequestParam(value = "participant_List_Submit_Deadline_Zh", defaultValue = "") String participant_List_Submit_Deadline_Zh,
                                            @RequestParam(value = "participant_List_Submit_Deadline_En", defaultValue = "") String participant_List_Submit_Deadline_En,
                                            @RequestParam(value = "invoice_Information_Submit_Deadline_Zh", defaultValue = "") String invoice_Information_Submit_Deadline_Zh,
                                            @RequestParam(value = "invoice_Information_Submit_Deadline_En", defaultValue = "") String invoice_Information_Submit_Deadline_En,
                                            @RequestParam(value = "advertisement_Submit_Deadline_Zh", defaultValue = "") String advertisement_Submit_Deadline_Zh,
                                            @RequestParam(value = "advertisement_Submit_Deadline_En", defaultValue = "") String advertisement_Submit_Deadline_En,
                                            @RequestParam(value = "company_Info_Insert_Submit_Deadline_Zh", defaultValue = "") String company_Info_Insert_Submit_Deadline_Zh,
                                            @RequestParam(value = "company_Info_Insert_Submit_Deadline_En", defaultValue = "") String company_Info_Insert_Submit_Deadline_En,
                                            @RequestParam(value = "stone_fair_end_year", defaultValue = "") String stone_fair_end_year,
                                            @RequestParam(value = "stone_fair_begin_year", defaultValue = "") String stone_fair_begin_year,
                                            @RequestParam(value = "company_Info_Data_End_Html", defaultValue = "") String company_Info_Data_End_Html,
                                            @RequestParam(value = "visa_Info_Submit_Deadline_Zh", defaultValue = "") String visa_Info_Submit_Deadline_Zh,
                                            @RequestParam(value = "visa_Info_Submit_Deadline_En", defaultValue = "") String visa_Info_Submit_Deadline_En,
                                            @RequestParam(value = "stone_Fair_Show_Date_Zh", defaultValue = "") String stone_Fair_Show_Date_Zh,
                                            @RequestParam(value = "stone_Fair_Show_Date_En", defaultValue = "") String stone_Fair_Show_Date_En,
                                            @RequestParam(value = "exhibitor_end_time", defaultValue = "") String exhibitor_end_time,
                                            @RequestParam(value = "exhibitor_area") Integer exhibitor_area) {
        BaseResponse response = new BaseResponse();
        try {
            exhibitorTimeManagerService.modifyExhibitorTime(company_Info_Submit_Deadline_Zh, company_Info_Submit_Deadline_En, participant_List_Submit_Deadline_Zh,
                    participant_List_Submit_Deadline_En, invoice_Information_Submit_Deadline_Zh, invoice_Information_Submit_Deadline_En,
                    advertisement_Submit_Deadline_Zh, advertisement_Submit_Deadline_En, company_Info_Insert_Submit_Deadline_Zh, company_Info_Insert_Submit_Deadline_En,
                    stone_fair_end_year, stone_fair_begin_year, company_Info_Data_End_Html, visa_Info_Submit_Deadline_Zh, visa_Info_Submit_Deadline_En,
                    stone_Fair_Show_Date_Zh, stone_Fair_Show_Date_En, exhibitor_end_time, exhibitor_area);
            response.setResultCode(0);
        } catch (Exception e) {
            log.error("modify exhibitor time error.", e);
            response.setResultCode(1);
        }
        return response;
    }

    /**
     * 更新菜单是否移动
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "modifyExhibitorMenuMove")
    public BaseResponse modifyExhibitorMenuMove(@ModelAttribute QueryExhibitorTimeRequest request,
                                                @RequestParam(value = "menu_move_switch") Integer menu_move_switch,
                                                @RequestParam(value = "exhibitor_area") Integer exhibitor_area) {
        BaseResponse response = new BaseResponse();
        try {
            exhibitorTimeManagerService.modifyExhibitorMenuMove(menu_move_switch, exhibitor_area);
            response.setResultCode(0);
        } catch (Exception e) {
            log.error("modify exhibitor time error.", e);
            response.setResultCode(1);
        }
        return response;
    }

    /**
     * 获取菜单是否移动值
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getCurrentMenuMove")
    public BaseResponse getCurrentMenuMove(@ModelAttribute QueryExhibitorTimeRequest request) {
        BaseResponse response = new BaseResponse();
        try {
            List<TExhibitorTime> tExhibitorTimes = exhibitorTimeDao.queryByHql("from TExhibitorTime", new Object[]{});
            if(tExhibitorTimes != null && tExhibitorTimes.size()>0){
                //加载前台界面菜单项是否支持移动
                TExhibitorTime tExhibitorTime = tExhibitorTimes.get(0);
                int menuMoveFlag = tExhibitorTime.getMenu_move_switch();
                response.setDescription(String.valueOf(menuMoveFlag));
            }
            response.setResultCode(0);
        } catch (Exception e) {
            log.error("query exhibitor time error.", e);
            response.setResultCode(1);
        }
        return response;
    }
}
