package com.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * ItemDto 객체를 하나 생성 후 모델에 데이터를 담아서 뷰에 전달할거임.
 */
@Getter
@Setter
public class ItemDto {
    private Long id;
    private String itemNm;
    private int price;
    private String itemDetail;
    private String sellStatCd;
    private LocalDateTime regTime;
    private LocalDateTime updateTime;
}
