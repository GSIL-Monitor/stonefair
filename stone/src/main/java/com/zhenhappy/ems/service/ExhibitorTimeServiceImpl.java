package com.zhenhappy.ems.service;

import com.zhenhappy.ems.stonetime.TExhibitorTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ExhibitorTimeServiceImpl implements ExhibitorTimeService {
    private static final Logger logger = LoggerFactory.getLogger(ExhibitorTimeServiceImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public TExhibitorTime loadExhibitorTime() {
        String company_Info_Submit_Deadline_Zh = jdbcTemplate.queryForObject("select company_Info_Submit_Deadline_Zh from [t_exhibitor_time] ", new Object[]{}, String.class);
        String company_Info_Submit_Deadline_En = jdbcTemplate.queryForObject("select company_Info_Submit_Deadline_En from [t_exhibitor_time] ", new Object[]{}, String.class);
        String participant_List_Submit_Deadline_Zh = jdbcTemplate.queryForObject("select participant_List_Submit_Deadline_Zh from [t_exhibitor_time] ", new Object[]{}, String.class);
        String participant_List_Submit_Deadline_En = jdbcTemplate.queryForObject("select participant_List_Submit_Deadline_En from [t_exhibitor_time] ", new Object[]{}, String.class);
        String invoice_Information_Submit_Deadline_Zh = jdbcTemplate.queryForObject("select invoice_Information_Submit_Deadline_Zh from [t_exhibitor_time] ", new Object[]{}, String.class);
        String invoice_Information_Submit_Deadline_En = jdbcTemplate.queryForObject("select invoice_Information_Submit_Deadline_En from [t_exhibitor_time] ", new Object[]{}, String.class);
        String advertisement_Submit_Deadline_Zh = jdbcTemplate.queryForObject("select advertisement_Submit_Deadline_Zh from [t_exhibitor_time] ", new Object[]{}, String.class);
        String advertisement_Submit_Deadline_En = jdbcTemplate.queryForObject("select advertisement_Submit_Deadline_En from [t_exhibitor_time] ", new Object[]{}, String.class);
        String company_Info_Insert_Submit_Deadline_Zh = jdbcTemplate.queryForObject("select company_Info_Insert_Submit_Deadline_Zh from [t_exhibitor_time] ", new Object[]{}, String.class);
        String company_Info_Insert_Submit_Deadline_En = jdbcTemplate.queryForObject("select company_Info_Insert_Submit_Deadline_En from [t_exhibitor_time] ", new Object[]{}, String.class);
        String stone_fair_end_year = jdbcTemplate.queryForObject("select stone_fair_end_year from [t_exhibitor_time] ", new Object[]{}, String.class);
        String stone_fair_begin_year = jdbcTemplate.queryForObject("select stone_fair_begin_year from [t_exhibitor_time] ", new Object[]{}, String.class);
        String company_Info_Data_End_Html = jdbcTemplate.queryForObject("select company_Info_Data_End_Html from [t_exhibitor_time] ", new Object[]{}, String.class);
        String visa_Info_Submit_Deadline_Zh = jdbcTemplate.queryForObject("select visa_Info_Submit_Deadline_Zh from [t_exhibitor_time] ", new Object[]{}, String.class);
        String visa_Info_Submit_Deadline_En = jdbcTemplate.queryForObject("select visa_Info_Submit_Deadline_En from [t_exhibitor_time] ", new Object[]{}, String.class);
        String stone_Fair_Show_Date_Zh = jdbcTemplate.queryForObject("select stone_Fair_Show_Date_Zh from [t_exhibitor_time] ", new Object[]{}, String.class);
        String stone_Fair_Show_Date_En = jdbcTemplate.queryForObject("select stone_Fair_Show_Date_En from [t_exhibitor_time] ", new Object[]{}, String.class);
        String exhibitor_end_time = jdbcTemplate.queryForObject("select exhibitor_end_time from [t_exhibitor_time] where area_time=?", new Object[]{}, String.class);

        TExhibitorTime tExhibitorTime = new TExhibitorTime();
        tExhibitorTime.setCompany_Info_Submit_Deadline_Zh(company_Info_Submit_Deadline_Zh);
        tExhibitorTime.setCompany_Info_Submit_Deadline_En(company_Info_Submit_Deadline_En);
        tExhibitorTime.setParticipant_List_Submit_Deadline_Zh(participant_List_Submit_Deadline_Zh);
        tExhibitorTime.setParticipant_List_Submit_Deadline_En(participant_List_Submit_Deadline_En);
        tExhibitorTime.setInvoice_Information_Submit_Deadline_Zh(invoice_Information_Submit_Deadline_Zh);
        tExhibitorTime.setInvoice_Information_Submit_Deadline_En(invoice_Information_Submit_Deadline_En);
        tExhibitorTime.setAdvertisement_Submit_Deadline_Zh(advertisement_Submit_Deadline_Zh);
        tExhibitorTime.setAdvertisement_Submit_Deadline_En(advertisement_Submit_Deadline_En);
        tExhibitorTime.setCompany_Info_Insert_Submit_Deadline_Zh(company_Info_Insert_Submit_Deadline_Zh);
        tExhibitorTime.setCompany_Info_Insert_Submit_Deadline_En(company_Info_Insert_Submit_Deadline_En);
        tExhibitorTime.setStone_fair_end_year(stone_fair_end_year);
        tExhibitorTime.setStone_fair_begin_year(stone_fair_begin_year);
        tExhibitorTime.setCompany_Info_Data_End_Html(company_Info_Data_End_Html);
        tExhibitorTime.setVisa_Info_Submit_Deadline_Zh(visa_Info_Submit_Deadline_Zh);
        tExhibitorTime.setVisa_Info_Submit_Deadline_En(visa_Info_Submit_Deadline_En);
        tExhibitorTime.setStone_Fair_Show_Date_Zh(stone_Fair_Show_Date_Zh);
        tExhibitorTime.setStone_Fair_Show_Date_En(stone_Fair_Show_Date_En);
        tExhibitorTime.setExhibitor_end_time(exhibitor_end_time);
        return tExhibitorTime;
    }

    @Override
    public TExhibitorTime loadExhibitorTimeByArea(int exhibitorArea){
        String company_Info_Submit_Deadline_Zh = jdbcTemplate.queryForObject("select company_Info_Submit_Deadline_Zh from [t_exhibitor_time] where area_time=?", new Object[]{exhibitorArea}, String.class);
        String company_Info_Submit_Deadline_En = jdbcTemplate.queryForObject("select company_Info_Submit_Deadline_En from [t_exhibitor_time] where area_time=?", new Object[]{exhibitorArea}, String.class);
        String participant_List_Submit_Deadline_Zh = jdbcTemplate.queryForObject("select participant_List_Submit_Deadline_Zh from [t_exhibitor_time] where area_time=?", new Object[]{exhibitorArea}, String.class);
        String participant_List_Submit_Deadline_En = jdbcTemplate.queryForObject("select participant_List_Submit_Deadline_En from [t_exhibitor_time] where area_time=?", new Object[]{exhibitorArea}, String.class);
        String invoice_Information_Submit_Deadline_Zh = jdbcTemplate.queryForObject("select invoice_Information_Submit_Deadline_Zh from [t_exhibitor_time] where area_time=?", new Object[]{exhibitorArea}, String.class);
        String invoice_Information_Submit_Deadline_En = jdbcTemplate.queryForObject("select invoice_Information_Submit_Deadline_En from [t_exhibitor_time] where area_time=?", new Object[]{exhibitorArea}, String.class);
        String advertisement_Submit_Deadline_Zh = jdbcTemplate.queryForObject("select advertisement_Submit_Deadline_Zh from [t_exhibitor_time] where area_time=?", new Object[]{exhibitorArea}, String.class);
        String advertisement_Submit_Deadline_En = jdbcTemplate.queryForObject("select advertisement_Submit_Deadline_En from [t_exhibitor_time] where area_time=?", new Object[]{exhibitorArea}, String.class);
        String company_Info_Insert_Submit_Deadline_Zh = jdbcTemplate.queryForObject("select company_Info_Insert_Submit_Deadline_Zh from [t_exhibitor_time] where area_time=?", new Object[]{exhibitorArea}, String.class);
        String company_Info_Insert_Submit_Deadline_En = jdbcTemplate.queryForObject("select company_Info_Insert_Submit_Deadline_En from [t_exhibitor_time] where area_time=?", new Object[]{exhibitorArea}, String.class);
        String stone_fair_end_year = jdbcTemplate.queryForObject("select stone_fair_end_year from [t_exhibitor_time] where area_time=?", new Object[]{exhibitorArea}, String.class);
        String stone_fair_begin_year = jdbcTemplate.queryForObject("select stone_fair_begin_year from [t_exhibitor_time] where area_time=?", new Object[]{exhibitorArea}, String.class);
        String company_Info_Data_End_Html = jdbcTemplate.queryForObject("select company_Info_Data_End_Html from [t_exhibitor_time] where area_time=?", new Object[]{exhibitorArea}, String.class);
        String visa_Info_Submit_Deadline_Zh = jdbcTemplate.queryForObject("select visa_Info_Submit_Deadline_Zh from [t_exhibitor_time] where area_time=?", new Object[]{exhibitorArea}, String.class);
        String visa_Info_Submit_Deadline_En = jdbcTemplate.queryForObject("select visa_Info_Submit_Deadline_En from [t_exhibitor_time] where area_time=?", new Object[]{exhibitorArea}, String.class);
        String stone_Fair_Show_Date_Zh = jdbcTemplate.queryForObject("select stone_Fair_Show_Date_Zh from [t_exhibitor_time] where area_time=?", new Object[]{exhibitorArea}, String.class);
        String stone_Fair_Show_Date_En = jdbcTemplate.queryForObject("select stone_Fair_Show_Date_En from [t_exhibitor_time] where area_time=?", new Object[]{exhibitorArea}, String.class);
        String exhibitor_end_time = jdbcTemplate.queryForObject("select exhibitor_end_time from [t_exhibitor_time] where area_time=?", new Object[]{exhibitorArea}, String.class);

        TExhibitorTime tExhibitorTime = new TExhibitorTime();
        tExhibitorTime.setCompany_Info_Submit_Deadline_Zh(company_Info_Submit_Deadline_Zh);
        tExhibitorTime.setCompany_Info_Submit_Deadline_En(company_Info_Submit_Deadline_En);
        tExhibitorTime.setParticipant_List_Submit_Deadline_Zh(participant_List_Submit_Deadline_Zh);
        tExhibitorTime.setParticipant_List_Submit_Deadline_En(participant_List_Submit_Deadline_En);
        tExhibitorTime.setInvoice_Information_Submit_Deadline_Zh(invoice_Information_Submit_Deadline_Zh);
        tExhibitorTime.setInvoice_Information_Submit_Deadline_En(invoice_Information_Submit_Deadline_En);
        tExhibitorTime.setAdvertisement_Submit_Deadline_Zh(advertisement_Submit_Deadline_Zh);
        tExhibitorTime.setAdvertisement_Submit_Deadline_En(advertisement_Submit_Deadline_En);
        tExhibitorTime.setCompany_Info_Insert_Submit_Deadline_Zh(company_Info_Insert_Submit_Deadline_Zh);
        tExhibitorTime.setCompany_Info_Insert_Submit_Deadline_En(company_Info_Insert_Submit_Deadline_En);
        tExhibitorTime.setStone_fair_end_year(stone_fair_end_year);
        tExhibitorTime.setStone_fair_begin_year(stone_fair_begin_year);
        tExhibitorTime.setCompany_Info_Data_End_Html(company_Info_Data_End_Html);
        tExhibitorTime.setVisa_Info_Submit_Deadline_Zh(visa_Info_Submit_Deadline_Zh);
        tExhibitorTime.setVisa_Info_Submit_Deadline_En(visa_Info_Submit_Deadline_En);
        tExhibitorTime.setStone_Fair_Show_Date_Zh(stone_Fair_Show_Date_Zh);
        tExhibitorTime.setStone_Fair_Show_Date_En(stone_Fair_Show_Date_En);
        tExhibitorTime.setExhibitor_end_time(exhibitor_end_time);
        return tExhibitorTime;
    }
}
