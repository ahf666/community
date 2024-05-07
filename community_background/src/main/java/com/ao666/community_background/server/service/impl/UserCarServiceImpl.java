package com.ao666.community_background.server.service.impl;

import com.ao666.community_background.common.constant.MessageConstant;
import com.ao666.community_background.common.exception.DeleteException;
import com.ao666.community_background.common.exception.InsertException;
import com.ao666.community_background.common.result.PageResult;
import com.ao666.community_background.pojo.dto.CarPageDTO;
import com.ao666.community_background.pojo.dto.UserCarDTO;
import com.ao666.community_background.pojo.dto.UserCarPageDTO;
import com.ao666.community_background.pojo.entity.UserCar;
import com.ao666.community_background.pojo.vo.Car;
import com.ao666.community_background.pojo.vo.FeePageVO;
import com.ao666.community_background.pojo.vo.UserCarPageVO;
import com.ao666.community_background.pojo.vo.UserCarVO;
import com.ao666.community_background.server.mapper.StopMapper;
import com.ao666.community_background.server.mapper.UserCarMapper;
import com.ao666.community_background.server.service.UserCarService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class UserCarServiceImpl implements UserCarService {

    @Autowired
    private UserCarMapper userCarMapper;
    @Autowired
    private StopMapper stopMapper;

    /**
     * 用户车辆分页查询
     * @param userCarPageDTO
     * @return
     */
    public PageResult page(UserCarPageDTO userCarPageDTO) {
        PageHelper.startPage(userCarPageDTO.getPage(), userCarPageDTO.getPageSize());
        Page<UserCarPageVO> page = userCarMapper.pageQuery(userCarPageDTO);
        log.info("service:--size:{}, result:{}",  page.getTotal(), page.getResult());
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 通过用户id查询
     * @param id
     * @return
     */
    public List<UserCarVO> getByUserId(Long id) {
        return userCarMapper.getByUserId(id);
    }

    /**
     * 用户新增车辆
     * @param userCarDTO
     */
    @Transactional
    public void insertUserCar(UserCarDTO userCarDTO) throws InsertException {

        // 检查该车辆是否已经存在
        List<UserCarVO> carLicenses = userCarMapper.getByUserId(userCarDTO.getUserId());
        if(carLicenses.size() != 0){
            for (UserCarVO u : carLicenses) {
                if(userCarDTO.getCarLicense().equals(u.getCarLicense())){
                    throw new InsertException(MessageConstant.INSERT_EXCEPTION); // 存在抛出异常
                }
            }
        }
        UserCar userCar = UserCar.builder()
                .userId(userCarDTO.getUserId())
                .carLicense(userCarDTO.getCarLicense())
                .build();
        userCarMapper.insert(userCar);
    }

    /**
     * 根据id删除车辆
     * @param id
     */
    public void deleteById(Long id) throws DeleteException{
        // 查询该车辆是否已经绑定车位
        String carLicense = userCarMapper.getById(id);
        List<String> carLicenses = stopMapper.getSaled();
        if(carLicenses.size() != 0){
            for (String s : carLicenses) {
                if(carLicense.equals(s)){
                    throw new DeleteException(MessageConstant.CAR_DELETE_EXCEPTION);
                }
            }
        }


        userCarMapper.deleteById(id);
    }

    public PageResult pageUser(CarPageDTO carPageDTO) {
        PageHelper.startPage(carPageDTO.getPage(), carPageDTO.getPageSize());
        Page<UserCarVO> page = userCarMapper.pageUserQuery(carPageDTO);
        log.info("service:--size:{}, result:{}",  page.getTotal(), page.getResult());
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 根据车牌号查询
     * @param carLicense
     * @return
     */
    public UserCar updateUserId(String carLicense) {
        return userCarMapper.getByCarLicense(carLicense);
    }


}
