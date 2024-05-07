package com.ao666.community_background.server.service;

import com.ao666.community_background.common.result.PageResult;
import com.ao666.community_background.pojo.dto.CarPageDTO;
import com.ao666.community_background.pojo.dto.UserCarDTO;
import com.ao666.community_background.pojo.dto.UserCarPageDTO;
import com.ao666.community_background.pojo.entity.UserCar;
import com.ao666.community_background.pojo.vo.Car;
import com.ao666.community_background.pojo.vo.UserCarVO;

import java.util.List;

public interface UserCarService {
    PageResult page(UserCarPageDTO userCarPageDTO);

    List<UserCarVO> getByUserId(Long id);

    void insertUserCar(UserCarDTO userCarDTO);

    void deleteById(Long id);

    PageResult pageUser(CarPageDTO carPageDTO);

    UserCar updateUserId(String carLicense);

}
