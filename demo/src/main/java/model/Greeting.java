package model;

import lombok.Data;

@Data
public class Greeting {
	private Long id;
	private String message;
	private String memo;
	private Long userId;
}