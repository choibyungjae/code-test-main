package com.wjc.codetest.product.repository;

import com.wjc.codetest.product.model.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByCategory(String name, Pageable pageable);
    /*
        문제 : 메소드명과 파라미터 불일치
        원인 : 파라미터 네이밍
        개선안 : String category 변경
     */

    @Query("SELECT DISTINCT p.category FROM Product p")
    List<String> findDistinctCategories();
    /*
        문제 : Distinct
        원인 : 정렬, 성능문제
        개선안 : 정렬은 order by, Distinct 제거
     */
}
