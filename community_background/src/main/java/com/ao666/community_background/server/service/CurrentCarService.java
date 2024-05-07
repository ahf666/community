package com.ao666.community_background.server.service;

import com.ao666.community_background.common.result.PageResult;
import com.ao666.community_background.pojo.dto.CurrentCarDTO;
import com.ao666.community_background.pojo.vo.Car;

import java.util.List;

public interface CurrentCarService {
    PageResult page(CurrentCarDTO currentCarDTO);

    void insert(Car car);

    List<Car> getAll();

    void delete(String carLicense);
}
