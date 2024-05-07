package com.ao666.community_background.server.service.impl;

import com.ao666.community_background.common.constant.MessageConstant;
import com.ao666.community_background.common.exception.QueryException;
import com.ao666.community_background.common.result.PageResult;
import com.ao666.community_background.pojo.dto.HousePageDTO;
import com.ao666.community_background.pojo.entity.House;
import com.ao666.community_background.pojo.vo.HousePageVO;
import com.ao666.community_background.pojo.vo.HouseVO;
import com.ao666.community_background.server.mapper.HouseMapper;
import com.ao666.community_background.server.service.HouseService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HouseServiceImpl implements HouseService {

    @Autowired
    private HouseMapper houseMapper;

    /**
     * 房屋分页查询
     * @param housePageDTO
     * @return
     */
    public PageResult page(HousePageDTO housePageDTO) {
        PageHelper.startPage(housePageDTO.getPage(), housePageDTO.getPageSize());
        Page<HousePageVO> page = houseMapper.pageQuery(housePageDTO);
        log.info("service:--size:{}, result:{}",  page.getTotal(), page.getResult());
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 根据id查询
     * @param houseId
     * @return
     */
    public House getById(int houseId) throws QueryException {
        House house = houseMapper.getById(houseId);
        if(house==null || house.getBuilding()==0){
            throw new QueryException(MessageConstant.QUERY_EXCEPTION);
        }
        return house;
    }

    @Override
    public HouseVO getByUserId(Long id) {
        return houseMapper.getByUserId(id);
    }
}
