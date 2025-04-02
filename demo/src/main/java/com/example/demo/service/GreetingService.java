package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.GreetingUserDTO;
import com.example.demo.mapper.GreetingMapper;
import com.example.demo.model.Greeting;

@Service
public class GreetingService {
	
	private final GreetingMapper greetingMapper;
	private final SqlExecutor sqlExecutor;

    public GreetingService(GreetingMapper greetingMapper, SqlExecutor sqlExecutor) {
        this.greetingMapper = greetingMapper;
        this.sqlExecutor = sqlExecutor;
    }
    
    public void addGreeting(String message) {
        Greeting greeting = new Greeting();
        greeting.setMessage(message);
        greetingMapper.insertGreeting(greeting);
    }

    public Greeting getGreetingById(Long id) {
        return greetingMapper.findGreetingById(id);
    }

    public List<Greeting> getAllGreetings() {
        return greetingMapper.findAllGreetings();
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
