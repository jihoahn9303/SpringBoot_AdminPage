package com.example.study.model.entity;

import com.example.study.model.enumclass.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"orderGroup", "item"})
@Builder
@Accessors(chain = true)
public class OrderDetail extends Auditable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private OrderStatus status;     // 입금 확인중 / 입금 확인 완료 / 배송 대기 / 배송 중 / 배송 완료

    private LocalDate arrivalDate;

    private Integer quantity;

    private BigDecimal totalPrice;

    // N : 1(order_group)
    // @ManyToOne : Hibernate를 통해 연관관계를 설정할 때, 반드시 객체의 타입을 명시해야함.
    @ManyToOne
    private OrderGroup orderGroup;
    @ManyToOne
    private Item item;

}
