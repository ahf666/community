package com.ao666.community_background.server.service.impl;

import com.ao666.community_background.common.constant.MessageConstant;
import com.ao666.community_background.common.exception.QueryException;
import com.ao666.community_background.common.result.PageResult;
import com.ao666.community_background.pojo.dto.BuyStopDTO;
import com.ao666.community_background.pojo.dto.StopPageDTO;
import com.ao666.community_background.pojo.entity.Stop;
import com.ao666.community_background.pojo.vo.StopPageVO;
import com.ao666.community_background.pojo.vo.StopVO;
import com.ao666.community_background.server.mapper.StopMapper;
import com.ao666.community_background.server.service.StopService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class StopServiceImpl implements StopService {

    @Autowired
    private StopMapper stopMapper;

    /**
     * 车位管理分页查询
     * @param stopPageDTO
     * @return
     */
    public PageResult page(StopPageDTO stopPageDTO) {
        PageHelper.startPage(stopPageDTO.getPage(), stopPageDTO.getPageSize());
        Page<StopPageVO> page = stopMapper.pageQuery(stopPageDTO);
        log.info("service:--size:{}, result:{}",  page.getTotal(), page.getResult());
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 根据id查询
     * @param stopId
     * @return
     */
    @Override
    public Stop getById(int stopId) throws QueryException{
        Stop stop = stopMapper.getById(stopId);

        if(stop==null || stop.getPlace()==null){
            throw new QueryException(MessageConstant.QUERY_EXCEPTION);
        }

        return stop;
    }

    /**
     * 根据用户id查询
     * @param id
     * @return
     */
    public List<StopVO> getByUserId(Long id) {
        return stopMapper.getByUserId(id);
    }

    /**
     * 用户租借车位
     * @param buyStopDTO
     */
    public void buyStop(BuyStopDTO buyStopDTO) {
        Stop stop = Stop.builder()
                .id(buyStopDTO.getId())
                .userId(buyStopDTO.getUserId())
                .carLicense(buyStopDTO.getCarLicense())
                .build();
        stopMapper.updateById(stop);
    }
}
