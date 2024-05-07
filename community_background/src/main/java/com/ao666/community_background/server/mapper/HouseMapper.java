package com.ao666.community_background.server.mapper;

import com.ao666.community_background.pojo.dto.HousePageDTO;
import com.ao666.community_background.pojo.entity.House;
import com.ao666.community_background.pojo.vo.HousePageVO;
import com.ao666.community_background.pojo.vo.HouseVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface HouseMapper {

    Page<HousePageVO> pageQuery(HousePageDTO housePageDTO);

    @Select("select * from tb_house where id = #{houseId}")
    House getById(int houseId);

    @Select("select id, building, cell, floor, doorplate from tb_house where user_id = #{id}")
    HouseVO getByUserId(Long id);

    @Select("select id, status from tb_house where " +
            "building=#{building} and cell=#{cell} and floor=#{floor} and doorplate=#{doorplate}")
    House selectStatus(House house);

    @Update("update tb_house set user_id=#{userId}, status=1 where id = #{id}")
    void update(House house);

    @Update("update tb_house set user_id=0, status=0 where user_id=#{userId}")
    void fresh(Long userId);
}
