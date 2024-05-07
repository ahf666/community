package com.ao666.community_background.server.service;

import com.ao666.community_background.common.result.PageResult;
import com.ao666.community_background.pojo.dto.*;
import com.ao666.community_background.pojo.entity.User;
import com.ao666.community_background.pojo.vo.UserPageVO;

public interface UserService {
    PageResult page(UserPageDTO userListDTO);

    void update(UserUpdateDTO userUpdateDTO);

    void status(UserStatusDTO userStatusDTO);

    void add(UserAddDTO userAddDTO);

    void delete(Long id);

    void updateMsg(UserUpdateDTO2 userUpdateDTO2);

    User getById(Long userId);
}
