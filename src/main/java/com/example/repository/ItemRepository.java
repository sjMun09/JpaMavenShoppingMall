package com.example.repository;

import com.example.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // Could not autowire. No beans of 'ItemRepository' type found. 해결하기 위해 어노테이션 추가
public interface ItemRepository extends JpaRepository<Item, Long> {

    //List<Item> findItemByItemNm(String itemNm); // 이렇게 하는 것이 정석이긴 함.
    // 그런데 엔티티 이름은 생략 가능해서 아래와 같이 구현 가능.
    List<Item> findByItemNm(String itemNm);
}
