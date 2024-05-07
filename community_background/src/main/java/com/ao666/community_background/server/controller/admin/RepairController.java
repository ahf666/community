package com.ao666.community_background.server.controller.admin;

import com.ao666.community_background.common.exception.QueryException;
import com.ao666.community_background.common.result.PageResult;
import com.ao666.community_background.common.result.Result;
import com.ao666.community_background.pojo.dto.RepairPageDTO;
import com.ao666.community_background.pojo.entity.House;
import com.ao666.community_background.pojo.vo.DetailsHouseVO;
import com.ao666.community_background.server.service.HouseService;
import com.ao666.community_background.server.service.RepairService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("adminRepairController")
@RequestMapping("/admin/repair")
@Api(tags = "管理端-报修接口")
@Slf4j
public class RepairController {

    @Autowired
    private RepairService repairService;
    @Autowired
    private HouseService houseService;

    @GetMapping("/page")
    @ApiOperation("报修管理")
    private Result<PageResult> page(RepairPageDTO repairPageDTO){
        log.info("缴费管理分页查询:{}", repairPageDTO);
        PageResult pageResult = repairService.page(repairPageDTO);
        log.info("查询结果:{}", pageResult.getRecords());
        return Result.success(pageResult);
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

}
