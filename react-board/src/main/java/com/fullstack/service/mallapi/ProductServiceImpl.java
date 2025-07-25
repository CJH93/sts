package com.fullstack.service.mallapi;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fullstack.DTO.PageRequestDTO;
import com.fullstack.DTO.PageResponseDTO;
import com.fullstack.config.CustomServeletConfig;
import com.fullstack.domain.Product;
import com.fullstack.domain.ProductImage;
import com.fullstack.mallapi.dto.ProductDTO;
import com.fullstack.repository.mallapi.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final CustomServeletConfig customServeletConfig;
	
	/*
	 * service 구현체
	 * 레포지터리를 통해서 Page(Object[]) 타입의 결과 데이터 리턴
	 * 각 Object[] 의 내용은 Product 객체와 ProductImage 객체로 매핑
	 * 반복문을 통해서 위 내용으로 정의
	 * Entity --> DTO 로 변환된 것을 List<ProductDTO> 로 처리
	 * 전체 개수를 이용해서 PageResponseDTO 타입으로 생성 이를 반환하도록 정의
	 * 
	 * 1. 파라미터 분석, 요청된 페이지에 대한 Pageable 생성
	 * 2. Page 객체를 얻어냄 (Object[] 타입)
	 * 
	 */
	
	private final ProductRepository productRepository;
	
	private final ModelMapper modelMapper;

	@Override
	public PageResponseDTO<ProductDTO> getList(PageRequestDTO pageRequestDTO) {
		
		Pageable pageable = PageRequest.of(pageRequestDTO.getPage()-1, pageRequestDTO.getSize(), Sort.by("pno").descending());
		
		Page<Object[]> page = productRepository.selectList(pageable);
		
		/*
		 * 3. ResponseDTO 의 필드 값으로 변환
		 * 페이지 내부에 있는 배열 객체를 끌고 와서 각 DTO 로 변환
		 * Mapper 를 사용해서 변환
		 * 변환이 필요한 객체는 DTO 2개 (Product, ProductImage)
		 */
		
		List<ProductDTO> dtoList = page
				.get()
				.map(arr -> 
				{
					Product product = (Product)arr[0];
					ProductImage productImage = (ProductImage)arr[1];
					ProductDTO productDTO = ProductDTO
							.builder()
							.pno(product.getPno())
							.pname(product.getPname())
							.pdesc(product.getPDesc())
							.price(product.getPrice())
							.build();
					
					String imageStr = productImage.getFileName();
					
					// ProductDTO 의 파일 정보 필드에 이 값을 매핑
					productDTO.setUploadFileNames(List.of(imageStr));
					
					return productDTO;
							
				}).collect(Collectors.toList());
		
		// PageResponseDTO 에 나머지 필요한 정보 값을 산출해서 매핑
		
		long totalcount = page.getTotalElements();
		
		return PageResponseDTO.<ProductDTO>withAll()
				.dtoList(dtoList)
				.pageRequestDTO(pageRequestDTO)
				.totalCount(totalcount)
				.build();
	}

	@Override
	public Long register(ProductDTO productDTO) {

		/*
		 * 이 메서드를 이용해서 상품의 정보와 등록된 Image 정보가 DB 상에 저장되어야 함
		 * 
		 * 1. DTO 가 2개로 나뉘어 있음. Product, Image. 두 개를 분리해서 설정
		 * 2. DTO 를 엔티티로 분리해서 변환하는 내부 메서드를 정의(private)
		 *  2-1. 2번이 수행될때 생각해야 할 요소
		 *  : 업로드가 끝나 파일의 이름 리스트를 DTO 가 담고있음
		 *    이를 get 해서 조건에 따라 (null 일 수 있음), 만약 존재 하면 Product 의 메서드를 통해 업로드된 이름 값을 저장해야함
		 *  
		 * 3. 이 메서드를 통해 요청된 DTO -> Entity 로 생성
		 * 
		 * 4. 엔티티 저장
		 * 
		 * 5. 저장된 엔티티의 pno 리턴
		 */
		
		Product product = dtoToEntity(productDTO);
		
		productRepository.save(product);
		
		return product.getPno();
	
	}
	
	

	@Override
	public ProductDTO get(Long pno) 
	{
		
		Optional<Product> result = productRepository.selectOne(pno);

		Product product = result.orElseThrow();
		
		ProductDTO productDTO = entityToDTO(product);
		
		return productDTO;
	}

	@Override
	public void modify(ProductDTO productDTO) {

		Optional<Product> result = productRepository.findById(productDTO.getPno());
		
		if(result.isPresent())
		{
			Product entity = result.get();
			
			// 엔티티 내용 변경
			entity.changeDesc(productDTO.getPdesc());
			entity.changeName(productDTO.getPname());
			entity.changePrice(productDTO.getPrice());
			entity.changeDel(productDTO.isDelFlag());
			
			entity.clearList(); // 기존 정보 날림
			
			List<String> uploadFileNames = productDTO.getUploadFileNames();
			
			if(uploadFileNames != null && productDTO.getUploadFileNames().size() > 0)
			{
				uploadFileNames.stream().forEach(uploadName -> {
					entity.addImageString(uploadName);
				});
			}
			
			productRepository.save(entity);
		
		}
	}

	@Override
	public void remove(Long pno) {

		productRepository.deleteById(pno);
	
	}
	
}









