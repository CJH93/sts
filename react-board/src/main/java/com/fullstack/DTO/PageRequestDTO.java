package com.fullstack.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

// 페이지 번호, 전체 데이터 수, 이전/다음 페이지 등에 관련된 정보 객체
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {
	
	@Builder.Default
	private int page = 1;
	
	@Builder.Default
	private int size = 10;
	
}
