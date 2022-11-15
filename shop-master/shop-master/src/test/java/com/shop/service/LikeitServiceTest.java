package com.shop.service;

import com.shop.constant.ItemSellStatus;
import com.shop.dto.LikeitItemDto;
import com.shop.entity.Item;
import com.shop.entity.LikeitItem;
import com.shop.entity.Member;
import com.shop.repository.ItemRepository;
import com.shop.repository.LikeitItemRepository;
import com.shop.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
class LikeitServiceTest {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    LikeitService likeitService;

    @Autowired
    LikeitItemRepository likeitItemRepository;

    public Item saveItem(){
        Item item = new Item();
        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("테스트 상품 상세 설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        return itemRepository.save(item);
    }

    public Member saveMember(){
        Member member = new Member();
        member.setEmail("test@test.com");
        return memberRepository.save(member);
    }

    @Test
    @DisplayName("좋아요 추가 테스트")
    public void addLike(){
        Item item = saveItem();
        Member member = saveMember();

        LikeitItemDto likeitItemDto = new LikeitItemDto();
        likeitItemDto.setCount(5);
        likeitItemDto.setItemId(item.getId());

        Long cartItemId = likeitService.addLike(likeitItemDto, member.getEmail());
        LikeitItem cartItem = likeitItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);

        assertEquals(item.getId(), cartItem.getItem().getId());
        assertEquals(likeitItemDto.getCount(), cartItem.getCount());
    }

}