package com.fullstack.mallapi;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;

import com.fullstack.DTO.PageRequestDTO;
import com.fullstack.DTO.PageResponseDTO;
import com.fullstack.domain.Product;
import com.fullstack.mallapi.dto.ProductDTO;
import com.fullstack.repository.mallapi.ProductRepository;
import com.fullstack.service.mallapi.ProductService;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class ProductRepositoryTest {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductService productService;
	
	// @Test
	public void testRemove()
	{
		Long pno = 52L;
		
		productRepository.deleteById(pno);
		
	}
	
	@Commit
	//@Test
	public void testModify()
	{
		Long pno = 5L;
		ProductDTO productDTO = ProductDTO.builder()
				.pno(pno)
				.pdesc("상품설명 변경")
				.pname("상품 변경")
				.price(4000)
				.build();
		
		productService.modify(productDTO);
	}
	
	// 상품 get 테스트
	// @Test
	public void getTest()
	{
		Long pno = 5L;
		
		ProductDTO productDTO = productService.get(pno);
		
		log.info(productDTO);
	}
	
	
	// 상품 등록 테스트
	@Test
	public void registerTest()
	{
		ProductDTO productDTO = ProductDTO.builder()
				.pname("새로운 상품")
				.pdesc("잘 등록 될까?")
				.price(100000)
				.build();
		
		// 상품의 이미지 정보 세팅
		productDTO.setUploadFileNames(
				List.of(UUID.randomUUID() + "_" + "Test1.jpg", UUID.randomUUID() + "_" + "Test2.jpg")
				);
		
		productService.register(productDTO);
	}
	
	
	// @Test
	public void testList()
	{
		PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .build();
		
		PageResponseDTO<ProductDTO> response = productService.getList(pageRequestDTO);
		
		List<ProductDTO> productList = response.getDtoList();
		
		for (ProductDTO product : productList) {
            log.info("Pno: {}, Name: {}, Desc: {}, Price: {}, Images: {}",
                    product.getPno(),
                    product.getPname(),
                    product.getPdesc(),
                    product.getPrice(),
                    product.getUploadFileNames());
		}
	}
		
	
	// 상품 목록 요청 테스트
	@Transactional
	@Commit
	// @Test
	public void listTest()
	{
		Pageable pageable = PageRequest.of(0, 10, Sort.by("pno").descending());
		Page<Object[]> list = productRepository.selectList(pageable);
		
		// log.info(list);
		list.forEach(arr -> log.info(Arrays.toString(arr)));
		
	}
	
	// 상품 수정 테스트
	// 기존의 이미지 정보도 모두 날려 버리고 새롭게 이미지를 받도록 설정
	@Transactional
	@Commit
	// @Test
	public void updateTest()
	{
		Long pno = 10L;
		Product product = productRepository.findById(pno).get();
		product.changeName("수정된 10번 상품");
		product.changeDesc("수정된 상품 내용");
		product.changePrice(50000);
		
		// 관련된 첨부파일 수정
		product.clearList();
		
		product.addImageString(UUID.randomUUID().toString() + "_" + "NEWIMAGE.jpg");
		product.addImageString(UUID.randomUUID().toString() + "_" + "NEWIMAGE2.jpg");
		product.addImageString(UUID.randomUUID().toString() + "_" + "NEWIMAGE3.jpg");
		
		productRepository.save(product);
		
	}
	
	// @Test
	public void testUpdate()
	{
		
		Long pno = 5L;
		
		productRepository.updateDelete(pno, true);
		
	}
	
	// @Test
	public void testRead2()
	{
		Long pno = 1L;
		
		Optional<Product> result = productRepository.selectOne(pno);
		Product product = result.orElseThrow();
		
		log.info(product);
		log.info(product.getImageList());
		
	}
	
	// 상품 조회 Test
	@Transactional
	// @Test
	public void testRead()
	{
		Long pno = 1L;
		
		Optional<Product> result = productRepository.findById(pno);
		Product product = result.orElseThrow();
		
		log.info(product);
		log.info(product.getImageList());
		
	}
	
	// @Test
	public void testInsert()
	{
		for(int i = 0; i < 10; i++)
		{	
			Product product = Product.builder()
				.pname("상품" + i)
				.price(100 * i)
				.pDesc("상품설명" + i)
				.build();
			
			product.addImageString(UUID.randomUUID().toString() + "_" + "IMAGE1.jpg");
			product.addImageString(UUID.randomUUID().toString() + "_" + "IMAGE2.jpg");
			
			productRepository.save(product);
			
		}
	}
	
}








