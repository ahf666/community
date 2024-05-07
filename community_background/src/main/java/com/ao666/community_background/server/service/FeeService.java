package com.ao666.community_background.server.service;

import com.ao666.community_background.common.result.PageResult;
import com.ao666.community_background.pojo.dto.FeePageDTO;
import com.ao666.community_background.pojo.dto.FeeUserPageDTO;

public interface FeeService {
    PageResult page(FeePageDTO feePageDTO);

    PageResult userPage(FeeUserPageDTO feeUserPageDTO);

    void buy(Long id);
}
