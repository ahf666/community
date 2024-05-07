package com.ao666.community_background.server.controller.admin;

import com.ao666.community_background.common.constant.JwtClaimsConstant;
import com.ao666.community_background.common.constant.MessageConstant;
import com.ao666.community_background.common.exception.AccountException;
import com.ao666.community_background.common.properties.JwtProperties;
import com.ao666.community_background.common.result.Result;
import com.ao666.community_background.common.util.JwtUtil;
import com.ao666.community_background.pojo.dto.LoginDTO;
import com.ao666.community_background.pojo.entity.Admin;
import com.ao666.community_background.pojo.vo.AdminVO;
import com.ao666.community_background.server.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController("adminLoginController")
@RequestMapping("/admin/admin")
@Api(tags = "管理端-登录接口")
@Slf4j
public class LoginController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private JwtProperties jwtProperties;
    @GetMapping("/login")
    @ApiOperation("管理员登录")
    public Result login(LoginDTO loginDTO){
        log.info("管理员登录---------");
        try{
            Admin admin = loginService.adminLogin(loginDTO);
            Map<String, Object> claims = new HashMap<>();
            claims.put(JwtClaimsConstant.ADMIN_ID, admin.getId());
            String token = JwtUtil.createJWT(
                    jwtProperties.getAdminSecretKey(), // 获取配置文件yml里面的
                    jwtProperties.getAdminTtl(),
                    claims);

            AdminVO adminVO = AdminVO.builder()
                    .id(admin.getId())
                    .username(admin.getUsername())
                    .phone(admin.getPhone())
                    .token(token)
                    .build();
            return Result.success(adminVO);
        }catch (AccountException e){
            log.info(e.getMessage());
            return Result.error(e.getMessage());
        }
    }
}
