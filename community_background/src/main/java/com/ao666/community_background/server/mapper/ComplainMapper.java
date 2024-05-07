package com.ao666.community_background.server.mapper;

import com.ao666.community_background.pojo.dto.ComplainPageDTO;
import com.ao666.community_background.pojo.dto.ComplainUserPageDTO;
import com.ao666.community_background.pojo.entity.Complain;
import com.ao666.community_background.pojo.vo.ComplainPageVO;
import com.ao666.community_background.pojo.vo.ComplainUserVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ComplainMapper {
    Page<ComplainPageVO> pageQuery(ComplainPageDTO complainPageDTO);

    Page<ComplainUserVO> userPageQuery(ComplainUserPageDTO complainUserPageDTO);

    void insert(Complain complain);

    @Delete("delete from tb_complain where user_id = #{userId}")
    void delete(Long userId);
}
