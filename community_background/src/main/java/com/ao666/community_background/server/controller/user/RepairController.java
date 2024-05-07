package com.ao666.community_background.server.controller.user;


import com.ao666.community_background.common.result.PageResult;
import com.ao666.community_background.common.result.Result;
import com.ao666.community_background.pojo.dto.RepairUserDTO;
import com.ao666.community_background.pojo.dto.RepairUserPageDTO;
import com.ao666.community_background.server.service.RepairService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("userRepairController")
@RequestMapping("/user/repair")
@Api(tags = "用户端-报修管理接口")
@Slf4j
public class RepairController {

    @Autowired
    private RepairService repairService;
    @GetMapping("/page")
    @ApiOperation("用户报修历史查询")
    private Result pageByUserId(RepairUserPageDTO repairUserPageDTO){
        log.info("报修记录历史查询");
        PageResult pageResult = repairService.userPage(repairUserPageDTO);
        log.info("查询结果:{}", pageResult.getRecords());
        return Result.success(pageResult);
    }

    @PostMapping("/add")
    @ApiOperation("用户发起报修")
    private Result repair(@RequestBody RepairUserDTO repairUserDTO){
        log.info("用户报修");
        repairService.repair(repairUserDTO);
        return Result.success();
    }
}
