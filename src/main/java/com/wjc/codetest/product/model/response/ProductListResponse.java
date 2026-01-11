package com.wjc.codetest.product.model.response;

import com.wjc.codetest.product.model.domain.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author : 변영우 byw1666@wjcompass.com
 * @since : 2025-10-27
 */
@Getter
@Setter
/*
    문제 : @Setter가 있어서 생성 이후 값 변경될 수 있음
    원인 : DTO를 가변 객체로 설계
    개선안 : Request는 가능한 불변으로 설계 @Setter제거
 */
public class ProductListResponse {
    private List<Product> products;
    /*
        문제 : Product Entity를 그대로 노출
        원인 : DTO분리 부족
        개선안 : 응답 전용 DTO로 변환 후 필요한 부분만 노출
     */
    private int totalPages;
    private long totalElements;
    private int page;

    public ProductListResponse(List<Product> content, int totalPages, long totalElements, int number) {
        this.products = content;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.page = number;
    }
    /*
        문제 : content, number 같은 의미 모호
        원인 : Page 용어 그대로 가져옴
        개선안 : products, pageIdx 같은 네이밍 정책
     */
}
