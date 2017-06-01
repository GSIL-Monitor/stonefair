package com.zhenhappy.ems.dao.managerrole;

import com.zhenhappy.ems.dao.BaseDao;
import com.zhenhappy.ems.entity.managerrole.TUserInfo;

import java.util.List;

/**
 * Created by wangxd on 2016-05-13.
 */
public interface TUserInfoDao extends BaseDao<TUserInfo> {
    /*public List<TUserInfo> findUser(TUserInfo t);

    public int countUser(TUserInfo t);

    public TUserInfo findOneUser(Integer id);

    public void addUser(TUserInfo t);

    public void updateUser(TUserInfo t);

    public void deleteUser(Integer id);

    public TUserInfo loginUser(Map map);

    public TUserInfo existUserWithUserName(String userName);

    public TUserInfo existUserWithRoleId(Integer roleId);*/

    public List<TUserInfo> loadUserInfoByRoleids(Integer[] ids);
}
