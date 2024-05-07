package com.ao666.community_background.server.service;

import com.ao666.community_background.common.result.PageResult;
import com.ao666.community_background.pojo.dto.ComplainPageDTO;
import com.ao666.community_background.pojo.dto.ComplainUserDTO;
import com.ao666.community_background.pojo.dto.ComplainUserPageDTO;

public interface ComplainService {
    PageResult page(ComplainPageDTO complainPageDTO);

    PageResult userPage(ComplainUserPageDTO complainUserPageDTO);

    void complain(ComplainUserDTO complainUserDTO);
}
