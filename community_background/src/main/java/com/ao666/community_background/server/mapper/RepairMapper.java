package com.ao666.community_background.server.mapper;

import com.ao666.community_background.pojo.dto.RepairPageDTO;
import com.ao666.community_background.pojo.dto.RepairUserPageDTO;
import com.ao666.community_background.pojo.entity.Repair;
import com.ao666.community_background.pojo.vo.FeePageVO;
import com.ao666.community_background.pojo.vo.RepairPageVO;
import com.ao666.community_background.pojo.vo.RepairUserPageVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RepairMapper {
    Page<RepairPageVO> pageQuery(RepairPageDTO repairPageDTO);

    Page<RepairUserPageVO> userPageQuery(RepairUserPageDTO repairUserPageDTO);

    void insert(Repair repair);

    @Delete("delete from tb_repair where user_id = #{userId}")
    void delete(Long userId);
}
