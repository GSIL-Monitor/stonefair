package com.zhenhappy.ems.action.user;

import com.zhenhappy.ems.action.BaseAction;
import com.zhenhappy.ems.dto.BaseResponse;
import com.zhenhappy.ems.dto.Principle;
import com.zhenhappy.ems.entity.TExhibitorInfo;
import com.zhenhappy.ems.entity.TInvoiceApplyExtend;
import com.zhenhappy.ems.service.ExhibitorService;
import com.zhenhappy.ems.service.InvoiceExtendService;
import com.zhenhappy.ems.sys.Constants;
import com.zhenhappy.system.SystemConfig;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.OutputStream;
import java.util.Date;
import java.util.Locale;

/**
 * Created by lianghaijian on 2014-05-13.
 */
@Controller
@RequestMapping(value = "/user/invoice")
@SessionAttributes(value = Principle.PRINCIPLE_SESSION_ATTRIBUTE)
public class InvoiceAction extends BaseAction {
    private static Logger log = Logger.getLogger(InvoiceAction.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private InvoiceExtendService invoiceService;
    @Autowired
    private SystemConfig systemConfig;

    @RequestMapping(value = "index")
    public ModelAndView index(@ModelAttribute(Principle.PRINCIPLE_SESSION_ATTRIBUTE) Principle principle,
                              HttpServletRequest httpServletRequest) {
        TInvoiceApplyExtend invoiceApply = invoiceService.getByEid(principle.getExhibitor().getEid());
        if (invoiceApply == null) {
            ModelAndView modelAndView = new ModelAndView("/user/invoice/index");
            return modelAndView;
        } else {
            try {
                invoiceApply.setExhibitorNo(jdbcTemplate.queryForObject("select booth_number from [t_exhibitor_booth] where eid = ?", new Object[]{principle.getExhibitor().getEid()}, java.lang.String.class));
            } catch (Exception e) {
                e.printStackTrace();
            }
            ModelAndView modelAndView = new ModelAndView("/user/invoice/update");
            modelAndView.addObject("invoice", invoiceApply);
            return modelAndView;
        }
    }

    /**
     * load exhibitor image.
     *
     * @param response
     * @param eid
     */
    @RequestMapping(value = "loadInvoiceImg", method = RequestMethod.GET)
    public void loadInvoiceImg(HttpServletResponse response, @RequestParam("eid") Integer eid) {
        try {
            String logoFileName = invoiceService.getByEid(eid).getInvoice_image_address();
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

    @ResponseBody
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public BaseResponse save(@RequestParam("title") String title,
                             @RequestParam("exhibitorNo") String exhibitorNo,
                             @RequestParam("invoiceNo") String invoiceNo,
                             @RequestParam(value = "id",required = false) Integer id,
                             @ModelAttribute(Principle.PRINCIPLE_SESSION_ATTRIBUTE) Principle principle) {
        BaseResponse response = new BaseResponse();
        try {
            TInvoiceApplyExtend apply = new TInvoiceApplyExtend();
            apply.setId(id);
            apply.setCreateTime(new Date());
            apply.setEid(principle.getExhibitor().getEid());
            try {
                apply.setExhibitorNo(jdbcTemplate.queryForObject("select booth_number from [t_exhibitor_booth] where eid = ?", new Object[]{principle.getExhibitor().getEid()}, java.lang.String.class));
            } catch (Exception e) {
                e.printStackTrace();
            }
            apply.setTitle(title);
            apply.setInvoiceNo(invoiceNo);
            invoiceService.create(apply);
            response.setResultCode(0);
        } catch (Exception e) {
            response.setResultCode(1);
        }
        return response;
    }

    @ResponseBody
    @RequestMapping(value = "setNoInvoice")
    public BaseResponse setNoInvoice(@ModelAttribute(Principle.PRINCIPLE_SESSION_ATTRIBUTE) Principle principle,
                                     @RequestParam(value = "id",required = false) Integer id) {
        BaseResponse response = new BaseResponse();
        TInvoiceApplyExtend invoiceApply = invoiceService.getByEid(principle.getExhibitor().getEid());
        try{
            if (invoiceApply == null) {
                invoiceApply = new TInvoiceApplyExtend();
                invoiceApply.setId(id);
                invoiceApply.setCreateTime(new Date());
                invoiceApply.setEid(principle.getExhibitor().getEid());
                invoiceApply.setInvoice_flag(0);
                invoiceService.create(invoiceApply);
            } else {
                invoiceApply.setInvoice_flag(0);
                invoiceService.update(invoiceApply);
            }
            response.setResultCode(0);
        }catch (Exception e){
            response.setResultCode(1);
        }
        return response;
    }

    @ResponseBody
    @RequestMapping(value = "queryInvoice")
    public TInvoiceApplyExtend queryInvoice(@ModelAttribute(Principle.PRINCIPLE_SESSION_ATTRIBUTE) Principle principle) {
        TInvoiceApplyExtend tInvoiceApplyExtend = new TInvoiceApplyExtend();
        try{
            tInvoiceApplyExtend = invoiceService.getByEid(principle.getExhibitor().getEid());
            if(tInvoiceApplyExtend == null){
                tInvoiceApplyExtend = new TInvoiceApplyExtend();
            }
        }catch (Exception e){
            log.error("query invoice extend error.", e);
        }
        return tInvoiceApplyExtend;
    }

    @ResponseBody
    @RequestMapping(value = "saveNormalInvoice", method = RequestMethod.POST)
    public BaseResponse saveNormalInvoice(@RequestParam("invoiceExhibitorNum") String invoiceExhibitorNum,
                                          @RequestParam("invoiceTitle") String invoiceTitle,
                                          @RequestParam("invoiceTaxNum") String invoiceTaxNum,
                                          Locale locale,
                                          @ModelAttribute(Principle.PRINCIPLE_SESSION_ATTRIBUTE) Principle principle) {
        BaseResponse response = new BaseResponse();

        TInvoiceApplyExtend invoiceApply = invoiceService.getByEid(principle.getExhibitor().getEid());
        try{
            if (invoiceApply == null) {
                invoiceApply = new TInvoiceApplyExtend();
                invoiceApply.setInvoice_flag(1);
                if(!invoiceExhibitorNum.equalsIgnoreCase(jdbcTemplate.queryForObject("select booth_number from [t_exhibitor_booth] where eid = ?",
                        new Object[]{principle.getExhibitor().getEid()}, java.lang.String.class))){
                    if (locale != null && ("en").equals(locale.getLanguage())) {
                        response.setDescription("Exhibitor number is wrong!");
                    } else {
                        response.setDescription("展位号输入错误！");
                    }
                    response.setResultCode(1);
                    return response;
                }else{
                    invoiceApply.setExhibitorNo(invoiceExhibitorNum);
                    invoiceApply.setCreateTime(new Date());
                    invoiceApply.setEid(principle.getExhibitor().getEid());
                    invoiceApply.setTitle(invoiceTitle);
                    invoiceApply.setInvoiceNo(invoiceTaxNum);
                    invoiceService.create(invoiceApply);
                }
            } else {
                invoiceApply.setInvoice_flag(1);
                if(!invoiceExhibitorNum.equalsIgnoreCase(jdbcTemplate.queryForObject("select booth_number from [t_exhibitor_booth] where eid = ?",
                        new Object[]{principle.getExhibitor().getEid()}, java.lang.String.class))){
                    if (locale != null && ("en").equals(locale.getLanguage())) {
                        response.setDescription("Exhibitor number is wrong!");
                    } else {
                        response.setDescription("展位号输入错误！");
                    }
                    response.setResultCode(1);
                    return response;
                }else{
                    invoiceApply.setExhibitorNo(invoiceExhibitorNum);
                    invoiceApply.setTitle(invoiceTitle);
                    invoiceApply.setInvoiceNo(invoiceTaxNum);
                    invoiceApply.setCreateTime(new Date());
                    invoiceService.update(invoiceApply);
                }
            }
            response.setResultCode(0);
        }catch (Exception e){
            response.setResultCode(1);
        }
        return response;
    }

    @ResponseBody
    @RequestMapping(value = "saveGeneralInvoice", method = RequestMethod.POST)
    public BaseResponse saveGeneralInvoice(@RequestParam("invoice_company") String invoice_company,
                                          @RequestParam("invoice_taxpayer_no") String invoice_taxpayer_no,
                                          @RequestParam("invoice_bank_name") String invoice_bank_name,
                                           @RequestParam("invoice_bank_account") String invoice_bank_account,
                                           @RequestParam("invoice_company_address") String invoice_company_address,
                                           @RequestParam("invoice_company_tel") String invoice_company_tel,
                                           @RequestParam("invoice_company_contact") String invoice_company_contact,
                                           @RequestParam("invoiceTaxer") String invoiceTaxer,
                                           @RequestParam("invoiceType") String invoiceType,
                                          @ModelAttribute(Principle.PRINCIPLE_SESSION_ATTRIBUTE) Principle principle) {
        BaseResponse response = new BaseResponse();

        TInvoiceApplyExtend invoiceApply = invoiceService.getByEid(principle.getExhibitor().getEid());
        try{
            if (invoiceApply == null) {
                invoiceApply = new TInvoiceApplyExtend();
                invoiceApply.setInvoice_flag(2);
                invoiceApply.setInvoice_company(invoice_company);
                invoiceApply.setInvoice_taxpayer_no(invoice_taxpayer_no);
                invoiceApply.setInvoice_bank_name(invoice_bank_name);
                invoiceApply.setInvoice_bank_account(invoice_bank_account);
                invoiceApply.setInvoice_company_address(invoice_company_address);
                invoiceApply.setInvoice_company_tel(invoice_company_tel);
                invoiceApply.setInvoice_company_contact(invoice_company_contact);
                invoiceApply.setInvoice_general_taxpayer_flag(invoiceTaxer);
                invoiceApply.setInvoice_general_tax_type(invoiceType);
                invoiceApply.setCreateTime(new Date());
                invoiceApply.setEid(principle.getExhibitor().getEid());
                invoiceService.create(invoiceApply);
            } else {
                invoiceApply.setInvoice_flag(2);
                invoiceApply.setInvoice_company(invoice_company);
                invoiceApply.setInvoice_taxpayer_no(invoice_taxpayer_no);
                invoiceApply.setInvoice_bank_name(invoice_bank_name);
                invoiceApply.setInvoice_bank_account(invoice_bank_account);
                invoiceApply.setInvoice_company_address(invoice_company_address);
                invoiceApply.setInvoice_company_tel(invoice_company_tel);
                invoiceApply.setInvoice_company_contact(invoice_company_contact);
                invoiceApply.setInvoice_general_taxpayer_flag(invoiceTaxer);
                invoiceApply.setInvoice_general_tax_type(invoiceType);
                invoiceApply.setCreateTime(new Date());
                invoiceApply.setEid(principle.getExhibitor().getEid());
                invoiceService.update(invoiceApply);
            }
            response.setResultCode(0);
        }catch (Exception e){
            response.setResultCode(1);
        }
        return response;
    }

    @RequestMapping(value = "uploadInvoiceImage", method = RequestMethod.POST)
    public ModelAndView uploadInvoiceImage(@ModelAttribute(Principle.PRINCIPLE_SESSION_ATTRIBUTE) Principle principle,
                                           HttpServletRequest httpServletRequest,
                                           @RequestParam(value="invoice_image_address",required = false) MultipartFile invoice_image_address,
                                           Locale locale) {
        //ModelAndView modelAndView = new ModelAndView("/user/invoice/index");
        ModelAndView modelAndView = new ModelAndView("/user/callback");
        TInvoiceApplyExtend invoiceApply = invoiceService.getByEid(principle.getExhibitor().getEid());
        try{
            if(invoiceApply != null && StringUtils.isNotEmpty(invoice_image_address.getOriginalFilename())){
                String filenameTemp = invoice_image_address.getOriginalFilename();
                String format = FilenameUtils.getExtension(filenameTemp);
                String fileName = "";
                if(StringUtils.isNotEmpty(format)){
                    fileName = systemConfig.getVal(Constants.appendix_directory) + "/" + new Date().getTime() + "." + format;
                }else{
                    fileName = systemConfig.getVal(Constants.appendix_directory) + "/" + new Date().getTime() + ".jpg";
                }
                FileUtils.copyInputStreamToFile(invoice_image_address.getInputStream(), new File(fileName));
                invoiceApply.setInvoice_image_address(fileName);
                invoiceApply.setInvoice_flag(3);
                invoiceService.update(invoiceApply);
                if (locale.equals(Locale.US)) {
                    modelAndView.addObject("method", "addSuccess");
                } else {
                    modelAndView.addObject("method", "addSuccess");
                }
            }
        }catch (Exception e){
            log.error("update invoice image error.", e);
            modelAndView.addObject("method", "addFailure");
        }
        return modelAndView;
    }
}
