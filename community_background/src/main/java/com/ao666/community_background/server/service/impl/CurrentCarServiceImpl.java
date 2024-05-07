package com.ao666.community_background.server.service.impl;

import com.ao666.community_background.common.result.PageResult;
import com.ao666.community_background.pojo.dto.CurrentCarDTO;
import com.ao666.community_background.pojo.vo.Car;
import com.ao666.community_background.pojo.vo.CurrentUserCarVO;
import com.ao666.community_background.pojo.vo.FeeUserPageVO;
import com.ao666.community_background.server.mapper.CurrentCarMapper;
import com.ao666.community_background.server.service.CurrentCarService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrentCarServiceImpl implements CurrentCarService {


    @Autowired
    private CurrentCarMapper currentCarMapper;
    /**
     * 当前车辆分页查询
     * @param currentCarDTO
     * @return
     */
    public PageResult page(CurrentCarDTO currentCarDTO) {
        PageHelper.startPage(currentCarDTO.getPage(), currentCarDTO.getPageSize());
        Page<CurrentUserCarVO> page = currentCarMapper.pageUserQuery(currentCarDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 持久化到Mysql
     * @param car
     */
    public void insert(Car car) {
        currentCarMapper.insert(car);
    }

    /**
     * 查询所有mysql中的车辆
     * @return
     */
    public List<Car> getAll() {
        return currentCarMapper.getAll();
    }

    /**
     * 删除一条
     * @param carLicense
     */
    public void delete(String carLicense) {
        currentCarMapper.delete(carLicense);
    }
}
