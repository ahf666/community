package com.ao666.community_background.server.controller.admin;

import com.ao666.community_background.common.result.PageResult;
import com.ao666.community_background.common.result.Result;
import com.ao666.community_background.pojo.dto.UserCarPageDTO;
import com.ao666.community_background.server.service.UserCarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("adminUserCarController")
@RequestMapping("/admin/userCar")
@Api(tags = "管理端-用户车辆接口")
@Slf4j
public class UserCarController {

    @Autowired
    private UserCarService userCarService;
    @GetMapping("/page")
    @ApiOperation("用户车辆管理")
    private Result<PageResult> page(UserCarPageDTO userCarPageDTO) {
        log.info("缴费管理分页查询:{}", userCarPageDTO);
        PageResult pageResult = userCarService.page(userCarPageDTO);
        log.info("查询结果:{}", pageResult.getRecords());
        return Result.success(pageResult);
    }
}
