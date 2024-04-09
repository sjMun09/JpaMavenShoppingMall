package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class OrderItem extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; // 주문금액
    private int count; // 수량

    //private LocalDateTime regTime;
    //private LocalDateTime updateTime;

    /**
     * 주문할 상품과 주문수량
     */
    public static OrderItem createOrderItem(Item item, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setCount(count);
        orderItem.setOrderPrice(item.getPrice()); // 현재 시간 기준으로 상품 가격을 주문 가격으로 세팅.
        // -> 상품 가격은 시간에 따라서 달라질 수 있다. 또한 쿠폰이나 할인을 적용하는 케이스들도 잇지만 여기서는 고려하지 않았다.

        item.removeStock(count);
        return orderItem;
    }

    public int getTotalPrice() {
        return orderPrice * count;
    }

    /**
     * 주문 취소
     */
    public void cancel() {
        this.getItem().addStock(count);
    }
}
