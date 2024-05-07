package com.ao666.community_background.server.controller.user;

import com.ao666.community_background.common.constant.JwtClaimsConstant;
import com.ao666.community_background.common.exception.AccountException;
import com.ao666.community_background.common.properties.JwtProperties;
import com.ao666.community_background.common.result.Result;
import com.ao666.community_background.common.util.JwtUtil;
import com.ao666.community_background.pojo.dto.LoginDTO;
import com.ao666.community_background.pojo.dto.SignUpDTO;
import com.ao666.community_background.pojo.entity.User;
import com.ao666.community_background.pojo.vo.UserLoginVO;
import com.ao666.community_background.server.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController("userLoginController")
@RequestMapping("/user/user")
@Api(tags = "用户端-登录接口")
@Slf4j
public class LoginController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private JwtProperties jwtProperties;

    @GetMapping("/login")
    @ApiOperation("用户登录")
    public Result<UserLoginVO> login(LoginDTO loginDTO){
        log.info("用户:{}登录", loginDTO);
        try{
            User user = loginService.userLogin(loginDTO);

            Map<String, Object> claims = new HashMap<>();
            claims.put(JwtClaimsConstant.USER_ID, user.getId());
            String token = JwtUtil.createJWT(
                    jwtProperties.getUserSecretKey(), // 获取配置文件yml里面的
                    jwtProperties.getUserTtl(),
                    claims);

            UserLoginVO userVO = UserLoginVO.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .name(user.getName())
                    .sex(user.getSex())
                    .phone(user.getPhone())
                    .token(token)
                    .build();
            return Result.success(userVO);
        }catch (AccountException e){
            log.info(e.getMessage());
            return Result.error(e.getMessage());
        }
    }


    @GetMapping("/qualification")
    @ApiOperation("用户资格检验")
    public Result qualification(SignUpDTO signUpDTO){
        try{
            loginService.qualification(signUpDTO);
            return Result.success();
        }catch (AccountException e){
            log.info(e.getMessage());
            return Result.error(e.getMessage());
        }

    }

    @PostMapping("/signUp")
    @ApiOperation("用户注册")
    public Result signUp(@RequestBody SignUpDTO signUpDTO){
        log.info("用户注册:{}", signUpDTO);
        loginService.signUp(signUpDTO);
        return Result.success();
    }

}
