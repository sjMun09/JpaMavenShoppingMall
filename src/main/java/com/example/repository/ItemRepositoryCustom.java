package com.example.repository;

import com.example.dto.ItemSearchDto;
import com.example.dto.MainItemDto;
import com.example.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 사용자 정의 인터페이스
 */
public interface ItemRepositoryCustom {

    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

    // 메인 페이지에 보여줄 상품 리스트를 가져오는 메서드
    Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
}
