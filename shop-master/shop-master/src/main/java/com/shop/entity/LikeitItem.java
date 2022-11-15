package com.shop.entity;

import com.shop.constant.LikeItStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name="likeit_item")
public class LikeitItem extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "likeit_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="likeit_id")
    private Likeit likeit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int count;

    public static LikeitItem createLikeItem(Likeit likeit, Item item, int count) {
        LikeitItem likeitItem = new LikeitItem();
        likeitItem.setLikeit(likeit);
        likeitItem.setItem(item);
        likeitItem.setCount(count);
        return likeitItem;
    }
    public void addLikeitCount(int count){
        this.count += count;
    }
    public void unlikeit() {
        this.likeit.setLikeItStatus(LikeItStatus.UN_LIKE_IT);
    }
    public void ilikeit() {
        this.likeit.setLikeItStatus(LikeItStatus.LIKE_IT);
    }
    public void updateLikeCount(int count){
        this.count = count;
    }


}