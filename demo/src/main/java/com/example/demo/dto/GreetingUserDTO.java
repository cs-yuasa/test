package com.example.demo.dto;

import lombok.Data;

@Data
public class GreetingUserDTO {
	private Long id;
    private String message;
    private String memo;
    private String name;

    public GreetingUserDTO(Long id, String message, String memo, String name) {
        this.id = id;
        this.message = message;
        this.memo = memo;
        this.name = name;
    }
}
