package com.shop.entity;

import com.shop.constant.LikeItStatus;
import com.shop.constant.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "likeit")
@Getter @Setter
@ToString
public class Likeit extends BaseEntity {

    @Id
    @Column(name = "likeit_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private LikeItStatus likeItStatus; //좋아요상태

    public static Likeit createLike(Member member){
        Likeit likeit = new Likeit();
        likeit.setMember(member);
        return likeit;
    }
    public void cancelLikeit() {
        this.likeItStatus = LikeItStatus.UN_LIKE_IT;
        /*for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }*/
    }

}