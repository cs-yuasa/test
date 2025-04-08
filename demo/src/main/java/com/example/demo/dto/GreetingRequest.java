package com.example.demo.dto;

import lombok.Data;

/**
 * 名前による検索に使用されるリクエストデータを表すクラス。
 * APIでJSON形式のデータを受け取るために使用されます。
 */
@Data
public class GreetingRequest {
	private String name;
}
