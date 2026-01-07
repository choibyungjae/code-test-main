package com.wjc.codetest.product.model.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
/*
    문제 : @Setter 전체 사용 시 모든 필드를 자유롭게 변경 가능(엔티티 무결성)
    원인 : 편의성 위주
    개선안 : 엔티티에는 @Getter만 사용
 */
public class Product {

    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    /*
        문제 : GenerationType.AUTO 는 DB에 따라서 생성 방식이 다름
        원인 : ID 전략 명시 부족
        개선안 : DB 환경 별 생성 방식 차이를 관리
     */
    private Long id;

    @Column(name = "category")
    /*
        문제 : category 컬럼에 제약이 없음(길이, null)
        원인 : 제약조건 누락
        개선안 : 길이지정, nullable 설정, enum으로 관리
     */
    private String category;

    @Column(name = "name")
    /*
        문제 : name 컬럼에 제약이 없음(길이, null)
        원인 : 제약 조건 누락
        개선안 : 길이지정, nullable 설정
     */
    private String name;

    protected Product() {
    }

    public Product(String category, String name) {
        /*
            문제 : 생성자에서 파라미터 검증 없음
            원인 : 제약조건 누락
            개선안 : 생성 시점에 파라미터 검증
         */
        this.category = category;
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }
    /*
        문제 : @Getter가 있음에도 Getter선언
        원인 : Lombok 혼선
        개선안 : 수동 Getter제거
     */
}
