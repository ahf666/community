package com.ao666.community_background.server.mapper;

import com.ao666.community_background.pojo.entity.Admin;
import com.ao666.community_background.pojo.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LoginMapper extends BaseMapper<User> {

    /**
     * 管理员登录
     * @param username
     * @return
     */
    @Select("select * from tb_admin where username = #{username}")
    Admin getAdmin(String username);

    @Select("select * from tb_user where username = #{username}")
    User getUser(String username);

    @Select("select * from tb_user where phone = #{phone}")
    User getUserByPhone(String phone);


    void updateByPhone(User user);

    @Select("select * from tb_user where username = #{username}")
    User getUserByUsername(String username);
}
