package com.zhenhappy.ems.manager.service;

import com.zhenhappy.ems.entity.managerrole.TUserInfo;

/**
 * Created by lianghaijian on 2014-04-22.
 */
public interface AdminService {

    public TUserInfo login(String username, String password);

}
