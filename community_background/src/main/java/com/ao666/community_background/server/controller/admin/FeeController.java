package com.ao666.community_background.server.controller.admin;

import com.ao666.community_background.common.exception.QueryException;
import com.ao666.community_background.common.result.PageResult;
import com.ao666.community_background.common.result.Result;
import com.ao666.community_background.pojo.dto.FeePageDTO;
import com.ao666.community_background.pojo.entity.House;
import com.ao666.community_background.pojo.entity.Stop;
import com.ao666.community_background.pojo.vo.DetailsHouseVO;
import com.ao666.community_background.pojo.vo.DetailsStopVO;
import com.ao666.community_background.server.service.FeeService;
import com.ao666.community_background.server.service.HouseService;
import com.ao666.community_background.server.service.StopService;
import com.ao666.community_background.server.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("adminFeeController")
@RequestMapping("/admin/fee")
@Api(tags = "管理端-缴费接口")
@Slf4j
public class FeeController {

    @Autowired
    private FeeService feeService;

    @Autowired
    private HouseService houseService;
    @Autowired
    private StopService stopService;

    @GetMapping("/page")
    @ApiOperation("缴费管理")
    private Result<PageResult> page(FeePageDTO feePageDTO){
        log.info("缴费管理分页查询:{}", feePageDTO);
        PageResult pageResult = feeService.page(feePageDTO);
        log.info("查询结果:{}", pageResult.getRecords());
        return Result.success(pageResult);
    }

    @GetMapping("/details/house")
    @ApiOperation("房屋详情")
    private Result detailsHouse(@RequestParam int houseId){

        try{
            House house = houseService.getById(houseId);

            DetailsHouseVO houseVO = DetailsHouseVO.builder()
                    .building(house.getBuilding())
                    .cell(house.getCell())
                    .floor(house.getFloor())
                    .doorplate(house.getDoorplate())
                    .build();

            return Result.success(houseVO);
        }catch (QueryException e){
            log.info(e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/details/stop")
    @ApiOperation("车位详情")
    private Result detailsStop(@RequestParam int stopId){
        try{
            Stop stop = stopService.getById(stopId);

            DetailsStopVO stopVO = DetailsStopVO.builder()
                    .place(stop.getPlace())
                    .build();
            return Result.success(stopVO);
        }catch (QueryException e){
            log.info(e.getMessage());
            return Result.error(e.getMessage());
        }
    }

}
