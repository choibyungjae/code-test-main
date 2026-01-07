package com.wjc.codetest.product.controller;

import com.wjc.codetest.product.model.request.CreateProductRequest;
import com.wjc.codetest.product.model.request.GetProductListRequest;
import com.wjc.codetest.product.model.domain.Product;
import com.wjc.codetest.product.model.request.UpdateProductRequest;
import com.wjc.codetest.product.model.response.ProductListResponse;
import com.wjc.codetest.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
/*
    문제 : @RequestMapping에 base path가 없어서 모든 API가 루트 하위에 직접 노출
    원인 : 컨트롤러 prefix 설계 누락
    개선안 : @RequestMapping("/products")로 base path지정
    검증 : 컨트롤러 추가 시 endpoint 충돌 확인
 */
@RequestMapping
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping(value = "/get/product/by/{productId}")
    /*
        문제 : RESTful API 규칙에 어긋남
        원인 : 명사 중심이 아닌 동사 중심 URL 설계
        개선안 : GET /products/{id} 형태로 단순화
        검증 : RESTful API 규칙 기준
     */
    public ResponseEntity<Product> getProductById(@PathVariable(name = "productId") Long productId){
        /*
            문제 : product 엔티티를 그대로 반환
            원인 : 응답 DTO 분리 부족
            개선안 : productResponse DTO 생성 후 필요한 컬럼만 노출
         */
        Product product = productService.getProductById(productId);
        return ResponseEntity.ok(product);
    }

    @PostMapping(value = "/create/product")
    /*
        문제 : 생성 API 인데 200 OK return, 입력값 검증 없음
        원인 : HTTP Status code 의미 미반영
        개선안 : 생성은 201 Created, DTO에 @NotNull, @NotBlank 적용
     */
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest dto){
        Product product = productService.create(dto);
        return ResponseEntity.ok(product);
    }

    @PostMapping(value = "/delete/product/{productId}")
    /*
        문제 : 삭제에 POST 사용
        원인 : 행위 중심 API 설계
        개선안 : DELETE /products/{id}
     */
    public ResponseEntity<Boolean> deleteProduct(@PathVariable(name = "productId") Long productId){
        productService.deleteById(productId);
        return ResponseEntity.ok(true);
    }

    @PostMapping(value = "/update/product")
    /*
        문제 : update에 id가 없음 PUT/PATCH 불명확
        원인 : 리소스 중심 설계 미흡
        개선안 : PUT /products/{id}, PATCH /products/{id} 선택

     */
    public ResponseEntity<Product> updateProduct(@RequestBody UpdateProductRequest dto){
        Product product = productService.update(dto);
        return ResponseEntity.ok(product);
    }

    @PostMapping(value = "/product/list")
    /*
        문제 : list 조회에 POST 사용
        원인 : 검색조건은 @RequestBody로 받는 방식
        개선안 : GET /products?name=000&category=000&page=1 ...

     */
    public ResponseEntity<ProductListResponse> getProductListByCategory(@RequestBody GetProductListRequest dto){
        Page<Product> productList = productService.getListByCategory(dto);
        return ResponseEntity.ok(new ProductListResponse(productList.getContent(), productList.getTotalPages(), productList.getTotalElements(), productList.getNumber()));
    }

    @GetMapping(value = "/product/category/list")
    /*
        문제 : 메소드 명이 getProductListByCategory로 list 조회 api와 중복
        원인 : 역할 기반 네이밍 부족
        개선안 : getCategory(), getUniqueCategory() 등으로 변경
     */
    public ResponseEntity<List<String>> getProductListByCategory(){
        List<String> uniqueCategories = productService.getUniqueCategories();
        return ResponseEntity.ok(uniqueCategories);
    }
}