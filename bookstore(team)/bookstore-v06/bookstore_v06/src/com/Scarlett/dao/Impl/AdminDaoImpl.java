package com.Scarlett.dao.Impl;

import com.Scarlett.bean.Admin;
import com.Scarlett.dao.AdminDao;
import com.Scarlett.dao.BaseDao;

import java.sql.SQLException;

public class AdminDaoImpl extends BaseDao<Admin> implements AdminDao {
    @Override
    public Admin findByAdminname(String adminName) throws SQLException {
        String sql = "select admin_id adminId,admin_name adminName,admin_pwd adminPwd from t_admin where admin_name = ?";
        Admin admin = getBean(Admin.class,sql,adminName);
        return admin;
    }

    @Override
    public void insertAdmin(Admin admin) throws SQLException {
        String sql = "insert into t_admin(admin_name,admin_pwd) values (?,?)";
        update(sql,admin.getAdminName(),admin.getAdminPwd());
    }
}
