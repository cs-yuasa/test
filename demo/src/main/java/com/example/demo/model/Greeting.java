package com.example.demo.model;

import lombok.Data;

/**
 * Greetingエンティティクラス。
 */
@Data
public class Greeting {
	private Long id;
	private String message;
	private String memo;
	private Long userId;
}