package com.ao666.community_background.server.controller.user;


import com.ao666.community_background.common.exception.QueryException;
import com.ao666.community_background.common.result.PageResult;
import com.ao666.community_background.common.result.Result;
import com.ao666.community_background.pojo.dto.FeePageDTO;
import com.ao666.community_background.pojo.dto.FeeUserPageDTO;
import com.ao666.community_background.pojo.entity.House;
import com.ao666.community_background.pojo.entity.Stop;
import com.ao666.community_background.pojo.vo.DetailsHouseVO;
import com.ao666.community_background.pojo.vo.DetailsStopVO;
import com.ao666.community_background.server.service.FeeService;
import com.ao666.community_background.server.service.HouseService;
import com.ao666.community_background.server.service.StopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("userFeeController")
@RequestMapping("/user/fee")
@Api(tags = "用户端-缴费管理接口")
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
    private Result<PageResult> page(FeeUserPageDTO feeUserPageDTO){
        log.info("用户缴费管理分页查询");
        PageResult pageResult = feeService.userPage(feeUserPageDTO);
        log.info("查询结果:{}", pageResult.getRecords());
        return Result.success(pageResult);
    }

    @PostMapping("/{id}")
    @ApiOperation("用户缴费")
    private Result buy(@PathVariable Long id){
        log.info("用户缴费");
        feeService.buy(id);
        return Result.success();
    }

    @GetMapping("/details/house")
    @ApiOperation("房屋详情")
    private Result detailsHouse(int houseId){

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
    private Result detailsStop(int stopId){
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
