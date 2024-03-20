package com.example.dto;

import com.example.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemSearchDto {
    private String searchDateType; // now와 등록일 비교해서 조회
    private ItemSellStatus searchSellStatus; // 판매상태 기준 조회
    private String searchBy; // 상품 유형 조회 (상품명||상품 등록자 id)
    private String searchQuery = ""; // 검색어를 저장할 변수
}
