package com.ao666.community_background.server.service;

import com.ao666.community_background.common.result.PageResult;
import com.ao666.community_background.pojo.dto.BuyStopDTO;
import com.ao666.community_background.pojo.dto.StopPageDTO;
import com.ao666.community_background.pojo.entity.Stop;
import com.ao666.community_background.pojo.vo.StopVO;

import java.util.List;

public interface StopService {
    PageResult page(StopPageDTO stopPageDTO);

    Stop getById(int stopId);

    List<StopVO> getByUserId(Long id);

    void buyStop(BuyStopDTO buyStopDTO);
}
