package com.fullstack.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnails;

/*
 * 컨트롤러에서 상품 이미지(데이터) 처리
 * 컨트롤러에서는 DTO 의 files 를 이용해서 전송하는 첨부 파일 처리,
 * 저장된 파일 이름은 DB 에 보관해서 필요시에 가져오는 방식
 * 
 * 또한, I/O 를 처리하며 특정 이름의 업로드된 이미지를 관리하는 폴더를 
 * 자동 생성하는 어노테이션을 이용해서 처리함
 * 
 * 기타 나머지 기능은 메서드를 정의해서 처리.
 */

@Component
@Log4j2
@RequiredArgsConstructor
public class CustomFileUtil {

	@Value("${com.fullstack.upload.path}")
	private String uploadPath;
	
	@PostConstruct
	public void init() 
	{
		File tempFolder = new File(uploadPath);
		
		if(tempFolder.exists() == false)
		{
			tempFolder.mkdir();
		}
		
		uploadPath = tempFolder.getAbsolutePath(); // 실제 경로 get
		log.info("----------------------------------");
		log.info(uploadPath);
	}
	
	// 업로드 요청된 파일을 저장하는 모듈 작성
	public List<String> saveFiles(List<MultipartFile> files) throws Exception 
	{
		if(files == null || files.size() == 0)
		{
			return List.of(); // 빈 리스트 리턴
		}
		List<String> upLoadNames = new ArrayList<>();
		
		for(MultipartFile multipartFile : files)
		{
			String saveName = UUID.randomUUID().toString() + "_" + multipartFile.getOriginalFilename();
			
			Path savePath = Paths.get(uploadPath, saveName);
			
			try 
			{
				Files.copy(multipartFile.getInputStream(), savePath);
				
				// 업로드된 파일 타입 알아내기
				String contentType = multipartFile.getContentType();
				
				// 타입을 기준으로 image 여부를 판단. (null 이 올 수도 있으니 null check)
				if(contentType != null && contentType.startsWith("image"))
				{
					// 썸네일 이미지 생성 이름
					Path thumSavePath = Paths.get(uploadPath, "s_" + saveName);
					
					// 썸네일 패스 정보 생성 완료, 썸네일 이미지를 생성해서 파일에 저장하는 작업 처리
					// of().size(크기정보).toFile() 를 순차적으로 사용
					Thumbnails.of(savePath.toFile()).size(200, 200).toFile(thumSavePath.toFile());
					
				}
				
				upLoadNames.add(saveName);
			} 
			catch (Exception e) 
			{
				throw new Exception(e.getMessage());
			}
		}
		return upLoadNames;
	}
	
	// 요청된 파일을 전송하는 메서드 정의
	public ResponseEntity<Resource> getFile(String fileName)
	{
		// FileSystemResource (패스와 파일네임) 으로 Resource 객체 생성
		// 클라이언트에 보내도록 header 설정 후, 파일을 넘겨주는 로직 정의
		Resource resource = new FileSystemResource(uploadPath + File.separator + fileName);
		
		if(!resource.isReadable())
		{
			resource = new FileSystemResource(uploadPath + File.separator + "default.jpg");
		}

		HttpHeaders headers = new HttpHeaders();
		
		try 
		{
			headers.add("Content-type", Files.probeContentType(resource.getFile().toPath()));
		} 
		catch (IOException e) 
		{
			return ResponseEntity.internalServerError().build();
		}
		return ResponseEntity.ok().headers(headers).body(resource);
		
	}
	
	
	// 파일 삭제 처리 메서드 구현
	public void deleteFiles(List<String> fileNames)
	{
		if(fileNames == null || fileNames.size() == 0)
		{
			fileNames.forEach(fileName -> {
				// 삭제시 썸네일 이미지도 같이 삭제
				String thumbnailFileName = "s_"+fileName;
				Path thumbnailPath = Paths.get(uploadPath, thumbnailFileName);
				Path filePath = Paths.get(uploadPath, fileName);
				// 삭제 시작
				try 
				{
					Files.deleteIfExists(filePath);
					Files.deleteIfExists(thumbnailPath);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
						
			});
		}
	}
	
}









