package com.ao666.community_background.server.mapper;

import com.ao666.community_background.pojo.dto.StopPageDTO;
import com.ao666.community_background.pojo.entity.Stop;
import com.ao666.community_background.pojo.vo.StopPageVO;
import com.ao666.community_background.pojo.vo.StopVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface StopMapper {

    @Select("select * from tb_stop where id = #{stopId}")
    public Stop getById(int stopId);

    public Page<StopPageVO> pageQuery(StopPageDTO stopPageDTO);

    @Select("select place, car_license from tb_stop where user_id = #{id}")
    List<StopVO> getByUserId(Long id);

    @Update("update tb_stop set user_id=#{userId}, car_license=#{carLicense}, status=1 where id = #{id}")
    void updateById(Stop stop);

    @Select("select car_license from tb_stop where status=1")
    List<String> getSaled();

    @Update("update tb_stop set user_id=0, status=0, car_license='xxxx' where user_id=#{userId}")
    void fresh(Long userId);
}
