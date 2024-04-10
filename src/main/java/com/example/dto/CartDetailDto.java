package com.example.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 장바구니 조회 페이지에 전달할 DTo
 */
@Getter
@Setter
public class CartDetailDto {

    private Long cartItemId;
    private String itemNm;
    private int price;
    private int count;
    private String imgUrl;

    public CartDetailDto(Long cartItemId, String itemNm, int price, int count, String imgUrl) {
        this.cartItemId = cartItemId;
        this.itemNm = itemNm;
        this.price = price;
        this.count = count;
        this.imgUrl = imgUrl;
    }
}
