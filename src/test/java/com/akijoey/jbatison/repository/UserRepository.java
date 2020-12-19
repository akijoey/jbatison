package com.akijoey.jbatison.repository;

import com.akijoey.jbatison.annotation.Mapper;
import com.akijoey.jbatison.annotation.Select;
import com.akijoey.jbatison.annotation.Update;
import com.akijoey.jbatison.entity.User;

import java.util.List;

@Mapper
public interface UserRepository {

    @Select("select * from user where id = #{id}")
    User getUser(String id);

    @Select("select * from user")
    List<User> getUsers();

    @Update("update user set name = #{name} where id = #{id}")
    void updateUser(String name, String id);

}
