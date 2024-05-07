package com.ao666.community_background.server.service;

import com.ao666.community_background.pojo.dto.LoginDTO;
import com.ao666.community_background.pojo.dto.SignUpDTO;
import com.ao666.community_background.pojo.entity.Admin;
import com.ao666.community_background.pojo.entity.User;

public interface LoginService {


    Admin adminLogin(LoginDTO loginDTO);

    User userLogin(LoginDTO loginDTO);

    void qualification(SignUpDTO signUpDTO);

    void signUp(SignUpDTO signUpDTO);
}
