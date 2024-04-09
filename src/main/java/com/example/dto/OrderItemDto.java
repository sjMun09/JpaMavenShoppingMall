package com.example.dto;

import com.example.entity.OrderItem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/**
 * 조회한 주문 데이터를 화면에 보낼 때 사용할 Dto
 */
public class OrderItemDto {
    private String itemNm;
    private int count;
    private int orderPrice;
    private String imgUrl;

    public OrderItemDto(OrderItem orderItem, String imgUrl) {
        this.itemNm = orderItem.getItem().getItemNm();
        this.count = orderItem.getCount();
        this.orderPrice = orderItem.getOrderPrice();
        this.imgUrl = imgUrl; // ? -> 파라미터로 인해.
    }
}
