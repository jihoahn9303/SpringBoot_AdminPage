package com.example.study.sample;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.Item;
import com.example.study.model.entity.OrderDetail;
import com.example.study.model.entity.OrderGroup;
import com.example.study.model.entity.User;
import com.example.study.model.enumclass.OrderStatus;
import com.example.study.model.enumclass.OrderType;
import com.example.study.model.enumclass.PaymentType;
import com.example.study.repository.ItemRepository;
import com.example.study.repository.OrderDetailRepository;
import com.example.study.repository.OrderGroupRepository;
import com.example.study.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


// 개선사항 : 특정 유저가 어떤 아이템을 몇 개 구매 했는지에 대해서 기록해보기 -> HashMap 자료구조를 이용하여..

public class OrderDetailSample extends StudyApplicationTests {

    private Random random = new Random();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderGroupRepository orderGroupRepository;

    @Test
    public void createOrder(){

        List<User> userList = userRepository.findAll();

//        for(int j = 0; j < 1; j++){
//            User user = userList.get(j);
//            item(user);
//        }

        userList.forEach(user -> {
            int orderCount = random.nextInt(10) + 1;
            for (int i = 0; i < orderCount; i++) {
                orderItem(user);
            }
        });
    }

    private void orderItem(User user){
        double totalAmount = 0; // value of total_amount column in orderGroup table
        int totalItemQuantity = 0; // total_quantity column in orderGroup table
        int quantity = 0;  // quantity column in orderDetail table

        List<Item> items = new ArrayList<>();
        List<OrderDetail> orderHistoryDetails = new ArrayList<>();
        List<Integer> quantityList = new ArrayList<Integer>();

        int itemCount = random.nextInt(10)+1;  // number of items bought by one user

        // 1. calculate total_price, 2. calculate total_quantity, 3. record # of buying count about one item per one user
        for(int i = 0 ; i < itemCount; i ++){

            int itemNumber = random.nextInt(405)+1;
            quantity = random.nextInt(10)+1;  // buying count about one item per one user

            Item item = itemRepository.findById((long)itemNumber).get();
            totalAmount += (item.getPrice().multiply(BigDecimal.valueOf(quantity))).doubleValue();
            totalItemQuantity += quantity;

            items.add(item);
            quantityList.add(quantity);
        }


        int s = random.nextInt(5)+1;
        OrderStatus status = OrderStatus.DEPOSIT_WAITING;
        PaymentType paymentType = PaymentType.TRANSFER;
        switch (s){
            case 1 :
                status = OrderStatus.DEPOSIT_WAITING;
                paymentType = PaymentType.CARD;
                break;

            case 2 :
                status = OrderStatus.DEPOSIT_FINISH;
                paymentType = PaymentType.CASH;
                break;

            case 3 :
                status = OrderStatus.DELIVER_READY;
                paymentType = PaymentType.TRANSFER;
                break;

            case 4:
                status = OrderStatus.DELIVER_START;
                paymentType = PaymentType.CALL;
                break;

            case 5:
                status = OrderStatus.DELIVER_FINISH;
                paymentType = PaymentType.POINT;
                break;
        }

        int t = random.nextInt(2)+1;
        OrderType type = t==1 ? OrderType.ALL:OrderType.EACH;

        OrderGroup orderGroup = OrderGroup.builder()
                .user(user)
                .status(status)
                .orderType(type)
                .revAddress("경기도 분당구 판교역로")
                .revName(user.getEmail())
                .paymentType(paymentType)
                .totalPrice(new BigDecimal(totalAmount))
                .orderAt(getRandomDateTime())
                .totalQuantity(totalItemQuantity)
                .arrivalDate(getRandomDate().plusDays(3))
                .orderDetailList(orderHistoryDetails)
                .build();

        orderGroupRepository.save(orderGroup);

        for(int i = 0; i < items.size(); i++){

            OrderStatus orderDetailStatus = OrderStatus.DEPOSIT_WAITING;
            switch (random.nextInt(5)+1){
                case 1 :
                    orderDetailStatus = OrderStatus.DEPOSIT_WAITING;
                    break;

                case 2 :
                    orderDetailStatus = OrderStatus.DEPOSIT_FINISH;
                    break;

                case 3 :
                    orderDetailStatus = OrderStatus.DELIVER_READY;
                    break;

                case 4:
                    orderDetailStatus = OrderStatus.DELIVER_START;
                    break;

                case 5:
                    orderDetailStatus = OrderStatus.DELIVER_FINISH;
                    break;
            }

            OrderDetail orderDetail = OrderDetail.builder()
                    .orderGroup(orderGroup)
                    .item(items.get(i))
                    .arrivalDate(type.equals(OrderType.ALL) ? orderGroup.getArrivalDate() : getRandomDate())
                    .status(type.equals(OrderType.ALL) ? status :orderDetailStatus)
                    .quantity(quantityList.get(i))
                    .totalPrice(items.get(i).getPrice().multiply(BigDecimal.valueOf(quantityList.get(i))))
                    .build();
            orderDetailRepository.save(orderDetail);
            orderHistoryDetails.add(orderDetail);
        }
    }

    private LocalDate getRandomDate(){
        return LocalDate.of(2020,getRandomNumber(),getRandomNumber());
    }

    private LocalDateTime getRandomDateTime() {
        return LocalDateTime.of(2020,getRandomNumber(),getRandomNumber(),getRandomNumber(),getRandomNumber(),getRandomNumber());
    }

    private int getRandomNumber(){
        return random.nextInt(11)+1;
    }
}
