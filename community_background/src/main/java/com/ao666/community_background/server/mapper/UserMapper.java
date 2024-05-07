package com.ao666.community_background.server.mapper;

import com.ao666.community_background.pojo.dto.UserPageDTO;
import com.ao666.community_background.pojo.dto.UserStatusDTO;
import com.ao666.community_background.pojo.entity.User;
import com.ao666.community_background.pojo.vo.UserPageVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    Page<UserPageVO> pageQuery(UserPageDTO userListDTO);

    void updatePassword(User user);

    @Update("update tb_user set status=#{status} where id = #{id}")
    void updateStatus(UserStatusDTO userStatusDTO);

    @Select("select * from tb_user where phone = #{phone}")
    User selectPhone(String phone);

    void insert(User user1);

    @Delete("delete from tb_user where id = #{id}")
    void delete(Long id);

    @Update("update tb_user set name=#{name}, username=#{username}, sex=#{sex} where id = #{id}")
    void updateById(User user1);

    @Select("select * from tb_user where id = #{id}")
    User getById(Long id);

}
