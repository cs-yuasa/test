package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.model.Greeting;

@Mapper
public interface GreetingMapper {

    @Insert("INSERT INTO greetings (message) VALUES (#{message}")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertGreeting(Greeting user);

    @Select("SELECT * FROM greetings WHERE id = #{id}")
    Greeting findGreetingById(@Param("id") Long id);
    
    @Select("SELECT * FROM greetings")
    List<Greeting> findAllGreetings();

    @Update("UPDATE greetings SET message = #{message} WHERE id = #{id}")
    void updateMessage(@Param("id") Long id, @Param("message") String message);

    @Delete("DELETE FROM greetings WHERE id = #{id}")
    void deleteGreeting(@Param("id") Long id);
    
}