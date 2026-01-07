package com.wjc.codetest.product.service;

import com.wjc.codetest.product.model.request.CreateProductRequest;
import com.wjc.codetest.product.model.request.GetProductListRequest;
import com.wjc.codetest.product.model.domain.Product;
import com.wjc.codetest.product.model.request.UpdateProductRequest;
import com.wjc.codetest.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product create(CreateProductRequest dto) {
        /*
            문제 : dto 값에 검증이 없음
            원인 : 도메인 생성자 검증 부재
            개선안 : controller에서 검증, Product Entity에서 필수값 검증, nullable 등으로 검증
         */
        Product product = new Product(dto.getCategory(), dto.getName());
        return productRepository.save(product);
        /*
            문제 : create는 쓰기 작업인데 @Transactional이 없음
            원인 : 서비스 단 트랜잭션 정책 부재
            개선안 : @Transactional 적용
            검증 : 저장, 수정 시 롤백 되는지 확인
         */
    }

    public Product getProductById(Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        /*
            문제 : isPresent는 가독성이 낮음
            원인 : Optional 사용 방식 미정
            개선안 : orElseThrow로 단순화
         */
        if (!productOptional.isPresent()) {
            /*
                문제 : RuntimeException은 의미가 약함
                원인 : 커스텀 예외
                개선안 ProductNotFoundException 생성
             */
            throw new RuntimeException("product not found");
        }
        return productOptional.get();
    }

    public Product update(UpdateProductRequest dto) {
        /*
            문제 : id, category, name 검증이 없음
            원인 : @Valid, 제약조건 부재
            개선안 : DTO validation 추가, id null체크
         */
        Product product = getProductById(dto.getId());
        /*
            문제 : setter로 직접 변경 시 도메인 규칙 우회 쉬움
            원인 : entity @Setter 공개
            개선안 : changeName/ changeCategory 등 entity에서 메소드로 제한
         */
        product.setCategory(dto.getCategory());
        product.setName(dto.getName());
        /*
            문제 : update는 쓰기 작업인데 @Transactional이 없음
            원인 : 서비스 단 트랜잭션 정책 부재
            개선안 : @Transactional 적용
            검증 : 저장, 수정 시 롤백 되는지 확인
         */
        Product updatedProduct = productRepository.save(product);
        return updatedProduct;

    }

    public void deleteById(Long productId) {
        Product product = getProductById(productId);
        /*
            문제 : delete는 쓰기 작업인데 @Transactional이 없음
            원인 : 서비스 단 트랜잭션 정책 부재
            개선안 : @Transactional 적용
            검증 : 저장, 수정 시 롤백 되는지 확인
         */
        productRepository.delete(product);
    }

    public Page<Product> getListByCategory(GetProductListRequest dto) {
        PageRequest pageRequest = PageRequest.of(dto.getPage(), dto.getSize(), Sort.by(Sort.Direction.ASC, "category"));
        /*
            문제 : 페이지 사이즈 검증 없음
            원인 : DTO validation 부재
            개선안 : @Min, @Max적용
         */
        return productRepository.findAllByCategory(dto.getCategory(), pageRequest);
    }

    public List<String> getUniqueCategories() {
        /*
            문제 : DB 과조회(변경이 잘 안되는 데이터)
            원인 : 캐싱 전략 부재
            개선안 : 캐싱 처리
         */
        return productRepository.findDistinctCategories();
    }
}