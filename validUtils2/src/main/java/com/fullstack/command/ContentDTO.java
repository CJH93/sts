package com.fullstack.command;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ContentDTO {

	private int id;
	
	@NotNull(message = "작성자 Null")
	@NotEmpty(message = "작성자 값 없음")
	@Size(min = 3, max = 12, message = "길이는 3 ~ 12 사이")
	private String writer;
	
	@NotNull(message = "내용 Null")
	@NotEmpty(message = "내용 값 없음")
	private String content;
	
}
