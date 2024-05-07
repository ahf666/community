package com.ao666.community_background.server.controller.user;

import com.ao666.community_background.common.exception.DeleteException;
import com.ao666.community_background.common.exception.InsertException;
import com.ao666.community_background.common.result.PageResult;
import com.ao666.community_background.common.result.Result;
import com.ao666.community_background.pojo.dto.BuyStopDTO;
import com.ao666.community_background.pojo.dto.CarPageDTO;
import com.ao666.community_background.pojo.dto.StopPageDTO;
import com.ao666.community_background.pojo.dto.UserCarDTO;
import com.ao666.community_background.pojo.vo.HouseVO;
import com.ao666.community_background.pojo.vo.StopVO;
import com.ao666.community_background.pojo.vo.UserCarVO;
import com.ao666.community_background.server.service.HouseService;
import com.ao666.community_background.server.service.StopService;
import com.ao666.community_background.server.service.UserCarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("userPropertyController")
@RequestMapping("/user/property")
@Api(tags = "用户端-财产管理接口")
@Slf4j
public class PropertyController {

    @Autowired
    private HouseService houseService;

    @Autowired
    private StopService stopService;

    @Autowired
    private UserCarService userCarService;

    @GetMapping("/house/{id}")
    @ApiOperation("房产查询")
    public Result getHouse(@PathVariable Long id){
        log.info("用户房产查询");
        HouseVO houseVO = houseService.getByUserId(id);
        return Result.success(houseVO);
    }

    @GetMapping("/userCar/page")
    @ApiOperation("用户分页查询车辆")
    public Result getUserCars(CarPageDTO carPageDTO){
        log.info("缴费管理分页查询:{}", carPageDTO);
        PageResult pageResult = userCarService.pageUser(carPageDTO);
        log.info("查询结果:{}", pageResult.getRecords());
        return Result.success(pageResult);
    }

    @PostMapping("/userCar/insert")
    @ApiOperation("用户新增车辆信息")
    public Result addUserCar(@RequestBody UserCarDTO userCarDTO){
        log.info("新增车辆信息:{}", userCarDTO);

        try{
            userCarService.insertUserCar(userCarDTO);
            return Result.success();
        }catch (InsertException e){
            return Result.error(e.getMessage());
        }

    }

    @DeleteMapping("/userCar/delete/{id}")
    @ApiOperation("用户删除车辆")
    public Result deleteById(@PathVariable Long id){
        log.info("根据id删除车辆");
        try{
            userCarService.deleteById(id);
            return Result.success();
        }catch (DeleteException e){
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/stop/{id}")
    @ApiOperation("车位查询")
    public Result getStops(@PathVariable Long id){
        log.info("用户车位查询");
        List<StopVO> stopVOS = stopService.getByUserId(id);
        return Result.success(stopVOS);
    }

    @GetMapping("/stop/page")
    @ApiOperation("空闲车位查询")
    public Result<PageResult> page(StopPageDTO stopPageDTO){
        log.info("车位管理分页查询:{}", stopPageDTO);
        PageResult pageResult = stopService.page(stopPageDTO);
        log.info("查询结果:{}", pageResult.getRecords());
        return Result.success(pageResult);
    }

    @PostMapping("/stop/buy")
    @ApiOperation("用户租借车位")
    public Result buyStop(@RequestBody BuyStopDTO buyStopDTO){
        log.info("用户租借车位:{}", buyStopDTO);
        stopService.buyStop(buyStopDTO);
        return Result.success();
    }

}
