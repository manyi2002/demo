package com.Scarlett.dao;

import com.Scarlett.bean.Admin;

import java.sql.SQLException;

public interface AdminDao {
        /**
         * 根据用户名查找用户
         * @param adminName
         * @return
         */
        public Admin findByAdminname(String adminName) throws SQLException;
        /**
         * 保存用户信息到数据库
         * @param  admin
         */
        void insertAdmin(Admin admin) throws SQLException;


}
