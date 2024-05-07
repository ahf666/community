package com.ao666.community_background.server.controller.admin;

import com.ao666.community_background.common.result.PageResult;
import com.ao666.community_background.common.result.Result;
import com.ao666.community_background.pojo.dto.ComplainPageDTO;
import com.ao666.community_background.server.service.ComplainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("adminComplainController")
@RequestMapping("/admin/complain")
@Api(tags = "管理端-投诉接口")
@Slf4j
public class ComplainController {

    @Autowired
    private ComplainService complainService;
    @GetMapping("/page")
    @ApiOperation("投诉管理")
    private Result<PageResult> page(ComplainPageDTO complainPageDTO){
        log.info("缴费管理分页查询:{}", complainPageDTO);
        PageResult pageResult = complainService.page(complainPageDTO);
        log.info("查询结果:{}", pageResult.getRecords());
        return Result.success(pageResult);
    }
}
