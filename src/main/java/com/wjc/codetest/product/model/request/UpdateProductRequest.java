package com.wjc.codetest.product.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/*
    문제 : @Setter가 있어서 생성 이후 값 변경될 수 있음
    원인 : DTO를 가변 객체로 설계
    개선안 : Request는 가능한 불변으로 설계 @Setter제거
 */
public class UpdateProductRequest {
    private Long id;
    private String category;
    private String name;

    /*
        문제 : category, name 컬럼에 제약이 없음
        원인 : 제약조건 누락
        개선안 : 길이지정, nullable 설정, category는 enum으로 관리
     */

    public UpdateProductRequest(Long id) {
        this.id = id;
    }

    public UpdateProductRequest(Long id, String category) {
        this.id = id;
        this.category = category;
    }

    public UpdateProductRequest(Long id, String category, String name) {
        this.id = id;
        this.category = category;
        this.name = name;
    }

    /*
        문제 : 생성자 오버로드가 많아 필드별 선택, 필수인지 모름
        원인 : 정책이 반영되지 않음
        개선안 : 정책 결정
     */
}

