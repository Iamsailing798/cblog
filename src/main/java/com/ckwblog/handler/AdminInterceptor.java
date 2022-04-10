package com.ckwblog.handler;

import com.ckwblog.dao.pojo.SysUser;
import com.ckwblog.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLSyntaxErrorException;

@Component
public class AdminInterceptor implements HandlerInterceptor {

    private SysUser sysUser;

    @Autowired
    private SysUserService sysUserService;

    public void getSysUser(String account)
    {
        SysUser sysUser=sysUserService.findUserByAccount(account);
        this.sysUser=sysUser;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(sysUser.getAdmin()!=1)
        {
            System.out.println("用户权限标识信息 "+sysUser.getAdmin());
            return false;
        }
    return true;
    }
}

