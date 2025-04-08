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
import com.example.demo.model.User;
/**
 * データベース操作を行うマッパークラス。
 */
@Mapper
public interface GreetingMapper {

	/**
     * ユーザー情報をデータベースに追加します。
     *
     * @param user 追加するユーザー情報
     */
	@Insert("INSERT INTO users (name) VALUES (#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertUser(User user);

	/**
     * 挨拶データをデータベースに追加します。
     *
     * @param greeting 追加する挨拶データ
     */
    @Insert("INSERT INTO greetings (message, user_id) VALUES (#{message}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertGreeting(Greeting greeting);
    
    /**
     * すべてのユーザーを取得します。
     *
     * @return ユーザーのリスト
     */
    @Select("SELECT * FROM users")
    List<User> findAllUsers();
    
    /**
     * 指定された名前に一致するユーザーを検索します。
     *
     * @param name 検索するユーザー名
     * @return 一致するユーザーのリスト
     */
    @Select("SELECT * FROM users WHERE name = #{name}")
    List<User> findUsersByName(@Param("name") String name);

    /**
     * 指定されたIDのメッセージを更新します。
     *
     * @param id 更新対象のID
     * @param message 更新するメッセージ内容
     */
    @Update("UPDATE greetings SET message = #{message} WHERE id = #{id}")
    void updateMessage(@Param("id") Long id, @Param("message") String message);
    
    /**
     * 指定されたIDのメモを更新します。
     *
     * @param id 更新対象のID
     * @param memo 更新するメモ内容
     */
    @Update("UPDATE greetings SET memo = #{memo} WHERE id = #{id}")
    void updateMemo(@Param("id") Long id, @Param("memo") String memo);
    
    /**
     * 指定されたIDに紐づいたユーザーの名前を更新します。
     *
     * @param greetingId 指定されたID
     * @param name 新しいユーザー名
     */
    @Update("UPDATE users SET name = #{name} WHERE id = "
    		+ "(SELECT user_id FROM greetings WHERE id = #{greetingId})")
    void updateNameByGreetingId(@Param("greetingId") Long greetingId, @Param("name") String name);

    /**
     * 指定されたIDの挨拶データを削除します。
     *
     * @param id 削除対象のID
     */
    @Delete("DELETE FROM greetings WHERE id = #{id}")
    void deleteGreeting(@Param("id") Long id);
    
}