package com.zhenhappy.ems.manager.action.user;

import com.zhenhappy.ems.dto.BaseResponse;
import com.zhenhappy.ems.entity.TVisa;
import com.zhenhappy.ems.manager.action.BaseAction;
import com.zhenhappy.ems.manager.dto.*;
import com.zhenhappy.ems.manager.service.JoinerManagerService;
import com.zhenhappy.ems.manager.service.TVisaManagerService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.OutputStream;

/**
 * Created by wujianbin on 2014-11-26.
 */
@Controller
@RequestMapping(value = "user")
@SessionAttributes(value = ManagerPrinciple.MANAGERPRINCIPLE)
public class TVisaAction extends BaseAction {

    private static Logger log = Logger.getLogger(TVisaAction.class);

    @Autowired
    private TVisaManagerService tVisaManagerService;
	@Autowired
	private JoinerManagerService joinerManagerService;

	@RequestMapping(value = "tVisa")
	public ModelAndView directToExhbitorVisa() {
		ModelAndView modelAndView = new ModelAndView("user/visa/tvisa");
		return modelAndView;
	}

	/**
	 * 分页查询国外展商VISA信息
	 *
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryTVisaByPage")
	public QueryTVisaResponse queryTVisaByPage(@ModelAttribute QueryTVisaRequest request) {
		QueryTVisaResponse response = new QueryTVisaResponse();
		try {
			response = tVisaManagerService.queryTVisaByPage(request);
		} catch (Exception e) {
			response.setResultCode(1);
			log.error("query tvisa error.", e);
		}
		return response;
	}

	/**
	 * 显示护照
	 * @param response
	 * @param vid
	 */
	@RequestMapping(value = "showPassportPage", method = RequestMethod.GET)
	public void showLogo(HttpServletResponse response, @RequestParam("vid") Integer vid) {
		try {
			String logoFileName = tVisaManagerService.queryByVid(vid).getPassportPage();
			if (StringUtils.isNotEmpty(logoFileName)) {
				OutputStream outputStream = response.getOutputStream();
                if(logoFileName.toLowerCase().contains(".pdf")){
					/*response.reset();// 清空输出流
					response.setContentType("application/pdf");
					response.setHeader( "Content-disposition", "attachment;filename=" + logoFileName);*/
					response.setContentType("application/pdf");
				}
				//response.setContentType("application/pdf");
				File logo = new File(logoFileName);
				if (!logo.exists())
					return;
				FileUtils.copyFile(logo, outputStream);
				outputStream.close();
				outputStream.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据ID获取具体的VISA信息
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "tVisaDetailInfo")
	public ModelAndView directToTVisaDetailInfo(@RequestParam("id") Integer id) {
		ModelAndView modelAndView = new ModelAndView("user/visa/tvisaInfo");
		modelAndView.addObject("id", id);
		TVisa tVisa = tVisaManagerService.queryByVid(id);
		modelAndView.addObject("visaInfo", tVisa);
		if(tVisa != null && tVisa.getJoinerId() != null){
			modelAndView.addObject("joinerInfo", joinerManagerService.loadExhibitorJoinerById(tVisa.getJoinerId()));
		}
		return modelAndView;
	}

	/**
	 * 显示Logo
	 * @param response
	 * @param path
	 */
	@RequestMapping(value = "showTPassportPicture", method = RequestMethod.GET)
	public void showTPassportPicture(HttpServletResponse response, @RequestParam("path") String path) {
		try {
			if (StringUtils.isNotEmpty(path)) {
				File logo = new File("http://www.stonefair.org.cn/" + path);
				if (!logo.exists())
					return;
				OutputStream outputStream = response.getOutputStream();
				FileUtils.copyFile(logo, outputStream);
				outputStream.close();
				outputStream.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 重置展商Visa列表为初始状态，即设置status=0
	 * @param principle
	 */
	@ResponseBody
	@RequestMapping(value = "resetExhibitorVisaToDefault", method = RequestMethod.POST)
	public BaseResponse resetExhibitorVisaToDefault(@ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) {
		BaseResponse response = new BaseResponse();
		try {
			tVisaManagerService.resetExhibitorVisaToDefault();
			response.setResultCode(0);
		} catch (Exception e) {
			response.setResultCode(1);
			e.printStackTrace();
		}
		return response;
	}
}
