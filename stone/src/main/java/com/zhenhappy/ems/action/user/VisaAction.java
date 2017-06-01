package com.zhenhappy.ems.action.user;

import com.zhenhappy.ems.action.BaseAction;
import com.zhenhappy.ems.dto.*;
import com.zhenhappy.ems.entity.*;
import com.zhenhappy.ems.service.ExhibitorService;
import com.zhenhappy.ems.service.JoinerService;
import com.zhenhappy.ems.service.VisaService;
import com.zhenhappy.ems.sys.Constants;
import com.zhenhappy.system.SystemConfig;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lianghaijian on 2014-05-10.
 */
@Controller
@RequestMapping(value = "/user")
@SessionAttributes(value = Principle.PRINCIPLE_SESSION_ATTRIBUTE)
public class VisaAction extends BaseAction {

    @Autowired
    private VisaService visaService;

    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private JoinerService joinerService;

    @Autowired
    private ExhibitorService exhibitorService;

    @RequestMapping(value = "/visa/list")
    public String visaList() {
        return "/user/visa/visalist";
    }

    private static Logger log = Logger.getLogger(VisaAction.class);

    @RequestMapping(value = "/visa/index")
    public String index() {
        return "/user/visa/index";
    }

	@RequestMapping(value = "/addFromJoiner")
	public String addFromJoiner() {
		return "/user/visa/addFromJoiner";
	}

    @RequestMapping(value = "/visa/queryVisas")
    @ResponseBody
    public QueryVisaResponse queryVisa(@ModelAttribute(Principle.PRINCIPLE_SESSION_ATTRIBUTE) Principle principle) {
        QueryVisaResponse response = null;
        try {
            List<TVisa> visas = visaService.queryVisaListByEid(principle.getExhibitor().getEid());
            for(TVisa visa:visas){
                eachProperties(visa);
            }
            response = new QueryVisaResponse();
            response.setRows(visas);
            response.setResultCode(0);
        } catch (Exception e) {
            log.error("query visas error.", e);
            response.setResultCode(1);
        }
        return response;
    }

