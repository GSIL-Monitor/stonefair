package com.zhenhappy.ems.action.user;

import com.alibaba.fastjson.JSONObject;
import com.zhenhappy.ems.action.BaseAction;
import com.zhenhappy.ems.dao.ExhibitorDao;
import com.zhenhappy.ems.dto.*;
import com.zhenhappy.ems.entity.TExhibitor;
import com.zhenhappy.ems.entity.TExhibitorInfo;
import com.zhenhappy.ems.entity.TExhibitorMeipai;
import com.zhenhappy.ems.service.*;
import com.zhenhappy.ems.sys.Constants;
import com.zhenhappy.system.SystemConfig;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by lianghaijian on 2014-04-08.
 */
@Controller
@RequestMapping(value = "user")
@SessionAttributes(value = Principle.PRINCIPLE_SESSION_ATTRIBUTE)
public class ExhibitorInfoAction extends BaseAction {

    @Autowired
    private MeipaiService meipaiService;

    @Autowired
    private ExhibitorService exhibitorService;

    @Autowired
    private ProductService productService;

    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private MsgService msgService;

    @Autowired
    private ImportExportAction importExportAction;

    private static Logger log = Logger.getLogger(ExhibitorInfoAction.class);

    @Autowired
    private ExhibitorDao exhibitorDao;

