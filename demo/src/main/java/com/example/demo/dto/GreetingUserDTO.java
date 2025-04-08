package com.example.demo.dto;

import lombok.Data;

/**
 * 挨拶メッセージとユーザー名を保持するデータ転送オブジェクト（DTO）クラス。
 * 複数のテーブルにまたがる情報を1つのオブジェクトとして扱うために使用します。
 */
@Data
public class GreetingUserDTO {
	private Long id;
    private String message;
    private String memo;
    private String name;
    
    /**
     * GreetingUserDTOのデフォルトコンストラクタ。
     */
    public GreetingUserDTO() {}

    /**
     * GreetingUserDTOのフィールドをすべて受け取るコンストラクタ。
     * 
     * @param id 挨拶ID
     * @param message 挨拶メッセージ
     * @param memo 挨拶メモ
     * @param name ユーザー名
     */
    public GreetingUserDTO(Long id, String message, String memo, String name) {
        this.id = id;
        this.message = message;
        this.memo = memo;
        this.name = name;
    }
}
