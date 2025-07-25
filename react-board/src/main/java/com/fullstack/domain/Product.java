package com.fullstack.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "tbl_product")
@Getter
@ToString(exclude = "ImageList") // toString 시 image 정보는 제외
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
	
	@Id
	@GeneratedValue
	private Long pno;
	
	private String pname;
	
	private int price;
	private boolean delFlag;
	private String pDesc;
	
	@ElementCollection // 하나의 엔티티에 아래의 객체가 값으로 매핑되는 경우
	// 테이블 생성시 관련된 테이블이 자동 생성, 참조키도 자동 설정됨
	// 기본적으로 Lazy 로 설정됨
	@Builder.Default
	private List<ProductImage> imageList = new ArrayList<>();
	
	public void changePrice(int price)
	{
		this.price = price;
	}
	
	public void changeDesc(String pDesc)
	{
		this.pDesc = pDesc;
	}
	
	public void changeName(String pname)
	{
		this.pname = pname;
	}
	
	public void addImage(ProductImage image)
	{
		image.setOrd(this.imageList.size());
		imageList.add(image);
	}
	
	public void addImageString(String filename)
	{
		ProductImage productImage = ProductImage.builder()
				.fileName(filename)
				.build();
		addImage(productImage);
				
	}
	
	// 이미지 전체 삭제
	public void clearList()
	{
		this.imageList.clear();
	}
	
	// 상품의 판매 여부를 수정하는 메서드 정의
	public void changeDel(boolean delFlag)
	{
		this.delFlag = delFlag;
	}
	
}











