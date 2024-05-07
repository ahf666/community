package com.ao666.community_background.server.service.impl;

import com.ao666.community_background.common.result.PageResult;
import com.ao666.community_background.pojo.dto.FeePageDTO;
import com.ao666.community_background.pojo.dto.FeeUserPageDTO;
import com.ao666.community_background.pojo.vo.FeePageVO;
import com.ao666.community_background.pojo.vo.FeeUserPageVO;
import com.ao666.community_background.pojo.vo.StopPageVO;
import com.ao666.community_background.server.mapper.FeeMapper;
import com.ao666.community_background.server.service.FeeService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FeeServiceImpl implements FeeService {

    @Autowired
    private FeeMapper feeMapper;

    /**
     * 缴费管理分页查询
     * @param feePageDTO
     * @return
     */
    public PageResult page(FeePageDTO feePageDTO) {
        PageHelper.startPage(feePageDTO.getPage(), feePageDTO.getPageSize());
        Page<FeePageVO> page = feeMapper.pageQuery(feePageDTO);
        log.info("service:--size:{}, result:{}",  page.getTotal(), page.getResult());
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 用户缴费分页管理
     * @param feeUserPageDTO
     * @return
     */
    public PageResult userPage(FeeUserPageDTO feeUserPageDTO) {
        PageHelper.startPage(feeUserPageDTO.getPage(), feeUserPageDTO.getPageSize());
        Page<FeeUserPageVO> page = feeMapper.pageUserQuery(feeUserPageDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 用户缴费
     * @param id
     */
    public void buy(Long id) {
        feeMapper.updateStatus(id);
    }


}
