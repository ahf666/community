package com.ao666.community_background.server.service.impl;


import com.ao666.community_background.common.constant.MessageConstant;
import com.ao666.community_background.common.constant.StatusConstant;
import com.ao666.community_background.common.exception.AccountException;
import com.ao666.community_background.pojo.dto.LoginDTO;
import com.ao666.community_background.pojo.dto.SignUpDTO;
import com.ao666.community_background.pojo.entity.Admin;
import com.ao666.community_background.pojo.entity.User;
import com.ao666.community_background.server.mapper.LoginMapper;
import com.ao666.community_background.server.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginMapper loginMapper;


    /**
     * 管理员登录
     */
    public Admin adminLogin(LoginDTO loginDTO) throws AccountException{
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();

        Admin admin = loginMapper.getAdmin(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (admin == null) {
            //账号不存在
            throw new AccountException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        // 对前端传过来的明文密码进行md5加密处理
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(admin.getPassword())) {
            //密码错误
            throw new AccountException(MessageConstant.PASSWORD_ERROR);
        } // MessageConstant里面封装了错误信息常量


        //3、返回实体对象
        return admin;
    }

    /**
     * 用户登录
     * @param loginDTO
     * @return
     */
    public User userLogin(LoginDTO loginDTO) throws AccountException{
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();

        User user = loginMapper.getUser(username);

        //1、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (user==null || user.getUsername() == null || user.getUsername().length() == 0) {
            //账号不存在
            throw new AccountException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        // 2.检查账号状态
        int status = user.getStatus();
        if(status == StatusConstant.DISABLE){
            throw new AccountException(MessageConstant.ACCOUNT_LOCK);
        }

        //密码比对
        // 对前端传过来的明文密码进行md5加密处理
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(user.getPassword())) {
            //密码错误
            System.out.println("密码是" + user.getPassword());
            throw new AccountException(MessageConstant.PASSWORD_ERROR);
        } // MessageConstant里面封装了错误信息常量

        //3、返回实体对象
        return user;
    }

    /**
     * 资格校验
     * @param
     * @return
     */
    public void qualification(SignUpDTO signUpDTO) throws AccountException{
        User user = loginMapper.getUserByPhone(signUpDTO.getPhone());
        if (user==null || user.getName()==null || user.getName().length()==0) {
            //姓名不存在说明账号不存在
            throw new AccountException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        if(!user.getUsername().equals(StatusConstant.NOT_SIGN_UP)){
            // 手机号对应的账号存在，已经注册,不能通过status比较，因为可能有被管理员禁用的已注册账号
            throw new AccountException(MessageConstant.ALREADY_SIGN_UP);
        }

        User user1 = loginMapper.getUserByUsername(signUpDTO.getUsername());
        if(user1!=null && user1.getId()>0){ // 用户名已经存在
            throw new AccountException(MessageConstant.USERNAME_EXIST);
        }
    }

    /**
     * 用户注册,只有资格校验通过才可注册
     * @param signUpDTO
     */
    public void signUp(SignUpDTO signUpDTO) {

        User user = User.builder()
                .username(signUpDTO.getUsername())
                .status(StatusConstant.ENABLE)
                .phone(signUpDTO.getPhone())
                .sex(signUpDTO.getSex())
                .build();
        // 设置密码.要进行md5加密,默认密码被常量类封装
        user.setPassword(DigestUtils.md5DigestAsHex(signUpDTO.getPassword().getBytes()));
        loginMapper.updateByPhone(user);
    }


}
