package com.fullstack.repository.mallapi;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fullstack.domain.Product;

import jakarta.transaction.Transactional;

public interface ProductRepository extends JpaRepository<Product, Long>{

	// @EntityGraph : 특정 객체 속성의 값이 다른 테이블의 값처럼 활용될때 (Embeddable)
	// 필요시에 JOIN 을 걸어서 데이터를 가져올때 사용함
	// Transactional 을 걸 필요없이 Join 되어 사용됨
	
	@EntityGraph(attributePaths = "imageList")
	@Query("Select p From Product p where p.pno = :pno")
	Optional<Product> selectOne(@Param("pno") Long pno);
	
	// 특정 상품의 판매 여부를 DB 상에 업데이트 하도록 메서드 선언(쿼리 포함)
	
	@Transactional
	@Modifying
	@Query("Update Product p set p.delFlag = :delFlag where p.pno = :pno")
	void updateDelete(@Param("pno") Long pno, @Param("delFlag") boolean delFlag);
	
	// 리액트에서 상품 메인 페이지에서 출력할 상품 정보를 얻어낸다고 가정
	// 이미지 정보까지 가져와야 함
	// 페이지 처리를 해야할 정보도 필요
	
	
	@Query("Select p, pi from Product p left join p.imageList pi where pi.ord = 0 and p.delFlag = false")
	Page<Object[]> selectList(Pageable pageable);
	
	
	
	
}









