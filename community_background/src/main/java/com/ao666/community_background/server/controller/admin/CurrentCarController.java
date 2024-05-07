package com.ao666.community_background.server.controller.admin;

import com.ao666.community_background.common.result.PageResult;
import com.ao666.community_background.common.result.Result;
import com.ao666.community_background.common.util.RedisUtil;
import com.ao666.community_background.pojo.dto.CurrentCarDTO;
import com.ao666.community_background.pojo.entity.CurrentCar;
import com.ao666.community_background.pojo.entity.User;
import com.ao666.community_background.pojo.entity.UserCar;
import com.ao666.community_background.pojo.vo.Car;
import com.ao666.community_background.pojo.vo.CarRedisVO;
import com.ao666.community_background.pojo.vo.CurrentUserCarVO;
import com.ao666.community_background.pojo.vo.UserPageVO;
import com.ao666.community_background.server.listener.RabbitMqListener;
import com.ao666.community_background.server.service.CurrentCarService;
import com.ao666.community_background.server.service.UserCarService;
import com.ao666.community_background.server.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController("currentUserController")
@RequestMapping("/admin/currentCar")
@Api(tags = "管理端-实时车辆管理接口")
@Slf4j
public class CurrentCarController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private CurrentCarService currentCarService;

    @Autowired
    private UserCarService userCarService;

    @Autowired
    private UserService userService;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-DD'T'HH:mm:ss");

    static LinkedList<Car> cars = new LinkedList<>(); // redis中

    static List<Car> carsMySql = new ArrayList<>(); // mysql中

    /**
     * 查询redis中全部键值对，并根据进入时间排序
     */
    private void loadRedis(){
        // 从redis中查出所有键值对
        Map<Object, Object> currentCarMap = redisTemplate.opsForHash().entries("current_car");
        log.info("从redis中查出的:{}---------------------",currentCarMap);
        Map<String, LocalDateTime> currentCar = new HashMap<>();
        for (Map.Entry<Object, Object> entry : currentCarMap.entrySet()) {
            String carLicense = (String) entry.getKey();
            String entryTimeString = (String) entry.getValue();
            if(carLicense==null || entryTimeString==null){
                continue;
            }
            LocalDateTime entryTime = LocalDateTime.parse(entryTimeString, formatter);
            currentCar.put(carLicense, entryTime);
        }
        // 排序转化为链表
        cars = RedisUtil.sortAndDeduplicateCars(currentCar);
    }

    /**
     * 查询mysql中的车辆
     */
    private void loadMysql(){
        carsMySql = currentCarService.getAll();
    }

    @GetMapping("/redisPage")
    @ApiOperation("24小时内车辆分页查询")
    public Result page1(CurrentCarDTO currentCarDTO){
        // redis
        if(cars==null || cars.size()==0){
            loadRedis();
            updateUserId();
        }
        // 分页查询
        List<Car> res = RedisUtil.paginateCars(cars, currentCarDTO.getPage(), currentCarDTO.getPageSize());
        int total = cars.size();
        CarRedisVO carRedisVO = new CarRedisVO(res, total);
        return Result.success(carRedisVO);
    }



    @GetMapping("/page")
    @ApiOperation("超过24小时车辆分页查询")
    public Result page2(CurrentCarDTO currentCarDTO){
        // 查mysql
        log.info("mysql分页查询:{}", currentCarDTO);
        PageResult pageResult = currentCarService.page(currentCarDTO);
        log.info("查询结果:{}", pageResult.getRecords());
        return Result.success(pageResult);
    }

    @GetMapping("/{userId}")
    @ApiOperation("查询车辆的用户")
    public Result user(@PathVariable Long userId){
        User user = userService.getById(userId);
        return Result.success(user);
    }

    /**
     * mq发来消息，添加或删除
     */
    public void addCar(String str){
        // 查cars中
        // 遍历链表，查找并可能删除具有给定carLicense的Car对象
        if(cars.size() == 0){
            loadRedis();
        }
        log.info("1此时cars内数据:{}", cars);

        Iterator<Car> iterator = cars.iterator();
        boolean found = false;
        while (iterator.hasNext()) {
            Car car = iterator.next();
            if (car.getCarLicense().equals(str)) {
                iterator.remove(); // 删除找到的Car对象
                found = true;
            // 使用redisTemplate的delete方法来删除指定的键值对
                redisTemplate.opsForHash().delete("current_car", str);
                System.out.println("redis删除"+ str + "------------------------");
                log.info("2此时cars内数据:{}", cars);
                break;
            }
        }
        if(found){ // 查到了不用操作了
            return;
        }
        // 没查到继续查carsMysql
        if(carsMySql.size() == 0){
            loadMysql();
        }
        Iterator<Car> iterator2 = carsMySql.iterator();
        while (iterator2.hasNext()) {
            Car car = iterator2.next();
            if (car.getCarLicense().equals(str)) {
                iterator2.remove(); // 删除找到的Car对象
                found = true;
                // 删除数据库中的车辆信息
                currentCarService.delete(car.getCarLicense());
                break;
            }
        }

        System.out.println("没有找到"+ str + "------------------------");
        // 如果没有找到，则在链表首位插入新的Car对象
        if (!found) {
            LocalDateTime entryTime = LocalDateTime.now();
            String entryTimeString = entryTime.format(formatter);
            redisTemplate.opsForHash().put("current_car", str, entryTimeString);
            // 查询它的userId
            UserCar userCar = userCarService.updateUserId(str);
            Long userId = 0L;
            if(userCar!=null){
                userId = userCar.getUserId();
            }
            Car newCar = new Car(userId, str, entryTime);
            cars.addFirst(newCar); // 插入到链表首位
        }
    }

    /**
     * 更新当前cars里的UserId
     */
    public void updateUserId(){
        for (Car car : cars) {
            UserCar userCar = userCarService.updateUserId(car.getCarLicense());
            if(userCar!=null){
                car.setUserId(userCar.getUserId());
            }
        }
    }

    /**
     * 定时任务
     */
    @Scheduled(cron = "0 0 3 * * ?") // 每天凌晨3点触发 秒 时 分 日 月 周
    public void updateDB(){
        LocalDateTime currentTime = LocalDateTime.now();
        // 查找过期的车辆
        for (Car car : cars) {
            if (Duration.between(car.getEntryTime(), currentTime).toHours() >= 24) {
                // redis中删除
                redisTemplate.opsForHash().delete("current_car", car.getCarLicense());
                // 持久化到mysql
                currentCarService.insert(car);
            }
        }

    }




}
