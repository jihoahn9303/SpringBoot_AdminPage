package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.Item;
import com.example.study.model.entity.OrderDetail;
import com.example.study.model.enumclass.OrderStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public class OrderDetailRepositoryTest extends StudyApplicationTests {

    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void create() {
        OrderDetail orderDetail = new OrderDetail();
        int quantity = 1;
        Long itemId = 1L;
        Long orderGroupId = 1L;
        BigDecimal totalPrice;

        Optional<Item> item = itemRepository.findById(itemId);
        totalPrice = BigDecimal.valueOf(item.map(i -> i.getPrice()).get().intValue() * quantity);

        orderDetail.setStatus(OrderStatus.DEPOSIT_WAITING);
        orderDetail.setArrivalDate(LocalDate.now().plusDays(2));
//        orderDetail.setItemId(itemId);
        orderDetail.setQuantity(quantity);
        orderDetail.setTotalPrice(totalPrice);
//      orderDetail.setOrderGroupId(orderGroupId);  // 어떠한 장바구니에
//        orderDetail.setItemId(itemId);  // 어떠한 상품

        OrderDetail newOrderDetail = orderDetailRepository.save(orderDetail);
        Assertions.assertNotNull(newOrderDetail);

    }
}
