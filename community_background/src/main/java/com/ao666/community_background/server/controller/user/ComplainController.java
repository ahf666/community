package com.ao666.community_background.server.controller.user;

import com.ao666.community_background.common.result.PageResult;
import com.ao666.community_background.common.result.Result;
import com.ao666.community_background.pojo.dto.ComplainUserDTO;
import com.ao666.community_background.pojo.dto.ComplainUserPageDTO;
import com.ao666.community_background.server.service.ComplainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("userComplainController")
@RequestMapping("/user/complain")
@Api(tags = "用户端-投诉管理接口")
@Slf4j
public class ComplainController {

    @Autowired
    private ComplainService complainService;
    @GetMapping("/page")
    @ApiOperation("用户投诉历史查询")
    private Result pageByUserId(ComplainUserPageDTO complainUserPageDTO){
        log.info("投诉记录历史查询");
        PageResult pageResult = complainService.userPage(complainUserPageDTO);
        log.info("查询结果:{}", pageResult.getRecords());
        return Result.success(pageResult);
    }

    @PostMapping("/add")
    @ApiOperation("用户发起投诉")
    private Result complain(@RequestBody ComplainUserDTO complainUserDTO){
        log.info("用户投诉");
        complainService.complain(complainUserDTO);
        return Result.success();
    }
}
