package com.example.demo.dto;

import lombok.Data;

@Data
public class GreetingUserDTO {
	private Long id;
    private String message;
    private String name;

    public GreetingUserDTO(Long id, String message, String name) {
        this.id = id;
        this.message = message;
        this.name = name;
    }
}
