package com.ao666.community_background.server.service;

import com.ao666.community_background.common.result.PageResult;
import com.ao666.community_background.pojo.dto.HousePageDTO;
import com.ao666.community_background.pojo.entity.House;
import com.ao666.community_background.pojo.vo.HouseVO;

public interface HouseService {


    PageResult page(HousePageDTO housePageDTO);

    House getById(int houseId);

    HouseVO getByUserId(Long id);
}
