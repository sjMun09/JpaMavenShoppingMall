package com.example.repository;

import com.example.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository // Could not autowire. No beans of 'ItemRepository' type found. 해결하기 위해 어노테이션 추가
public interface ItemRepository extends JpaRepository<Item, Long>, QuerydslPredicateExecutor<Item>
            ,ItemRepositoryCustom { // 상품관리 페이지 목록 추가

    //List<Item> findItemByItemNm(String itemNm); // 이렇게 하는 것이 정석이긴 함.
    // 그런데 엔티티 이름은 생략 가능해서 아래와 같이 구현 가능.
    List<Item> findByItemNm(String itemNm);

    List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);

    List<Item> findByPriceLessThan(Integer price);

    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);

    List<Item> findByPriceLessThanOrderByPriceAsc(Integer price);

    /**
     *  jpql
     */
    @Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc")
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);

    @Query(value = "select * from item i where i.item_detail like %:itemDetail% order by i.price desc", nativeQuery = true)
    List<Item> findByItemDetailByNative(@Param("itemDetail")String itemDetail);

}

