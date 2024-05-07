package com.ao666.community_background.server.controller.admin;

import com.ao666.community_background.common.result.PageResult;
import com.ao666.community_background.common.result.Result;
import com.ao666.community_background.pojo.dto.HousePageDTO;
import com.ao666.community_background.server.service.HouseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("adminHouseController")
@RequestMapping("/admin/house")
@Api(tags = "管理端-房屋接口")
@Slf4j
public class HouseController {
    @Autowired
    private HouseService houseService;

    @GetMapping("/page")
    @ApiOperation("房屋管理分页查询")
    // 前端传int类型的时候，空默认为-1
    public Result<PageResult> page(HousePageDTO housePageDTO){
        log.info("房屋管理分页查询:{}", housePageDTO);
        PageResult pageResult = houseService.page(housePageDTO);
        log.info("查询结果:{}", pageResult.getRecords());
        return Result.success(pageResult);
    }
}
