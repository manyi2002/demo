package com.Scarlett.service.Impl;

import com.Scarlett.Utils.MD5Util;
import com.Scarlett.bean.Admin;
import com.Scarlett.dao.AdminDao;
import com.Scarlett.dao.Impl.AdminDaoImpl;
import com.Scarlett.service.AdminService;

public class AdminServiceImpl implements AdminService {
    private AdminDao adminDao = new AdminDaoImpl();

    @Override
    public Admin doLogin(Admin paramterAdmin) throws Exception {
        //校验用户名
        Admin dbAdmin = adminDao.findByAdminname(paramterAdmin.getAdminName());
        if (dbAdmin==null){
//            说明用户名错误
            throw new RuntimeException("登录失败，用户名错误!"+paramterAdmin.getAdminName());
        }
        //校验密码
        String encodePassword = MD5Util.encode(paramterAdmin.getAdminPwd());
        if (!dbAdmin.getAdminPwd().equals(encodePassword)){
            //说明密码错误
            throw new RuntimeException("登录失败，密码错误！");
        }
        return dbAdmin;
    }

    @Override
    public void findByAdmin(String adminName) throws Exception {
        //调用持久层方法根据adminName查询admin
        Admin admin = adminDao.findByAdminname(adminName);
        if (admin!=null){
            throw new RuntimeException("用户名已存在！");
        }

    }
}
