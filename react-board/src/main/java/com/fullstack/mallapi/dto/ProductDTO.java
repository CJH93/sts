package com.fullstack.mallapi.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 상품 관련 DTO
/*
 *  상품번호, 이름, 가격, 삭제 여부, 상품 이미지, 업로드된 파일 이름 등 필드 설정
 *  주의! 상품 하나에는 하나 이상의 이미지가 매핑 되어야함.
 *  파일 이름도 마찬가지. 이를 처리하기 위해서 List 로 매핑
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

	private Long pno;
	private String pname;
	private int price;
	private String pdesc;
	private boolean delFlag;
	
	@Builder.Default
	private List<MultipartFile> files = new ArrayList<>(); // 서버에 보내지는 실제 파일 데이터 필드
	// Builder.default 를 사용해서 초기 타입 지정
	
	@Builder.Default
	private List<String> uploadFileNames = new ArrayList<>();
		
	
}







