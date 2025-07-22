package com.fullstack.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fullstack.DTO.PageRequestDTO;
import com.fullstack.DTO.PageResponseDTO;
import com.fullstack.mallapi.dto.ProductDTO;
import com.fullstack.service.mallapi.ProductService;
import com.fullstack.utils.CustomFileUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor 
@Log4j2
@RequestMapping("api/products")
public class ProductController {

	private final CustomFileUtil fileUtil;
	private final ProductService productService;
	
	@PostMapping("/") // 상품 등록 메서드
	public Map<String, String> register(ProductDTO productDTO) throws Exception
	{
log.info("상품 등록: " + productDTO);
		
		List<MultipartFile> files = productDTO.getFiles();
		
		List<String> uploadFileName =  fileUtil.saveFiles(files);
		
		productDTO.setUploadFileNames(uploadFileName);
		
		log.info(uploadFileName);
		
		productService.register(productDTO);
		
		return Map.of("RESULT", "SUCCESS");
		
	}
	
	@GetMapping("/view/{fileName}")
	public ResponseEntity<Resource> viewFile(@PathVariable("fileName") String fileName)
	{
		// fileUtil 메서드를 호출해서 해당 이미지를 리턴
		return fileUtil.getFile(fileName);
	}
	
	// 상품 목록을 get 하도록 컨트롤러 설정. postman 으로 테스트
	// 아래처럼 특정 메서드에 권한 설정을 하고 나면, Filter 에서는 JWT 인증 정보를 활용해서 사용자를 구성하고
	// 이를 시큐리티에 지정해줘야 함.
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/list")
	public PageResponseDTO<ProductDTO> list(PageRequestDTO pageRequestDTO)
	{
		log.info("상품리스트 요청");
		return productService.getList(pageRequestDTO);
	}

	// path variable 매핑, 상품 번호 front 에서 보내면
	// 이 값을 받아서 해당 상품 객체를 보내도록 정의
	@GetMapping("{pno}")
	public ProductDTO read(@PathVariable("pno") Long pno)
	{
		return productService.get(pno);
	}
	
	@PutMapping("/{pno}")
	public Map<String, String> modify(@PathVariable(name = "pno") Long pno, ProductDTO productDTO)
	{
		// 기존의 상품 정보를 get, 상품 이미지 정보를 미리 파악해 두고 나중에 삭제해야 하는 파일을 파악할때 사용
		// DTO 에 담긴 파일 정보는 새롭게 수정되어야 할 내용이므로, 이 정보 또한 미리 파악.
		// DTO 의 uploadFileName 내용은 기존의 이름 값.
		// 새로 업로드된 값으로 업데이트
		// 서비스에서 파일 관련 처리가 완료된 DTO 를 전달, 처리
		// 기존의 파일 중, 더 이상 사용하지 않는 파일을 찾아서 제거.
		productDTO.setPno(pno);
		
		ProductDTO oldProductDTO = productService.get(pno);
		// 기존의 파일들 (DB 상의 파일) 파악
		List<String> oldFileNames = oldProductDTO.getUploadFileNames();
		
		List<MultipartFile> files = productDTO.getFiles();
		
		// 새로 업로드 되어서 만들어진 파일 이름들
		try 
		{
			List<String> currentUploadFileNames = fileUtil.saveFiles(files);
			
			// 화면에서 변화 없이 계속 유지되는 파일들 목록
			List<String> uploadFileNames = productDTO.getUploadFileNames();
			
			// 유지되는 파일과 새로 업로드된 파일 이름들이 저장해야 하는 목록으로 구성이 되어야함.
			if(currentUploadFileNames != null && currentUploadFileNames.size() > 0)
			{
				uploadFileNames.addAll(currentUploadFileNames);
			}
			
			// 수정 작업 시작
			productService.modify(productDTO);
			
			if(oldFileNames != null && oldFileNames.size() > 0)
			{
				// 제거 대상 찾기. 예전 파일들 중에서 지워져야 할 파일 이름들 찾아내기
				List<String> removeFiles = oldFileNames
						.stream()
						.filter(fileName -> uploadFileNames.indexOf(fileName) == -1)
						.collect(Collectors.toList());
				
				// 실제 파일 삭제
				fileUtil.deleteFiles(removeFiles);
			}
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return Map.of("RESULT", "SUCCESS");
		
	}
	
	
}









