package com.ao666.community_background.server.service.impl;

import com.ao666.community_background.common.constant.MessageConstant;
import com.ao666.community_background.common.exception.AccountException;
import com.ao666.community_background.common.exception.AddUserException;
import com.ao666.community_background.common.exception.DeleteException;
import com.ao666.community_background.common.result.PageResult;
import com.ao666.community_background.pojo.dto.*;
import com.ao666.community_background.pojo.entity.Fee;
import com.ao666.community_background.pojo.entity.House;
import com.ao666.community_background.pojo.entity.User;
import com.ao666.community_background.pojo.vo.UserPageVO;
import com.ao666.community_background.server.mapper.*;
import com.ao666.community_background.server.service.FeeService;
import com.ao666.community_background.server.service.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private HouseMapper houseMapper;

    @Autowired
    private StopMapper stopMapper;

    @Autowired
    private FeeMapper feeMapper;

    @Autowired
    private RepairMapper repairMapper;

    @Autowired
    ComplainMapper complainMapper;

    @Autowired
    UserCarMapper userCarMapper;

    @Autowired
    LoginMapper loginMapper;
    /**
     * 用户管理分页查询
     * @param userListDTO
     * @return
     */
    public PageResult page(UserPageDTO userListDTO) {
        PageHelper.startPage(userListDTO.getPage(), userListDTO.getPageSize());
        Page<UserPageVO> page = userMapper.pageQuery(userListDTO);
        log.info("service:--size:{}, result:{}",  page.getTotal(), page.getResult());
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 用户修改自身的密码
     * @param userUpdateDTO
     */
    public void update(UserUpdateDTO userUpdateDTO) {

        // 对新密码进行加密
        String password = DigestUtils.md5DigestAsHex(userUpdateDTO.getPassword().getBytes());

        User user = User.builder()
                .id(userUpdateDTO.getId())
                .password(password)
                .build();

        userMapper.updatePassword(user);
    }

    /**
     * 修改用户状态
     * @param userStatusDTO
     */
    public void status(UserStatusDTO userStatusDTO) {
        userMapper.updateStatus(userStatusDTO);
    }


    /**
     * 新增用户绑定房产
     * @param userAddDTO
     * @throws AddUserException
     */
    @Transactional
    public void add(UserAddDTO userAddDTO) throws AddUserException {
        // 手机号是否已注册
        User user = userMapper.selectPhone(userAddDTO.getPhone());
        if(user!=null && user.getId()!=0){
            throw new AddUserException(MessageConstant.PHONE_EXIST);
        }
        // 房屋是否已被购入
        House house = House.builder()
                .building(userAddDTO.getBuilding())
                .cell(userAddDTO.getCell())
                .floor(userAddDTO.getFloor())
                .doorplate(userAddDTO.getDoorplate())
                .build();
        House house1 = houseMapper.selectStatus(house);
        if(house1==null || house1.getId()==0){
            throw new AddUserException(MessageConstant.HOUSE_NOT_EXIST);
        }else if(house1.getStatus() != 0) {
            throw new AddUserException(MessageConstant.HOUSE_EXIST);
        }

        User user1 = User.builder()
                .name(userAddDTO.getName())
                .phone(userAddDTO.getPhone())
                .sex(userAddDTO.getSex())
                .build();
        // 先插入用户，主键返回
        userMapper.insert(user1);
        Long userId = user1.getId();

        house1.setUserId(userId);
        houseMapper.update(house1);
    }

    /**
     * 删除用户
     * @param userId
     */
    @Transactional
    public void delete(Long userId) throws DeleteException {
        List<Fee> fees = feeMapper.checkUser(userId);
        if(fees!=null && fees.size()!=0){
            throw new DeleteException(MessageConstant.FEE_EXIST);
        }
        userMapper.delete(userId);
        houseMapper.fresh(userId);
        stopMapper.fresh(userId);
        repairMapper.delete(userId);
        complainMapper.delete(userId);
        userCarMapper.delete(userId);
    }

    /**
     * 用户修改信息
     * @param userUpdateDTO2
     */
    public void updateMsg(UserUpdateDTO2 userUpdateDTO2) throws AccountException{
        User user0 = userMapper.getById(userUpdateDTO2.getId());
        // 本来不是自己的用户名
        if(!user0.getUsername().equals(userUpdateDTO2.getUsername())){
            User user = loginMapper.getUserByUsername(userUpdateDTO2.getUsername());
            if(user!=null && user.getId()>0){ // 用户名已经存在
                throw new AccountException(MessageConstant.USERNAME_EXIST);
            }
        }
        User user1 = User.builder()
                .id(userUpdateDTO2.getId())
                .name(userUpdateDTO2.getName())
                .username(userUpdateDTO2.getUsername())
                .sex(userUpdateDTO2.getSex())
                .build();
        userMapper.updateById(user1);

    }

    /**
     * 查询车辆的用户
     * @param userId
     * @return
     */
    public User getById(Long userId) {
        return userMapper.getById(userId);
    }
}
