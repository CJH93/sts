package com.fullstack.command;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ContentValidator implements Validator {

	@Override // 검증 대상 DTO(Command) 의 타입 지정
	public boolean supports(Class<?> clazz) {
		
		return ContentDTO.class.isAssignableFrom(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {

		ContentDTO dto = (ContentDTO)target;
		
		String writer = dto.getWriter();
		
		ValidationUtils.rejectIfEmpty(errors, "writer", "작성자가 없음");
		ValidationUtils.rejectIfEmpty(errors, "content", "내용이 없음");
		
//		// 검증할 로직 작성
//		if(writer == null || writer.trim().isEmpty())
//		{
//			System.out.println("작성자가 없음");
//			errors.reject("writer", "문제발생");
//		}
//		
//		String content = dto.getContent();
//		
//		if(content == null || content.trim().isEmpty())
//		{
//			System.out.println("내용 없음");
//			errors.reject("content", "문제발생");
//		}
		
		
		
	}
	
}
