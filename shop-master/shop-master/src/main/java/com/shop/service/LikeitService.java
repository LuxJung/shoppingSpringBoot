package com.shop.service;

import com.shop.dto.*;
import com.shop.entity.*;
import com.shop.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeitService {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final LikeitRepository likeitRepository;
    private final LikeitItemRepository likeitItemRepository;
    private final OrderService orderService;

    public Long addLike(LikeitItemDto likeitItemDto, String email){
        // 1. 좋아요 추가할 상품 엔티티 조회
        Item item = itemRepository.findById(likeitItemDto.getItemId())
                .orElseThrow(EntityNotFoundException::new);
        // 2. 현재 로그인한 회원 엔티티 조회
        Member member = memberRepository.findByEmail(email);
        // 3. 현재 로그인한 회원의 좋아요 엔티티 조회
        Likeit likeit = likeitRepository.findByMemberId(member.getId());
        // 4. 상품을 처음으로 좋아요할 경우 해당 회원의 좋아요 엔티티를 생성
        if(likeit == null){
            likeit = Likeit.createLike(member);
            likeitRepository.save(likeit);
        }
        // 5.현재 좋아요가 이미 추가되어있는지 조회
        LikeitItem savedLikeitItem = likeitItemRepository
                .findByLikeitIdAndItemId(likeit.getId(), item.getId());

        if(savedLikeitItem != null){
            // 6. 좋아요 했던 상품일 경우 기존 수량에 추가 -----> 좋아요 해제로 변경할것 (했음)
            //savedLikeitItem.unlikeit();
            //System.out.println(String.valueOf(savedLikeitItem.getLikeit().getLikeItStatus()));
            savedLikeitItem.addLikeitCount(likeitItemDto.getCount());
            return savedLikeitItem.getId();
        } else {// 좋아요가 처음일 경우
            // 7. 좋아요 엔티티, 상품 엔티티 좋아요 수를 이용해 LikeItem 엔티티 생섵
            LikeitItem likeitItem =
                    LikeitItem
                    .createLikeItem(likeit, item, likeitItemDto.getCount());
            // 8.좋아요 추가할 상품을 저장
            likeitItemRepository.save(likeitItem);
            return likeitItem.getId();
        }
    }




/*
    @Transactional(readOnly = true)
    public List<CartDetailDto> getCartList(String email){

        List<CartDetailDto> cartDetailDtoList = new ArrayList<>();

        Member member = memberRepository.findByEmail(email);
        Cart cart = cartRepository.findByMemberId(member.getId());
        if(cart == null){
            return cartDetailDtoList;
        }

        cartDetailDtoList = cartItemRepository.findCartDetailDtoList(cart.getId());
        return cartDetailDtoList;
    }

    @Transactional(readOnly = true)
    public boolean validateCartItem(Long cartItemId, String email){
        Member curMember = memberRepository.findByEmail(email);
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);
        Member savedMember = cartItem.getCart().getMember();

        if(!StringUtils.equals(curMember.getEmail(), savedMember.getEmail())){
            return false;
        }

        return true;
    }

    public void updateCartItemCount(Long cartItemId, int count){
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);

        cartItem.updateCount(count);
    }

    public void deleteCartItem(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);
        cartItemRepository.delete(cartItem);
    }

    public Long orderCartItem(List<CartOrderDto> cartOrderDtoList, String email){
        List<OrderDto> orderDtoList = new ArrayList<>();

        for (CartOrderDto cartOrderDto : cartOrderDtoList) {
            CartItem cartItem = cartItemRepository
                            .findById(cartOrderDto.getCartItemId())
                            .orElseThrow(EntityNotFoundException::new);

            OrderDto orderDto = new OrderDto();
            orderDto.setItemId(cartItem.getItem().getId());
            orderDto.setCount(cartItem.getCount());
            orderDtoList.add(orderDto);
        }

        Long orderId = orderService.orders(orderDtoList, email);
        for (CartOrderDto cartOrderDto : cartOrderDtoList) {
            CartItem cartItem = cartItemRepository
                            .findById(cartOrderDto.getCartItemId())
                            .orElseThrow(EntityNotFoundException::new);
            cartItemRepository.delete(cartItem);
        }

        return orderId;
    }
*/
}