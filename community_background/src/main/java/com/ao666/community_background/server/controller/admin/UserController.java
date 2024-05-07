package com.ao666.community_background.server.controller.admin;

import com.ao666.community_background.common.exception.AddUserException;
import com.ao666.community_background.common.exception.DeleteException;
import com.ao666.community_background.common.result.PageResult;
import com.ao666.community_background.common.result.Result;
import com.ao666.community_background.pojo.dto.UserAddDTO;
import com.ao666.community_background.pojo.dto.UserPageDTO;
import com.ao666.community_background.pojo.dto.UserStatusDTO;
import com.ao666.community_background.server.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("adminUserController")
@RequestMapping("/admin/user")
@Api(tags = "管理端-用户管理接口")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/page")
    @ApiOperation("用户管理分页查询")
    // 前端传int类型的时候，空默认为-1
    public Result<PageResult> page(UserPageDTO userPageDTO){
        log.info("用户管理分页查询:{}", userPageDTO);
        PageResult pageResult = userService.page(userPageDTO);
        log.info("查询结果:{}", pageResult.getRecords());
        return Result.success(pageResult);
    }

    @PostMapping("/status")
    @ApiOperation("用户状态修改")
    public Result status(@RequestBody UserStatusDTO userStatusDTO){
        log.info("用户状态修改:{}", userStatusDTO);
        userService.status(userStatusDTO);
        return Result.success();
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除用户")
    public Result delete(Long id){
        log.info("删除用户:{}", id);
        try{
            userService.delete(id);
            return Result.success();
        }catch (DeleteException e){
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/add")
    @ApiOperation("添加用户")
    public Result add(@RequestBody UserAddDTO userAddDTO){
        log.info("添加用户");
        try{
            userService.add(userAddDTO);
            return Result.success();
        }catch (AddUserException e){
            return Result.error(e.getMessage());
        }

    }
}
