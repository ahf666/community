package com.ao666.community_background.server.controller.admin;

import com.ao666.community_background.common.result.PageResult;
import com.ao666.community_background.common.result.Result;
import com.ao666.community_background.pojo.dto.StopPageDTO;
import com.ao666.community_background.server.service.StopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("adminStopController")
@RequestMapping("/admin/stop")
@Api(tags = "管理端-车位接口")
@Slf4j
public class StopController {

    @Autowired
    private StopService stopService;

    @GetMapping("/page")
    @ApiOperation("车位管理分页查询")
    // 前端传int类型的时候，空默认为-1
    public Result<PageResult> page(StopPageDTO stopPageDTO){
        log.info("车位管理分页查询:{}", stopPageDTO);
        PageResult pageResult = stopService.page(stopPageDTO);
        log.info("查询结果:{}", pageResult.getRecords());
        return Result.success(pageResult);
    }

}
