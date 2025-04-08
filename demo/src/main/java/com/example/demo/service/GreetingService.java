package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.GreetingUserDTO;
import com.example.demo.mapper.GreetingMapper;
import com.example.demo.model.Greeting;
import com.example.demo.model.User;

/**
 * 挨拶とユーザー情報の作成・取得・更新・削除を行うサービスクラス。
 */
@Service
public class GreetingService {

	private final GreetingMapper greetingMapper;
	private final SqlExecutor sqlExecutor;

	/**
     * GreetingServiceのコンストラクタ。
     * 
     * @param greetingMapper データベース操作を行うマッパークラス
     * @param sqlExecutor 複雑なSQLを実行する補助クラス
     */
    public GreetingService(GreetingMapper greetingMapper, SqlExecutor sqlExecutor) {
        this.greetingMapper = greetingMapper;
        this.sqlExecutor = sqlExecutor;
    }
    
    /**
     * ユーザー名とメッセージを追加します。
     * ユーザー名が存在しない場合は新規作成し、挨拶をそのユーザーに関連付けて登録します。
     * 
     * @param name ユーザー名
     * @param message 挨拶メッセージ
     */
    @Transactional
    public void addNameAndMessage(String name, String message) {
    	Long userId = null;
    	List<User> userList = greetingMapper.findUsersByName(name);
    	if(userList != null && userList.size() != 0) {
    		userId = userList.get(0).getId();
    	} else {
    		User user = new User();
            user.setName(name);
            greetingMapper.insertUser(user);
            userId = user.getId();
    	}
        Greeting greeting = new Greeting();
        greeting.setMessage(message);
        greeting.setUserId(userId);
        greetingMapper.insertGreeting(greeting);
    }
    
    /**
     * ユーザーを追加します。
     * @param name ユーザー名
     */
    public void addUser(String name) {
    	User user = new User();
        user.setName(name);
    	greetingMapper.insertUser(user);
    }
    
    /**
     * 全ユーザーの情報を取得します。
     * 
     * @return ユーザーのリスト
     */
    public List<User> getAllUsers() {
    	return greetingMapper.findAllUsers();
    }
    
    /**
     * 指定されたユーザー名に対応するユーザーのリストを取得します。
     * 
     * @param name ユーザー名
     * @return ユーザーのリスト
     */
    public List<User> getUsersByName(String name) {
    	return greetingMapper.findUsersByName(name);
    }
    
    /**
     * 挨拶とユーザーの情報を結合して取得します。
     * 
     * @return GreetingUserDTOのリスト
     */
    public List<GreetingUserDTO> getAllGreetingsWithUsers() {
        return sqlExecutor.getGreetingsWithUsers();
    }
    
    /**
     * 指定されたIDに対応する挨拶およびユーザー情報を更新します。
     * 
     * @param greetingId 更新対象のID
     * @param name 更新後のユーザー名
     * @param message 更新後のメッセージ
     * @param memo 更新後のメモ
     */
    @Transactional
    public void updateGreetingAndUser(Long greetingId, 
    		String name, String message, String memo) {
    	if (name != null && !name.isEmpty()) {
    		greetingMapper.updateNameByGreetingId(greetingId, name);
    	}
    	if (message != null && !message.isEmpty()) {
    		greetingMapper.updateMessage(greetingId, message);
    	}
    	if (memo != null) {
    		greetingMapper.updateMemo(greetingId, memo);
    	}
    	
    }

    /**
     * 指定されたIDに対応する挨拶データを削除します。
     * 
     * @param id 削除対象のID
     */
    public void deleteGreeting(Long id) {
        greetingMapper.deleteGreeting(id);
    }
}