    private void eachProperties(Object model) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Field[] field = model.getClass().getDeclaredFields(); //获取实体类的所有属性，返回Field数组
        for(int j=0 ; j<field.length ; j++){ //遍历所有属性
            String name = field[j].getName(); //获取属性的名字

            //System.out.println("attribute name:"+name);
            name = name.substring(0,1).toUpperCase()+name.substring(1); //将属性的首字符大写，方便构造get，set方法
            String type = field[j].getGenericType().toString(); //获取属性的类型
            if(type.equals("class java.lang.String")){ //如果type是类类型，则前面包含"class "，后面跟类名
                Method get = model.getClass().getMethod("get"+name);
                String value = (String) get.invoke(model);
                Method set = model.getClass().getMethod("set"+name, new Class[] {String.class});
                set.invoke(model,new Object[] { replaceBlank(value) });
            }
        }
    }

    private String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    @RequestMapping(value = "/visa/saveVisa", method = RequestMethod.POST)
    public ModelAndView saveVisa(@ModelAttribute(Principle.PRINCIPLE_SESSION_ATTRIBUTE) Principle principle,
                                 @ModelAttribute SaveVisaInfoRequest visa,
                                 @RequestParam(value = "license", required = false) MultipartFile license,
                                 @RequestParam(value = "passportPageFile", required = false) MultipartFile passportPage) {
        ModelAndView modelAndView = new ModelAndView("/user/callback");
        try {
            if(visa.getJoinerId() != null){
                TVisa temp = visaService.queryVisasByJoinerId(visa.getJoinerId());

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                if(StringUtils.isNotEmpty(visa.getBirthDay())){
                    temp.setBirth(sdf.parse(visa.getBirthDay()));
                }
                if(StringUtils.isNotEmpty(visa.getExpireDate())){
                    temp.setExpDate(sdf.parse(visa.getExpireDate()));
                }
                if(StringUtils.isNotEmpty(visa.getFromDate())) {
                    temp.setFrom(sdf.parse(visa.getFromDate()));
                }
                if(StringUtils.isNotEmpty(visa.getToDate())) {
                    temp.setTo(sdf.parse(visa.getToDate()));
                }
                if(StringUtils.isNotEmpty(visa.getPassportName())) {
                    temp.setPassportName(visa.getPassportName());
                }
                if(visa.getGender() != null) {
                    temp.setGender(visa.getGender());
                }
                if(StringUtils.isNotEmpty(visa.getNationality())) {
                    temp.setNationality(visa.getNationality());
                }
                if(StringUtils.isNotEmpty(visa.getPassportNo())) {
                    temp.setPassportNo(visa.getPassportNo());
                }
                if(StringUtils.isNotEmpty(visa.getApplyFor())) {
                    temp.setApplyFor(visa.getApplyFor());
                }
                if(StringUtils.isNotEmpty(visa.getHotelAddress())) {
                    temp.setDetailedAddress(visa.getHotelAddress());
                }
                if (passportPage != null) {
                    String fileName = systemConfig.getVal(Constants.appendix_directory)+"/" + new Date().getTime() + "." + FilenameUtils.getExtension(passportPage.getOriginalFilename());
                    if (passportPage != null && passportPage.getSize() != 0) {
                        FileUtils.copyInputStreamToFile(passportPage.getInputStream(), new File(fileName));
                        temp.setPassportPage(fileName);
                    }
                }
                temp.setUpdateTime(new Date());
                if(principle.getExhibitor() != null){
                    temp.setEid(principle.getExhibitor().getEid());
                    TExhibitorInfo exhibitorInfo = exhibitorService.loadExhibitorInfoByEid(principle.getExhibitor().getEid());
                    if(principle.getExhibitor().getCountry() != 44){
                        temp.setCompanyName(exhibitorInfo.getCompanyEn());
                    }else{
                        temp.setCompanyName(exhibitorInfo.getCompany());
                    }
                    temp.setCompanyWebsite(exhibitorInfo.getWebsite());
                    temp.setAddress(exhibitorInfo.getAddress());
                    temp.setEmail(exhibitorInfo.getEmail());
                    temp.setTel(exhibitorInfo.getPhone());
                }
                temp.setStatus(1);
                visaService.saveOrUpdate(temp);
                //visaService.deleteByEidAndJoinerId(principle.getExhibitor().getEid(), visa.getJoinerId());
                modelAndView.addObject("method", "addSuccess");
            }
        } catch (Exception e) {
            log.error("add visa error",e);
            modelAndView.addObject("method", "addFailure");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/visa/saveVisa1", method = RequestMethod.POST)
    public ModelAndView saveVisa1(@ModelAttribute(Principle.PRINCIPLE_SESSION_ATTRIBUTE) Principle principle,
                                 @ModelAttribute SaveVisaInfoRequest visa,
                                 @RequestParam(value = "license", required = false) MultipartFile license,
                                 @RequestParam(value = "passportPageFile1", required = false) MultipartFile passportPage) {
        ModelAndView modelAndView = new ModelAndView("/user/callback");
        try {
            if(visa.getJoinerId() != null){
                TVisa temp = visaService.queryVisasByJoinerId(visa.getJoinerId());

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                if(StringUtils.isNotEmpty(visa.getBirthDay())){
                    temp.setBirth(sdf.parse(visa.getBirthDay()));
                }
                if(StringUtils.isNotEmpty(visa.getExpireDate())){
                    temp.setExpDate(sdf.parse(visa.getExpireDate()));
                }
                if(StringUtils.isNotEmpty(visa.getFromDate())) {
                    temp.setFrom(sdf.parse(visa.getFromDate()));
                }
                if(StringUtils.isNotEmpty(visa.getToDate())) {
                    temp.setTo(sdf.parse(visa.getToDate()));
                }
                if(StringUtils.isNotEmpty(visa.getPassportName())) {
                    temp.setPassportName(visa.getPassportName());
                }
                if(visa.getGender() != null) {
                    temp.setGender(visa.getGender());
                }
                if(StringUtils.isNotEmpty(visa.getNationality())) {
                    temp.setNationality(visa.getNationality());
                }
                if(StringUtils.isNotEmpty(visa.getPassportNo())) {
                    temp.setPassportNo(visa.getPassportNo());
                }
                if(StringUtils.isNotEmpty(visa.getApplyFor())) {
                    temp.setApplyFor(visa.getApplyFor());
                }
                if(StringUtils.isNotEmpty(visa.getHotelAddress())) {
                    temp.setDetailedAddress(visa.getHotelAddress());
                }
                if (passportPage != null) {
                    String fileName = systemConfig.getVal(Constants.appendix_directory)+"/" + new Date().getTime() + "." + FilenameUtils.getExtension(passportPage.getOriginalFilename());
                    if (passportPage != null && passportPage.getSize() != 0) {
                        FileUtils.copyInputStreamToFile(passportPage.getInputStream(), new File(fileName));
                        temp.setPassportPage(fileName);
                    }
                }
                temp.setUpdateTime(new Date());
                if(principle.getExhibitor() != null){
                    temp.setEid(principle.getExhibitor().getEid());
                    TExhibitorInfo exhibitorInfo = exhibitorService.loadExhibitorInfoByEid(principle.getExhibitor().getEid());
                    if(principle.getExhibitor().getCountry() != 44){
                        temp.setCompanyName(exhibitorInfo.getCompanyEn());
                    }else{
                        temp.setCompanyName(exhibitorInfo.getCompany());
                    }
                    temp.setCompanyWebsite(exhibitorInfo.getWebsite());
                    temp.setAddress(exhibitorInfo.getAddress());
                    temp.setEmail(exhibitorInfo.getEmail());
                    temp.setTel(exhibitorInfo.getPhone());
                }
                temp.setStatus(1);
                visaService.saveOrUpdate(temp);
                //visaService.deleteByEidAndJoinerId(principle.getExhibitor().getEid(), visa.getJoinerId());
                modelAndView.addObject("method", "addSuccess");
            }
        } catch (Exception e) {
            log.error("add visa error",e);
            modelAndView.addObject("method", "addFailure");
        }
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/visa/addJoinerToVisa", method = RequestMethod.POST)
    public BaseResponse addJoinerToVisa(@ModelAttribute(Principle.PRINCIPLE_SESSION_ATTRIBUTE) Principle principle,
                                        @RequestParam(value = "joinerid", defaultValue = "") Integer[] joinerids) {
        BaseResponse baseResponse = new BaseResponse();
        try {
            List<TExhibitorJoiner> joiners = joinerService.queryAllJoinersByIds(joinerids);
            for(TExhibitorJoiner joiner:joiners){
                joiner.setIsDelete(0);
                joinerService.saveOrUpdate(joiner);

                TVisa visa = visaService.queryVisasByJoinerId(joiner.getId());
                if(visa != null){
                    visa.setPassportName(joiner.getName());
                    visa.setEid(principle.getExhibitor().getEid());
                    visa.setJoinerId(joiner.getId());
                    if(joiner.getIsNew() != null && joiner.getIsNew() == 1) {
                        visa.setStatus(2);
                    }else{
                        visa.setStatus(0);
                    }
                    visaService.saveOrUpdate(visa);
                }else{
                    visa = new TVisa();
                    visa.setPassportName(joiner.getName());
                    visa.setEid(principle.getExhibitor().getEid());
                    visa.setJoinerId(joiner.getId());
                    visa.setStatus(2);
                    visaService.saveOrUpdate(visa);
                }
            }
            baseResponse.setResultCode(0);
        } catch (Exception e) {
            log.error("add joiner to visa error",e);
            baseResponse.setResultCode(1);
        }
        return baseResponse;
    }

    @ResponseBody
    @RequestMapping(value = "/visa/delete",method = RequestMethod.POST)
    public BaseResponse deleteVisa(@RequestParam(value = "vid") Integer vid,
                                   @ModelAttribute(Principle.PRINCIPLE_SESSION_ATTRIBUTE) Principle principle){
        BaseResponse response = new BaseResponse();
        try{
            TVisa tVisa = visaService.queryByVid(vid);
            visaService.delete(principle.getExhibitor().getEid(),vid);
            if(tVisa != null){
                Integer[] ids = new Integer[1];
                ids[0] = tVisa.getJoinerId();
                List<TExhibitorJoiner> tExhibitorJoinerList = joinerService.queryAllJoinersByIds(ids);
                for(TExhibitorJoiner tExhibitorJoiner:tExhibitorJoinerList){
                    tExhibitorJoiner.setIsDelete(1);
                    joinerService.saveOrUpdate(tExhibitorJoiner);
                }
            }
            response.setResultCode(0);
        }catch (Exception e){
            log.error("delete visa error.",e);
            response.setResultCode(1);
        }
        return response;
    }

    @RequestMapping(value = "/visa/visaZhengjian",method = RequestMethod.GET)
    public void visaPage(@RequestParam("vid") Integer vid,@RequestParam("type") Integer type,HttpServletResponse response){
        try {
            TVisa image = visaService.queryByVid(vid);
            if (image != null) {
                OutputStream outputStream = response.getOutputStream();
                File img = null;
                if(type.intValue()==1){
                    img = new File(image.getPassportPage());
                }else{
                    img = new File(image.getBusinessLicense());
                }
                if(!img.exists()){
                    return;
                }
                FileUtils.copyFile(img, outputStream);
                outputStream.close();
                outputStream.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/visa/queryVisaByJoinerID")
    @ResponseBody
    public TVisa queryVisaByJoinerID(@RequestParam("joinerid") Integer joinerid,
                                     @ModelAttribute(Principle.PRINCIPLE_SESSION_ATTRIBUTE) Principle principle) {
        TVisa visa = new TVisa();
        try {
            visa = visaService.queryVisasByJoinerId(joinerid);
        } catch (Exception e) {
            log.error("query visas error.", e);
        }
        return visa;
    }
}
