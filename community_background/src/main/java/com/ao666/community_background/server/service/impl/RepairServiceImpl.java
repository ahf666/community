package com.ao666.community_background.server.service.impl;

import com.ao666.community_background.common.constant.StatusConstant;
import com.ao666.community_background.common.result.PageResult;
import com.ao666.community_background.pojo.dto.RepairPageDTO;
import com.ao666.community_background.pojo.dto.RepairUserDTO;
import com.ao666.community_background.pojo.dto.RepairUserPageDTO;
import com.ao666.community_background.pojo.entity.Repair;
import com.ao666.community_background.pojo.vo.RepairPageVO;
import com.ao666.community_background.pojo.vo.RepairUserPageVO;
import com.ao666.community_background.server.mapper.RepairMapper;
import com.ao666.community_background.server.service.RepairService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RepairServiceImpl implements RepairService {

    @Autowired
    private RepairMapper repairMapper;

    /**
     * 缴费分页查询
     * @param repairPageDTO
     * @return
     */
    public PageResult page(RepairPageDTO repairPageDTO) {
        PageHelper.startPage(repairPageDTO.getPage(), repairPageDTO.getPageSize());
        Page<RepairPageVO> page = repairMapper.pageQuery(repairPageDTO);
        log.info("service:--size:{}, result:{}",  page.getTotal(), page.getResult());
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 用户缴费分页查询
     * @param repairUserPageDTO
     * @return
     */
    public PageResult userPage(RepairUserPageDTO repairUserPageDTO) {
        PageHelper.startPage(repairUserPageDTO.getPage(), repairUserPageDTO.getPageSize());
        Page<RepairUserPageVO> page = repairMapper.userPageQuery(repairUserPageDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 用户报修
     * @param repairUserDTO
     */
    public void repair(RepairUserDTO repairUserDTO) {
        Repair repair = Repair.builder()
                .userId(repairUserDTO.getUserId())
                .houseId(repairUserDTO.getHouseId())
                .typeId(repairUserDTO.getTypeId())
                .image(repairUserDTO.getImage())
                .details(repairUserDTO.getDetails())
                .status(StatusConstant.DISABLE)
                .build();
        repairMapper.insert(repair);
    }
}
