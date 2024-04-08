package com.example.repository;

import com.example.constant.ItemSellStatus;
import com.example.entity.Item;
import com.example.entity.QItem;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

// @SpringBootTest(classes = ItemRepository.class) // Could not autowire. No beans of 'ItemRepository' type found. 해결하기 위해 직접 인자로 주입
@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("상품 저장 테스트")
    public void createItemTest() {
        Item item = new Item();
        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setStockNumber(100);
        item.setItemDetail("테스트 상품 상세 설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());
        Item savedItem = itemRepository.save(item);
        System.out.println(savedItem.toString());
    }

    public void createItemList() {
        for (int i = 1; i <= 10; i++) {
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            Item savedItem = itemRepository.save(item);
        }
    }

    @Test
    @DisplayName("상품명 조회 테스트")
    public void findByItemNmTest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemNm("테스트 상품1");
        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("상품명, 상품상세설명 or 테스트")
    public void findByOrItemDetailTest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemNmOrItemDetail("테스트 상품1", "테스트 상품 상세 설명5");
        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

//    @Test
//    @DisplayName("상품명, 상품상세설명 or 테스트2")
//    public void findByOrItemDetailTest2() {
//        this.createItemList();
//        List<Item> itemList = itemRepository.findByItemNmOrItemDetail("테스트 상품2", "테스트 상품 상세 설명4");
//        for (Item item : itemList) {
//            System.out.println(item.toString());
//        }
//    }
    /*
    출력
    Item(id=2, itemNm=테스트 상품2, price=10002, stockNumber=100, itemDetail=테스트 상품 상세 설명2, itemSellStatus=SELL, regTime=2024-03-07T00:43:31.709386, updateTime=2024-03-07T00:43:31.709396)
    Item(id=4, itemNm=테스트 상품4, price=10004, stockNumber=100, itemDetail=테스트 상품 상세 설명4, itemSellStatus=SELL, regTime=2024-03-07T00:43:31.710886, updateTime=2024-03-07T00:43:31.710892)
     */

    @Test
    @DisplayName("가격 LessThan 테스트")
    public void findByPriceLessThan() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByPriceLessThan(10005);
        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("가격 내림차순 조희 테스트")
    public void findByPriceLessThanOrderByPriceDesc() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(10005);
        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("가격 올림차순 조희 테스트")
    public void findByPriceLessThanOrderByPriceAsc() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceAsc(10005);
        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("@Query파라미터를 이용한 상품조회 테스트")
    public void findByItemDetail() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemDetail("테스트 상품 상세 설명");
        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("nativeQuery 속성을 이용한 상품 조회 테스트")
    public void findByItemDetailNative() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemDetailByNative("테스트 상품 상세 설명");
        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    /**
     * JPQL 팩토리를 이용한 상품조회
     */
    @Test
    @DisplayName("Querydsl 조회 테스트1")
    public void queryDslTest() {
        this.createItemList();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em); // 생성자 매개변수로 엔티티 매니저 객체를 넣어줌
        QItem qItem = QItem.item; // 자동으로 생성된 QItem 객체를 이용
        JPAQuery<Item> query = queryFactory.selectFrom(qItem)
                .where(qItem.itemSellStatus.eq(ItemSellStatus.SELL))
                .where(qItem.itemDetail.like("%" + "테스트 상품 상세 설명" + "%"))
                .orderBy(qItem.price.desc());

        List<Item> itemList = query.fetch();

        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    /*
    jpa레포지토리에 QueryDslPredicateExcutor 인터페이스 상속받고 QueryDsl Test 2 시작.
    */
    public void createItemList2() {  // 1
        for (int i = 1; i <= 5; i++) {
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setStockNumber(100);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());

            itemRepository.save(item);
        }
        for (int j = 6; j <= 10; j++) {
            Item item = new Item();
            item.setItemNm("테스트 상품" + j);
            item.setPrice(10000 + j);
            item.setStockNumber(0);
            item.setItemDetail("테스트 상품 상세 설명" + j);
            item.setItemSellStatus(ItemSellStatus.SOLD_OUT);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());

            itemRepository.save(item);
        }
    }

    @Test
    @DisplayName("상품 Querydsl 조회테스트 2")
    public void queryDslTest2() {
        this.createItemList2();

        BooleanBuilder booleanBuilder = new BooleanBuilder(); // 2
        QItem qItem = QItem.item;
        String itemDetail = "테스트 상품 상세 설명";
        int price = 10003;
        String itemSellStat = "SELL";

        booleanBuilder.and(qItem.itemDetail.like("%" + itemDetail + "%")); // 3
        booleanBuilder.and(qItem.price.gt(price));

        if (StringUtils.equals(itemSellStat, ItemSellStatus.SELL)) {
            booleanBuilder.and(qItem.itemSellStatus.eq(ItemSellStatus.SELL));
        }

        Pageable pageable = PageRequest.of(0, 5); // 4
        Page<Item> itemPagingResult =
                itemRepository.findAll(booleanBuilder, pageable); // 5
        System.out.println("total elements : " +
                itemPagingResult.getTotalElements());

        List<Item> resultItemList = itemPagingResult.getContent();
        for (Item resultItem : resultItemList) {
            System.out.println(resultItem.toString());
        }
    }



}