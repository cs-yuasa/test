package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.GreetingUserDTO;
import com.example.demo.mapper.GreetingMapper;

import model.Greeting;
import model.User;

@Service
public class GreetingService {

	private final GreetingMapper greetingMapper;
	private final SqlExecutor sqlExecutor;

    public GreetingService(GreetingMapper greetingMapper, SqlExecutor sqlExecutor) {
        this.greetingMapper = greetingMapper;
        this.sqlExecutor = sqlExecutor;
    }
    
    @Transactional
    public void addNameAndMessage(String name, String message) {
    	Long userId = null;
    	List<User> userList = greetingMapper.findUsersByName(name);
    	if(userList != null) {
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
    
    public List<User> getAllName() {
    	return greetingMapper.findAllName();
    }
    
    @Transactional
    public void updateGreetingAndUser(Long greetingId, 
    		String message, String memo, String name) {
    	if (message != null && !message.isEmpty()) {
    		greetingMapper.updateMessage(greetingId, message);
    	}
    	if (memo != null && !memo.isEmpty()) {
    		greetingMapper.updateMemo(greetingId, memo);
    	}
    	if (name != null && !name.isEmpty()) {
    		greetingMapper.updateNameByGreetingId(greetingId, name);
    	}
    }

    public void deleteGreeting(Long id) {
        greetingMapper.deleteGreeting(id);
    }
    
    public List<GreetingUserDTO> getAllGreetingsWithUsers() {
        return sqlExecutor.getGreetingsWithUsers();
    }
}
