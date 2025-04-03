package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import model.Greeting;
import model.User;

@Mapper
public interface GreetingMapper {

	@Insert("INSERT INTO users (name) VALUES (#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertUser(User user);

    @Insert("INSERT INTO greetings (message, user_id) VALUES (#{message}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertGreeting(Greeting greeting);
    
    @Select("SELECT name FROM users")
    List<User> findAllName();
    
    @Select("SELECT * FROM users WHERE name = #{name}")
    List<User> findUsersByName(@Param("name") String name);

    @Update("UPDATE greetings SET message = #{message} WHERE id = #{id}")
    void updateMessage(@Param("id") Long id, @Param("message") String message);
    
    @Update("UPDATE greetings SET memo = #{memo} WHERE id = #{id}")
    void updateMemo(@Param("id") Long id, @Param("memo") String memo);
    
    @Update("UPDATE users SET name = #{name} WHERE id = "
    		+ "(SELECT user_id FROM greetings WHERE id = #{greetingId})")
    void updateNameByGreetingId(@Param("greetingId") Long greetingId, @Param("name") String name);

    @Delete("DELETE FROM greetings WHERE id = #{id}")
    void deleteGreeting(@Param("id") Long id);
    
}