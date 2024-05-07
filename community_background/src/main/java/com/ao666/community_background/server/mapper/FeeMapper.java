package com.ao666.community_background.server.mapper;

import com.ao666.community_background.pojo.dto.FeePageDTO;
import com.ao666.community_background.pojo.dto.FeeUserPageDTO;
import com.ao666.community_background.pojo.entity.Fee;
import com.ao666.community_background.pojo.vo.FeePageVO;
import com.ao666.community_background.pojo.vo.FeeUserPageVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface FeeMapper {
    Page<FeePageVO> pageQuery(FeePageDTO feePageDTO);

    Page<FeeUserPageVO> pageUserQuery(FeeUserPageDTO feeUserPageDTO);

    @Update("update tb_fee set status=1 where id = #{id}")
    void updateStatus(Long id);

    @Select("select * from tb_fee where user_id=#{userId}")
    List<Fee> checkUser(Long userId);
}
