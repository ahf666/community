package com.ao666.community_background.server.service.impl;

import com.ao666.community_background.common.constant.StatusConstant;
import com.ao666.community_background.common.result.PageResult;
import com.ao666.community_background.pojo.dto.ComplainPageDTO;
import com.ao666.community_background.pojo.dto.ComplainUserDTO;
import com.ao666.community_background.pojo.dto.ComplainUserPageDTO;
import com.ao666.community_background.pojo.entity.Complain;
import com.ao666.community_background.pojo.vo.ComplainPageVO;
import com.ao666.community_background.pojo.vo.ComplainUserVO;
import com.ao666.community_background.pojo.vo.FeePageVO;
import com.ao666.community_background.server.mapper.ComplainMapper;
import com.ao666.community_background.server.service.ComplainService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ComplainServiceImpl implements ComplainService {

    @Autowired
    private ComplainMapper complainMapper;

    /**
     * 报修分页查询
     * @param complainPageDTO
     * @return
     */
    public PageResult page(ComplainPageDTO complainPageDTO) {
        PageHelper.startPage(complainPageDTO.getPage(), complainPageDTO.getPageSize());
        Page<ComplainPageVO> page = complainMapper.pageQuery(complainPageDTO);
        log.info("service:--size:{}, result:{}",  page.getTotal(), page.getResult());
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 用户报修历史查询
     * @param complainUserPageDTO
     * @return
     */
    public PageResult userPage(ComplainUserPageDTO complainUserPageDTO) {
        PageHelper.startPage(complainUserPageDTO.getPage(), complainUserPageDTO.getPageSize());
        Page<ComplainUserVO> page = complainMapper.userPageQuery(complainUserPageDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 用户投诉
     * @param complainUserDTO
     */
    public void complain(ComplainUserDTO complainUserDTO) {
        Complain complain = Complain.builder()
                .userId(complainUserDTO.getUserId())
                .details(complainUserDTO.getDetails())
                .status(StatusConstant.DISABLE)
                .build();
        complainMapper.insert(complain);
    }
}
