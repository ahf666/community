package com.ao666.community_background.server.mapper;

import com.ao666.community_background.pojo.dto.CurrentCarDTO;
import com.ao666.community_background.pojo.vo.Car;
import com.ao666.community_background.pojo.vo.CurrentUserCarVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CurrentCarMapper {
    Page<CurrentUserCarVO> pageUserQuery(CurrentCarDTO currentCarDTO);

    @Insert("insert into tb_current_car(car_license, user_id, entry_time) VALUES (#{carLicense}, #{userId}, #{entryTime})")
    void insert(Car car);

    @Select("select * from tb_current_car")
    List<Car> getAll();

    @Delete("delete from tb_current_car where car_license = #{carLicense}")
    void delete(String carLicense);
}
