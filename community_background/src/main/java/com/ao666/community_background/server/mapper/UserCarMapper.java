package com.ao666.community_background.server.mapper;

import com.ao666.community_background.pojo.dto.CarPageDTO;
import com.ao666.community_background.pojo.dto.UserCarPageDTO;
import com.ao666.community_background.pojo.entity.UserCar;
import com.ao666.community_background.pojo.vo.FeePageVO;
import com.ao666.community_background.pojo.vo.UserCarPageVO;
import com.ao666.community_background.pojo.vo.UserCarVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserCarMapper {
    Page<UserCarPageVO> pageQuery(UserCarPageDTO userCarPageDTO);

    @Select("select id, car_license from tb_user_car where user_id = #{id}")
    List<UserCarVO> getByUserId(Long id);

    @Insert("insert into tb_user_car (car_license, user_id) VALUES (#{carLicense}, #{userId})")
    void insert(UserCar userCar);

    @Delete("delete from tb_user_car where id = #{id}")
    void deleteById(Long id);


    @Select("select car_license from tb_user_car where id = #{id}")
    String getById(Long id);
    @Delete("delete from tb_user_car where user_id = #{userId}")
    void delete(Long userId);

    Page<UserCarVO> pageUserQuery(CarPageDTO carPageDTO);

    @Select("select * from tb_user_car where car_license = #{carLicense}")
    UserCar getByCarLicense(String carLicense);
}
