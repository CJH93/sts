package com.fullstack.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDTO {

	private Long rno;
	private String text;
	private String replyer;
	
	// 게시물 번호
	private Long bno;
	private LocalDateTime regDate, modDate;
	
}