    /**
     * redirect to information manager.service page.
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "info", method = RequestMethod.GET)
    public ModelAndView redirectInfo(HttpServletRequest request, Locale locale) {
        ModelAndView modelAndView = new ModelAndView();
        Principle principle = (Principle) request.getSession().getAttribute(Principle.PRINCIPLE_SESSION_ATTRIBUTE);
        Integer exhibitorId = principle.getExhibitor().getEid();
        TExhibitorInfo exhibitorInfo = exhibitorService.loadExhibitorInfoByEid(exhibitorId);
		if (exhibitorInfo == null){
            List<ProductType> productTypes = exhibitorService.loadAllProductTypes();
            //List<ProductTypeCheck> productTypeChecks = exhibitorService.loadAllProductTypesWithCheck(exhibitorInfo.getEinfoid());
            List<ProductTypeCheck> productTypeChecks = Collections.emptyList();
            if (locale.equals(Locale.US)) {
                for (ProductType productType : productTypes) {
                    productType.setTypeName(productType.getTypeNameEN());
                    for (ProductType type : productType.getChildrenTypes()) {
                        type.setTypeName(type.getTypeNameEN());
                    }
                }
                modelAndView.setViewName("/user/info/insert_en");
            } else {
                modelAndView.setViewName("/user/info/insert");
            }
            modelAndView.addObject("types", productTypes);
            modelAndView.addObject("selected", JSONObject.toJSONString(productTypeChecks));
        } else {
            List<ProductType> productTypes = exhibitorService.loadAllProductTypes();
			List<ProductTypeCheck> productTypeChecks = null;
			if (exhibitorInfo.getCompany() == null && exhibitorInfo.getCompanyEn() == null){
				productTypeChecks = Collections.emptyList();
			}else{
            	productTypeChecks = exhibitorService.loadAllProductTypesWithCheck(exhibitorInfo.getEinfoid());
			}
            if (locale.equals(Locale.US)) {
                /*for (ProductType productType : productTypes) {
                    productType.setTypeName(productType.getTypeNameEN());
                    for (ProductType type : productType.getChildrenTypes()) {
                        type.setTypeName(type.getTypeNameEN());
                    }
                }*/
                modelAndView.setViewName("/user/info/update_en");
            } else {
                modelAndView.setViewName("/user/info/update");
            }
			TExhibitor exhibitor = exhibitorService.getExhibitorByEid(exhibitorId);
            //wangxd 2016-06-24  若账号刚激活，需要登录进基本信信界面，后台颜色才会更新
            exhibitor.setIsLogin(1);
            exhibitorDao.update(exhibitor);

            ExhibitorBooth booth = exhibitorService.loadBoothInfo(exhibitor.getEid());
            if(booth != null){
                modelAndView.addObject("booth", booth);
            }

            modelAndView.addObject("exhibitor", exhibitor);
			modelAndView.addObject("area", exhibitor.getArea());
            modelAndView.addObject("types", productTypes);
            modelAndView.addObject("selected", JSONObject.toJSONString(productTypeChecks));
            modelAndView.addObject("updateInfo", exhibitorInfo);
        }
        return modelAndView;
    }

    @RequestMapping(value = "highlight", method = RequestMethod.GET)
    public ModelAndView redirectHighlight(HttpServletRequest request, Locale locale) {
        ModelAndView modelAndView = new ModelAndView();
        Principle principle = (Principle) request.getSession().getAttribute(Principle.PRINCIPLE_SESSION_ATTRIBUTE);
        Integer exhibitorId = principle.getExhibitor().getEid();
        TExhibitorInfo exhibitorInfo = exhibitorService.loadExhibitorInfoByEid(exhibitorId);
        if (exhibitorInfo == null){
            List<ProductType> productTypes = exhibitorService.loadAllProductTypes();
            List<ProductTypeCheck> productTypeChecks = Collections.emptyList();
            if (locale.equals(Locale.US)) {
                for (ProductType productType : productTypes) {
                    productType.setTypeName(productType.getTypeNameEN());
                    for (ProductType type : productType.getChildrenTypes()) {
                        type.setTypeName(type.getTypeNameEN());
                    }
                }
                modelAndView.setViewName("/user/spotlight/highlight");
            } else {
                modelAndView.setViewName("/user/spotlight/highlight");
            }
            modelAndView.addObject("types", productTypes);
            modelAndView.addObject("selected", JSONObject.toJSONString(productTypeChecks));
        } else {
            List<ProductType> productTypes = exhibitorService.loadAllProductTypes();
            List<ProductTypeCheck> productTypeChecks = null;
            if (exhibitorInfo.getCompany() == null && exhibitorInfo.getCompanyEn() == null){
                productTypeChecks = Collections.emptyList();
            }else{
                productTypeChecks = exhibitorService.loadAllProductTypesWithCheck(exhibitorInfo.getEinfoid());
            }
            if (locale.equals(Locale.US)) {
                modelAndView.setViewName("/user/spotlight/highlight");
            } else {
                modelAndView.setViewName("/user/spotlight/highlight");
            }
            TExhibitor exhibitor = exhibitorService.getExhibitorByEid(exhibitorId);
            ExhibitorBooth booth = exhibitorService.loadBoothInfo(exhibitor.getEid());
            if(booth != null){
                modelAndView.addObject("booth", booth);
            }

            modelAndView.addObject("exhibitor", exhibitor);
            modelAndView.addObject("area", exhibitor.getArea());
            modelAndView.addObject("types", productTypes);
            modelAndView.addObject("selected", JSONObject.toJSONString(productTypeChecks));
            modelAndView.addObject("updateInfo", exhibitorInfo);
        }
        return modelAndView;
    }

    @RequestMapping(value = "saveHighlight", method = RequestMethod.POST)
    public ModelAndView saveHighlight(@ModelAttribute TExhibitorInfo exhibitorInfo, HttpServletRequest request, Locale locale) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        try {
            Principle principle = (Principle) request.getSession().getAttribute(Principle.PRINCIPLE_SESSION_ATTRIBUTE);
            exhibitorInfo.setEid(principle.getExhibitor().getEid());
            TExhibitorInfo info = exhibitorService.loadExhibitorInfoByEid(exhibitorInfo.getEid());
            info.setCompany_hignlight(exhibitorInfo.getCompany_hignlight());

            info.setAdminUpdateTime(new Date());
            info.setAdminUser(info.getAdminUser());
            info.setUpdateTime(new Date());
            exhibitorService.update(info);
            if (locale.equals(Locale.US)) {
                modelAndView.setViewName("/user/spotlight/highlight");
                modelAndView.addObject("alert", "Modify Success");
            } else {
                modelAndView.addObject("alert", "修改成功");
                modelAndView.setViewName("/user/spotlight/highlight");
            }
            modelAndView.addObject("updateInfo", exhibitorInfo);
            modelAndView.addObject("redirect", "/user/highlight");
        } catch (Exception e) {
            log.error("update exhibitor highlight information error.", e);
        }
        return modelAndView;
    }

    /**
     * create current user's information
     *
     * @return
     */
    @RequestMapping(value = "info", method = RequestMethod.POST)
    public ModelAndView addInfo(@ModelAttribute(value = "info") TExhibitorInfo exhibitorInfo, @RequestParam("companyLogo") MultipartFile companyLogo, HttpServletRequest request, HttpServletResponse response, Locale locale) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        try {
            Principle principle = (Principle) request.getSession().getAttribute(Principle.PRINCIPLE_SESSION_ATTRIBUTE);
            exhibitorInfo.setEid(principle.getExhibitor().getEid());
            String fileName = systemConfig.getVal(Constants.appendix_directory) + "/" + new Date().getTime() + "." + FilenameUtils.getExtension(companyLogo.getOriginalFilename());
            FileUtils.copyInputStreamToFile(companyLogo.getInputStream(), new File(fileName));
            exhibitorInfo.setLogo(fileName);
            exhibitorInfo.setIsDelete(0);
            exhibitorInfo.setCreateTime(new Date());
            exhibitorService.insert(exhibitorInfo);
            exhibitorService.saveExhibitorProductClass(JSONObject.parseObject(exhibitorInfo.getClassjson(), SaveExhibitorSelectRequest.class).getSelected(), exhibitorInfo.getEinfoid());
            List<ProductType> productTypes = exhibitorService.loadAllProductTypes();
            List<ProductTypeCheck> productTypeChecks = exhibitorService.loadAllProductTypesWithCheck(exhibitorInfo.getEinfoid());
            if (locale.equals(Locale.US)) {
                /*for (ProductType productType : productTypes) {
                    productType.setTypeName(productType.getTypeNameEN());
                    for (ProductType type : productType.getChildrenTypes()) {
                        type.setTypeName(type.getTypeNameEN());
                    }
                }*/
                modelAndView.setViewName("/user/info/update_en");
                modelAndView.addObject("alert", "Add Successfully!");
            } else {
                modelAndView.addObject("alert", "添加成功");
                modelAndView.setViewName("/user/info/update");
            }
            modelAndView.addObject("types", productTypes);
            modelAndView.addObject("selected", JSONObject.toJSONString(productTypeChecks));
            modelAndView.addObject("info", exhibitorInfo);
        } catch (Exception e) {
            log.error("create exhibitor information error.", e);
            throw new IllegalArgumentException("数据填写错误");
        }
        return modelAndView;
    }

    /**
     * update information
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "updateinfo", method = RequestMethod.POST)
    public ModelAndView updateInfo(@ModelAttribute TExhibitorInfo exhibitorInfo,
                                   @RequestParam("companyLogo") MultipartFile companyLogo,
                                   HttpServletRequest request,
                                   HttpServletResponse response,
                                   Locale locale) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        try {
            Principle principle = (Principle) request.getSession().getAttribute(Principle.PRINCIPLE_SESSION_ATTRIBUTE);
            TExhibitor exhibitor = exhibitorService.getExhibitorByEid(principle.getExhibitor().getEid());
            exhibitorInfo.setEid(principle.getExhibitor().getEid());
            if (companyLogo != null && companyLogo.getSize() != 0) {
                if(companyLogo.getSize()/1024 > 1024) {
                    if (locale.equals(Locale.US)) {
                        modelAndView.setViewName("/user/info/update_en");
                        modelAndView.addObject("alert", "The file size can not be more than 1M");
                    } else {
                        modelAndView.setViewName("/user/info/update");
                        modelAndView.addObject("alert", "上传图片超过1M");
                    }
                    modelAndView.addObject("redirect", "/user/info");
                    return modelAndView;
                } else {
                    String fileName = systemConfig.getVal(Constants.appendix_directory) + "/" + new Date().getTime() + "." + FilenameUtils.getExtension(companyLogo.getOriginalFilename());
                    FileUtils.copyInputStreamToFile(companyLogo.getInputStream(), new File(fileName));
                    exhibitorInfo.setLogo(fileName);
                }
            }
            TExhibitorInfo info = exhibitorService.loadExhibitorInfoByEid(exhibitorInfo.getEid());
            info.setPhone(exhibitorInfo.getPhone());
            info.setFax(exhibitorInfo.getFax());
            info.setEmail(exhibitorInfo.getEmail());
            info.setWebsite(exhibitorInfo.getWebsite());
            info.setAddress(exhibitorInfo.getAddress());
            info.setAddressEn(exhibitorInfo.getAddressEn());
            info.setZipcode(exhibitorInfo.getZipcode());
            info.setMainProduct(exhibitorInfo.getMainProduct());
            info.setMainProductEn(exhibitorInfo.getMainProductEn());
            info.setMeipai(exhibitorInfo.getMeipai());
            info.setMeipaiEn(exhibitorInfo.getMeipaiEn());
            info.setLogo(exhibitorInfo.getLogo());
            info.setAdminUpdateTime(info.getAdminUpdateTime());
            info.setAdminUser(info.getAdminUser());
            info.setUpdateTime(new Date());
            exhibitorService.update(info);
            exhibitorService.saveExhibitorProductClass(JSONObject.parseObject(exhibitorInfo.getClassjson(), SaveExhibitorSelectRequest.class).getSelected(), exhibitorInfo.getEinfoid());
            List<ProductType> productTypes = exhibitorService.loadAllProductTypes();
            List<ProductTypeCheck> productTypeChecks = exhibitorService.loadAllProductTypesWithCheck(exhibitorInfo.getEinfoid());
            if (locale.equals(Locale.US)) {
                modelAndView.setViewName("/user/info/update_en");
                modelAndView.addObject("alert", "Modify Success");
            } else {
                modelAndView.addObject("alert", "修改成功");
                modelAndView.setViewName("/user/info/update");
            }
            modelAndView.addObject("types", productTypes);
            modelAndView.addObject("selected", JSONObject.toJSONString(productTypeChecks));
            modelAndView.addObject("updateInfo", exhibitorInfo);
            modelAndView.addObject("redirect", "/user/info");
            modelAndView.addObject("exhibitor", exhibitor);
        } catch (Exception e) {
            log.error("update exhibitor information error.", e);
        }
        return modelAndView;
    }

    @RequestMapping(value = "showLogo", method = RequestMethod.GET)
    public void showLogo(HttpServletResponse response, @RequestParam("eid") Integer eid) {
        try {
            String logoFileName = exhibitorService.loadExhibitorInfoByEid(eid).getLogo();
            if (StringUtils.isNotEmpty(logoFileName)) {
                File logo = new File(logoFileName);
                if (!logo.exists()) return;
                OutputStream outputStream = response.getOutputStream();
                FileUtils.copyFile(logo, outputStream);
                outputStream.close();
                outputStream.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "uploadCompanyLogo", method={RequestMethod.POST,RequestMethod.GET})
    public BaseResponse uploadCompanyLogo(@RequestParam MultipartFile inputLogo,
                                          @ModelAttribute HttpServletRequest request,
                                          @RequestParam("eid") Integer eid) {
        BaseResponse response = new BaseResponse();
        try {
            if(eid != null){
                TExhibitorInfo info = exhibitorService.loadExhibitorInfoByEid(eid);
                String oldLogoPath = info.getLogo();
                System.out.println("--oldLogoPath: " + oldLogoPath + "--eid: " + eid);
                File logo = importExportAction.upload(inputLogo, null, null);
                if(StringUtils.isNotEmpty(oldLogoPath)){
                    File oldLogo = new File(oldLogoPath);
                    if(oldLogo.exists() == false) FileUtils.deleteQuietly(oldLogo);
                }
                info.setLogo(logo.getPath());
                exhibitorService.update(info);
            }else{
                throw new Exception();
            }
        } catch (Exception e) {
            log.error("update company logo error.", e);
            response.setResultCode(1);
        }
        return response;
    }

    /**
     * open exhibitor manage page.
     *
     * @return
     */
    @RequestMapping(value = "exhibitorclass", method = RequestMethod.GET)
    public ModelAndView exhibitorClass(HttpServletRequest request, Locale locale) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        try {
            Principle principle = (Principle) request.getSession().getAttribute(Principle.PRINCIPLE_SESSION_ATTRIBUTE);
            Integer exhibitorId = principle.getExhibitor().getEid();
            TExhibitorInfo exhibitorInfo = exhibitorService.loadExhibitorInfoByEid(exhibitorId);
            if (exhibitorInfo == null) {
                modelAndView.addObject("alert", "请先填写展商基本信息");
                modelAndView.setViewName("/user/info/insert");
            } else {
                modelAndView.setViewName("/user/info/class");
                List<ProductType> productTypes = exhibitorService.loadAllProductTypes();
                List<ProductTypeCheck> productTypeChecks = exhibitorService.loadAllProductTypesWithCheck(exhibitorInfo.getEinfoid());
                if (locale.equals(Locale.US)) {
                    for (ProductType productType : productTypes) {
                        productType.setTypeName(productType.getTypeNameEN());
                        for (ProductType type : productType.getChildrenTypes()) {
                            type.setTypeName(type.getTypeNameEN());
                        }
                    }
                }
                modelAndView.addObject("types", productTypes);
                modelAndView.addObject("selected", JSONObject.toJSONString(productTypeChecks));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "exhibitorclass", method = RequestMethod.POST)
    public BaseResponse saveExhibitorClass(@RequestBody SaveExhibitorSelectRequest saveExhibitorSelectRequest, HttpServletRequest request) {
        BaseResponse response = new BaseResponse();
        try {
            Principle principle = (Principle) request.getSession().getAttribute(Principle.PRINCIPLE_SESSION_ATTRIBUTE);
            Integer exhibitorId = principle.getExhibitor().getEid();
            TExhibitorInfo exhibitorInfo = exhibitorService.loadExhibitorInfoByEid(exhibitorId);
            exhibitorService.saveExhibitorProductClass(saveExhibitorSelectRequest.getSelected(), exhibitorInfo.getEinfoid());
        } catch (Exception e) {
            log.error("save exhibitor product type error.", e);
            response.setResultCode(1);
            response.setDescription("保存失败");
        }
        return response;
    }

    /**
     * load exhibitor image.
     *
     * @param response
     * @param eid
     */
    @RequestMapping(value = "logoImg", method = RequestMethod.GET)
    public void logoImg(HttpServletResponse response, @RequestParam("eid") Integer eid) {
        try {
            String logoFileName = exhibitorService.loadExhibitorInfoByEid(eid).getLogo();
            if (StringUtils.isNotEmpty(logoFileName)) {
                OutputStream outputStream = response.getOutputStream();
                File logo = new File(logoFileName);
                if (!logo.exists()) {
                    return;
                }
                FileUtils.copyFile(new File(logoFileName), outputStream);
                outputStream.close();
                outputStream.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "main", method = RequestMethod.GET)
    public ModelAndView main(HttpServletRequest request) {
        Principle principle = (Principle) request.getSession().getAttribute(Principle.PRINCIPLE_SESSION_ATTRIBUTE);
        Long count = msgService.countUnreadMessage(principle.getExhibitor().getEid());
        ModelAndView modelAndView = new ModelAndView("/user/main");
        modelAndView.addObject("unReadMessageCount", count);
        return modelAndView;
    }

    @RequestMapping(value = "password", method = RequestMethod.GET)
    public ModelAndView redirectPassword() throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView.setViewName("user/info/modifyPassword");
        } catch (Exception e) {
            throw e;
        }
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "modifyPassword", method = RequestMethod.POST)
    public BaseResponse modifyPassword(@RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword, HttpServletRequest request) {
        Principle principle = (Principle) request.getSession().getAttribute(Principle.PRINCIPLE_SESSION_ATTRIBUTE);
        Integer exhibitorId = principle.getExhibitor().getEid();
        BaseResponse response = new BaseResponse();
        try {
            exhibitorService.modifyPassword(exhibitorId, newPassword, oldPassword);
        } catch (Exception e) {
            response.setResultCode(1);
            response.setDescription(e.getMessage());
        }
        return response;
    }

    @RequestMapping(value = "banners", method = RequestMethod.GET)
    public ModelAndView bannerPrint(@ModelAttribute(Principle.PRINCIPLE_SESSION_ATTRIBUTE) Principle principle) {
        ModelAndView modelAndView = new ModelAndView("/user/banner/index");
        modelAndView.addObject("eid", principle.getExhibitor().getEid());
        return modelAndView;
    }

    @RequestMapping(value = "preview")
    public ModelAndView preview(@ModelAttribute(Principle.PRINCIPLE_SESSION_ATTRIBUTE) Principle principle) {
        ModelAndView modelAndView = new ModelAndView("/user/preview/preview");
        Integer exhibitorId = principle.getExhibitor().getEid();
        TExhibitorInfo exhibitorInfo = exhibitorService.loadExhibitorInfoByEid(exhibitorId);
        modelAndView.addObject("info", exhibitorInfo);
        if (exhibitorInfo != null) {
            modelAndView.addObject("products", productService.previewProducts(exhibitorInfo.getEinfoid()));
        }
        return modelAndView;
    }

    @RequestMapping(value = "addMeipai", method = RequestMethod.GET)
    public ModelAndView addMeipai(@ModelAttribute(Principle.PRINCIPLE_SESSION_ATTRIBUTE) Principle principle) {
        ModelAndView modelAndView = null;
        Integer exhibitorId = principle.getExhibitor().getEid();
        TExhibitorMeipai meipai = meipaiService.getMeiPaiByEid(principle.getExhibitor().getEid());
        if (meipai == null) {
            modelAndView = new ModelAndView("user/meipai/add");
        } else {
            modelAndView = new ModelAndView("user/meipai/update");
            modelAndView.addObject("meipai", meipai);
        }
        return modelAndView;
    }

    @RequestMapping(value = "addMeipai", method = RequestMethod.POST)
    public ModelAndView saveMeipai(@ModelAttribute(Principle.PRINCIPLE_SESSION_ATTRIBUTE) Principle principle, @RequestParam(value = "name", required = false) String name, @RequestParam(value = "ename", required = false) String ename, @RequestParam(value = "mid", required = false) Integer mid, Locale locale) {
        ModelAndView modelAndView = null;
        Integer exhibitorId = principle.getExhibitor().getEid();
        TExhibitorMeipai meipai = new TExhibitorMeipai();
        meipai.setExhibitorId(exhibitorId);
        meipai.setMeipai(name);
        meipai.setMeipaiEn(ename);
        meipai.setCreateTime(new Date());
        meipai.setUpdateTime(new Date());
        meipai.setId(mid);
        meipaiService.saveOrUpdate(meipai);
        modelAndView = new ModelAndView("user/meipai/update");
        if (locale.equals(Locale.US)) {
            modelAndView.addObject("alert", "Operation success!");
        } else {
            modelAndView.addObject("alert", "操作成功！");
        }
        modelAndView.addObject("meipai", meipai);
        return modelAndView;
    }
}
