package com.fullstack.service.mallapi;

import java.util.List;

import com.fullstack.DTO.PageRequestDTO;
import com.fullstack.DTO.PageResponseDTO;
import com.fullstack.domain.Product;
import com.fullstack.domain.ProductImage;
import com.fullstack.mallapi.dto.ProductDTO;

public interface ProductService {

	// List 페이지를 구성할 수 있도록 상품의 정보를 리턴하는 Service layer 정의

	/*
	 * mallapi 패키지에 ProductService 생성 리턴 타입으로 요청된 페이지의 정보를 담고있는 PageResponseDTO 를
	 * 메서드 이름은 getList. 요청 파라미터는 생각해서 매핑
	 */
	
	PageResponseDTO<ProductDTO> getList(PageRequestDTO pageRequestDTO);

	Long register(ProductDTO productDTO);
	
	default Product dtoToEntity(ProductDTO productDTO)
	{
		Product product = Product.builder()
				.pno(productDTO.getPno())
				.pname(productDTO.getPname())
				.price(productDTO.getPrice())
				.pDesc(productDTO.getPdesc())
				.delFlag(productDTO.isDelFlag())
				.build();
		
		// ProductDTO 내부에는 이미지 첨부를 통해서 이미지를 등록하면
		// 갯수만큼 저장된 파일 이름 값이 들어가있음
		// 이 값을 get 해서 entity 에도 setting 해야함
		
		// 업로드가 끝난 파일들의 이름 정보
		
		List<String> uploadFileNames = productDTO.getUploadFileNames();
		
		if(uploadFileNames == null)
			return product;
		
		// 이름이 존재하면 list 를 순회하면서 이름 값 추출 및 setting
		uploadFileNames.forEach(uploadName -> {
			product.addImageString(uploadName);
		});		
		return product;
	}
	
	ProductDTO get(Long pno);
	
	default ProductDTO entityToDTO(Product product)
	{
		ProductDTO productDTO = ProductDTO.builder()
				.pno(product.getPno())
				.pname(product.getPname())
				.price(product.getPrice())
				.pdesc(product.getPDesc())
				.delFlag(product.isDelFlag())
				.build();
		
		// 이미지 List 정보 세팅
		List<ProductImage> imageList = product.getImageList();
		
		if(imageList == null || imageList.size() == 0)
		{
			return productDTO;	
		}
		
		List<String> fileNameList = imageList.stream().map(productImage -> productImage.getFileName()).toList();
		
		productDTO.setUploadFileNames(fileNameList);
		
		return productDTO;
	}
	
	void modify(ProductDTO productDTO);
	
	void remove(Long pno);
	
	
}




