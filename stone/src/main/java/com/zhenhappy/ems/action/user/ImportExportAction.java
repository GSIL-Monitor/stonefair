package com.zhenhappy.ems.action.user;

import com.zhenhappy.ems.action.BaseAction;
import com.zhenhappy.ems.dao.ExhibitorInfoDao;
import com.zhenhappy.ems.dto.Principle;
import com.zhenhappy.ems.dto.ProductType;
import com.zhenhappy.ems.dto.ProductTypeCheck;
import com.zhenhappy.ems.entity.*;
import com.zhenhappy.ems.service.ExhibitorService;
import com.zhenhappy.ems.sys.Constants;
import com.zhenhappy.system.SystemConfig;
import freemarker.template.Template;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by wujianbin on 2014-08-26.
 */
@Controller
@RequestMapping(value = "user")
@SessionAttributes(value = Principle.PRINCIPLE_SESSION_ATTRIBUTE)
public class ImportExportAction extends BaseAction {
	private static Logger log = Logger.getLogger(ImportExportAction.class);

	@Autowired
	private SystemConfig systemConfig;

    @RequestMapping("/upload")
    public File upload(@RequestParam MultipartFile file, String destDir, String fileName){
    	String appendix_directory = systemConfig.getVal(Constants.appendix_directory).replaceAll("\\\\\\\\", "\\\\");
    	if(StringUtils.isEmpty(fileName)) fileName = new Date().getTime() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
    	if(StringUtils.isNotEmpty(destDir)) destDir = appendix_directory + destDir;
    	else destDir = appendix_directory;
        File targetFile = new File(destDir, fileName);
        if(!targetFile.exists()) {
            targetFile.mkdirs();
        }
        try {
        	file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return targetFile;
    }
}
