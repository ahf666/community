package com.ao666.community_background.server.controller.user;

import com.ao666.community_background.common.exception.AccountException;
import com.ao666.community_background.common.result.Result;
import com.ao666.community_background.pojo.dto.UserUpdateDTO;
import com.ao666.community_background.pojo.dto.UserUpdateDTO2;
import com.ao666.community_background.server.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("userAccountController")
@RequestMapping("/user/account")
@Api(tags = "用户端-账号管理接口")
@Slf4j
public class AccountController {

    @Autowired
    private UserService userService;

    @PostMapping("/update")
    @ApiOperation("用户修改个人密码")
    public Result update(@RequestBody UserUpdateDTO userUpdateDTO){
        log.info("用户修改自己的数据:{}", userUpdateDTO);
        userService.update(userUpdateDTO);
        return Result.success();
    }

    @PostMapping("/updateMsg")
    @ApiOperation("用户修改个人信息")
    public Result update2(@RequestBody UserUpdateDTO2 userUpdateDTO2){
        log.info("用户修改自己的数据:{}", userUpdateDTO2);
        try{
            userService.updateMsg(userUpdateDTO2);
            return Result.success();
        }catch (AccountException e){
            return Result.error(e.getMessage());
        }
    }

}
