package com.fullstack.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 필드 : title, content, email, name, regDate, modDate, bno, 댓글수
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardDTO {

	private Long bno;
	private String title;
	private String content;
	private String writerEmail;
	private String writerName;
	private LocalDateTime regDate;
	private LocalDateTime modDate;
	private int replyCount;
	
	
	
	
}
