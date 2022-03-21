package com.ckwblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ckwblog.dao.pojo.SysUser;
import com.ckwblog.vo.Result;
import com.ckwblog.vo.UserVo;

public interface SysUserService extends IService<SysUser> {

    SysUser findUserById(Long id);

    UserVo findUserVoById(Long id);

    SysUser findUser(String account, String password);

    /**
     * 根据token查询用户信息
     * @param token
     * @return
     */
    Result findUserByToken(String token);

    /**
     * 根据账户查找用户
     * @param account
     * @return
     */
    SysUser findUserByAccount(String account);

    /**
     * 保存用户
     * @param sysUser
     */
    void save1(SysUser sysUser);
}
