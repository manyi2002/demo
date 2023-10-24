package com.xmy.service.impl;

import com.xmy.bean.User;
import com.xmy.dao.UserDao;
import com.xmy.dao.impl.UserDaoImpl;
import com.xmy.service.UserService;
import com.xmy.utils.MD5Util;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();

    @Override
    public User doLogin(User parameterUser) throws Exception {
        //校验用户名
        User dbUser = userDao.findByUsername(parameterUser.getUserName());
        if(dbUser == null){
            //说明用户名错误
            throw new RuntimeException("登陆失败，用户名错误！");
        }
        //校验密码
        String encodedPassword = MD5Util.encode(parameterUser.getUserPwd());
        if(!dbUser.getUserPwd().equals(encodedPassword)){
            //说明密码错误
            throw new RuntimeException("登陆失败，密码错误！");
        }
        return dbUser;
    }

    @Override
    public void doRegister(User parameterUser) throws Exception {
        //调用持久层的方法，检查用户名是否已经存在
        User dbUser = userDao.findByUsername(parameterUser.getUserName());
        if(dbUser != null){
            //说明用户名已被占用
            throw new RuntimeException("注册失败，用户名已被占用！");
        }
        //说明用户名可用，将对应密码进行MD5加密
        String encodedPassword = MD5Util.encode(parameterUser.getUserPwd());
        parameterUser.setUserPwd(encodedPassword);
        //调用持久层方法保存注册信息
        userDao.insertUser(parameterUser);
    }

    //调用持久层的方法根据username查询user
    @Override
    public void findByUsername(String username) throws Exception {
        User user = userDao.findByUsername(username);
        if (user != null) {
            throw new RuntimeException("用户名已存在");
        }
    }
}