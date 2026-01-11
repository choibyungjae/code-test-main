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
public class CreateProductRequest {
    private String category;
    private String name;
    /*
        문제 : category, name 컬럼에 제약이 없음
        원인 : 제약조건 누락
        개선안 : 길이지정, nullable 설정, category는 enum으로 관리
     */

    public CreateProductRequest(String category) {
        this.category = category;
    }
    /*
        문제 : category만 받는 생성자가 있으면 name 값이 애매해짐.
        원인 : name이 필수값인지 반영되지 않음
        개선안 : name 필수면 생성자 제거, 선택이면 null 허용
     */

    public CreateProductRequest(String category, String name) {
        this.category = category;
        this.name = name;
    }
}

