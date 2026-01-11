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
public class GetProductListRequest {
    private String category;
    /*
        문제 : category 컬럼에 제약이 없음
        원인 : 제약조건 누락
        개선안 : 길이지정, nullable 설정
     */
    private int page;
    private int size;
    /*
        문제 : page, size 제약 조건이 없음
        원인 : 제약조건 부재
        개선안, @Min, @Max 등 적용
     */
}