package com.shop.repository;

import com.shop.entity.LikeitItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeitItemRepository extends JpaRepository<LikeitItem, Long> {

    LikeitItem findByLikeitIdAndItemId(Long likeitId, Long itemId);
/*
    @Query("select new com.shop.dto.LikeDetailDto(li.id, i.itemNm, i.price, li.count, im.imgUrl) " +
            "from LikeItem li, ItemImg im " +
            "join li.item i " +
            "where li.like.id = :likeId " +
            "and im.item.id = li.item.id " +
            "and im.repimgYn = 'Y' " +
            "order by li.regTime desc"
            )
    List<LikeDetailDto> findLikeDetailDtoList(Long likeId);
*/
}