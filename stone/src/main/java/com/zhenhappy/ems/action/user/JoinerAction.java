package com.zhenhappy.ems.action.user;

import com.zhenhappy.ems.action.BaseAction;
import com.zhenhappy.ems.dto.BaseResponse;
import com.zhenhappy.ems.dto.Principle;
import com.zhenhappy.ems.dto.QueryJoinersResponse;
import com.zhenhappy.ems.entity.TExhibitorJoiner;
import com.zhenhappy.ems.entity.TVisa;
import com.zhenhappy.ems.service.JoinerService;
import com.zhenhappy.ems.service.VisaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lianghaijian on 2014-04-20.
 */
@Controller
@SessionAttributes(value = Principle.PRINCIPLE_SESSION_ATTRIBUTE)
@RequestMapping(value = "user")
public class JoinerAction extends BaseAction {

    @Autowired
    private JoinerService joinerService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private VisaService visaService;

    @RequestMapping(value = "queryJoiners", method = RequestMethod.GET)
    public String queryJoiners() {
        return "/user/joiner/index";
    }

    /**
     * query all joiners in exhibitor.
     *
     * @param principle
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryJoiners", method = RequestMethod.POST)
    public QueryJoinersResponse queryJoiners(@RequestParam("flag") Integer flag,
                                             @ModelAttribute(Principle.PRINCIPLE_SESSION_ATTRIBUTE) Principle principle) {
        QueryJoinersResponse response = new QueryJoinersResponse();
        try {
            List<TExhibitorJoiner> joiners = joinerService.queryAllJoinersByEid(principle.getExhibitor().getEid(), flag);
            for(TExhibitorJoiner j:joiners) {
                j.setName(replaceBlank(j.getName()));
                j.setPosition(replaceBlank(j.getPosition()));
                j.setTelphone(replaceBlank(j.getTelphone()));
                j.setEmail(replaceBlank(j.getEmail()));
            }
            Collections.sort(joiners,new Comparator<TExhibitorJoiner>(){
                public int compare(TExhibitorJoiner arg0, TExhibitorJoiner arg1) {
                    return arg0.getIsDelete().compareTo(arg1.getIsDelete());
                }
            });
            response.setJoiners(joiners);
            Integer area = jdbcTemplate.queryForInt("select exhibition_area from [t_exhibitor] where eid = ?",new Object[] { principle.getExhibitor().getEid() });
            if(area != null)
                response.setArea(area);
            else
                response.setArea(0);
        } catch (Exception e) {
            response.setResultCode(1);
        }
        return response;
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

    @ResponseBody
    @RequestMapping(value = "saveJoiner", method = RequestMethod.POST)
    public BaseResponse saveJoiner(@RequestBody TExhibitorJoiner joiner,@ModelAttribute(Principle.PRINCIPLE_SESSION_ATTRIBUTE) Principle principle) {
        BaseResponse response = new BaseResponse();
        try {
            joiner.setEid(principle.getExhibitor().getEid());
            joiner.setCreateTime(new Date());
            joiner.setAdminUpdateTime(new Date());
            joiner.setIsNew(1);
            joinerService.saveOrUpdate(joiner);

            TVisa visa = new TVisa();
            visa.setPassportName(joiner.getName());
            visa.setEid(principle.getExhibitor().getEid());
            visa.setJoinerId(joiner.getId());
            //visa.setStatus(0);
            visa.setStatus(2);
            visa.setCreateTime(new Date());
            visaService.saveOrUpdate(visa);

            response.setResultCode(0);
        } catch (Exception e) {
            response.setResultCode(1);
        }
        return response;
    }

    @ResponseBody
    @RequestMapping(value = "updateJoiner", method = RequestMethod.POST)
    public BaseResponse updateJoiner(@RequestBody TExhibitorJoiner joiner,@ModelAttribute(Principle.PRINCIPLE_SESSION_ATTRIBUTE) Principle principle) {
        BaseResponse response = new BaseResponse();
        try {
            joiner.setEid(principle.getExhibitor().getEid());
            joiner.setAdminUpdateTime(new Date());
            joinerService.saveOrUpdate(joiner);

            /*TVisa visa = visaService.queryVisasByJoinerId(joiner.getId());
            if(visa == null){
                visa = new TVisa();
                visa.setPassportName(joiner.getName());
                visa.setEid(principle.getExhibitor().getEid());
                visa.setJoinerId(joiner.getId());
                visa.setStatus(2);
                visaService.saveOrUpdate(visa);
            }*/
            response.setResultCode(0);
        } catch (Exception e) {
            response.setResultCode(1);
        }
        return response;
    }

    @ResponseBody
    @RequestMapping(value = "deleteJoiner", method = RequestMethod.POST)
    public BaseResponse deleteJoiner(@RequestParam("jid") Integer jid,@ModelAttribute(Principle.PRINCIPLE_SESSION_ATTRIBUTE) Principle principle) {
        BaseResponse response = new BaseResponse();
        try {
            if(joinerService.delete(jid,principle.getExhibitor().getEid())>0){
                response.setResultCode(0);
            }else{
                response.setResultCode(1);
            }
        } catch (Exception e) {
            response.setResultCode(1);
        }
        return response;
    }
}
