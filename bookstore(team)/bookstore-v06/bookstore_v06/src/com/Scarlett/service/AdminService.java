package com.Scarlett.service;

import com.Scarlett.bean.Admin;

public interface AdminService {
    public Admin doLogin(Admin paramterAdmin) throws Exception;
    void findByAdmin(String adminName) throws Exception;
}

