package com.example.demo.model;

import lombok.Data;

/**
 * Userエンティティクラス。
 */
@Data
public class User {
	private Long id;
	private String name;
	
	/**
	 * Userのデフォルトコンストラクタ。
	 */
	public User() {}
	
	/**
	 * Userのフィールドをすべて受け取るコンストラクタ。
	 * 
	 * @param id ユーザーID
	 * @param name ユーザー名
	 */
	public User(Long id, String name) {
		this.id = id;
		this.name = name;
	}
}