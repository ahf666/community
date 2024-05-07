package com.ao666.community_background.server.service;

import com.ao666.community_background.common.result.PageResult;
import com.ao666.community_background.pojo.dto.RepairPageDTO;
import com.ao666.community_background.pojo.dto.RepairUserDTO;
import com.ao666.community_background.pojo.dto.RepairUserPageDTO;

public interface RepairService {
    PageResult page(RepairPageDTO repairPageDTO);

    PageResult userPage(RepairUserPageDTO repairUserPageDTO);

    void repair(RepairUserDTO repairUserDTO);
}
