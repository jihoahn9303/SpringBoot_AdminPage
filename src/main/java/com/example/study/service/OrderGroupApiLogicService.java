package com.example.study.service;

import com.example.study.model.entity.OrderGroup;
import com.example.study.model.enumclass.OrderStatus;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.OrderGroupApiRequest;
import com.example.study.model.network.response.OrderGroupApiResponse;
import com.example.study.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderGroupApiLogicService extends BasicService<OrderGroupApiRequest, OrderGroupApiResponse, OrderGroup> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Header<OrderGroupApiResponse> create(Header<OrderGroupApiRequest> request) {
        OrderGroupApiRequest body = request.getData();

        if(body != null)
        {
            OrderGroup orderGroup = OrderGroup.builder()
                .status(OrderStatus.DEPOSIT_WAITING).revName(body.getRevName()).revAddress(body.getRevAddress())
                .totalQuantity(body.getTotalQuantity()).totalPrice(body.getTotalPrice()).paymentType(body.getPaymentType())
                .orderType(body.getOrderType()).orderAt(LocalDateTime.now()).arrivalDate(LocalDate.now().plusDays(2))
                .user(userRepository.getOne(body.getUserId())).build();

            OrderGroup newOrderGroup = baseRepository.save(orderGroup);

            return Header.OK(response(newOrderGroup));
        }

        else return null;
    }

    @Override
    public Header<OrderGroupApiResponse> read(Long id) {
        Optional<OrderGroup> optional = baseRepository.findById(id);

        return optional.map(orderGroup -> Header.OK(response(orderGroup)))
                .orElseGet(() ->Header.ERROR("해당 id의 주문 그룹 내역 없음"));
    }

    @Override
    public Header<OrderGroupApiResponse> update(Header<OrderGroupApiRequest> request) {
        OrderGroupApiRequest body = request.getData();

        Optional<OrderGroup> optional = baseRepository.findById(request.getData().getId());

        return optional.map(orderGroup -> {
            orderGroup.setStatus(body.getStatus()).setRevName(body.getRevName()).setRevAddress(body.getRevAddress())
                    .setTotalQuantity(body.getTotalQuantity()).setTotalPrice(body.getTotalPrice()).setOrderType(body.getOrderType())
                    .setOrderType(body.getOrderType()).setUser(userRepository.getOne(body.getUserId()));

            OrderGroup newOrderGroup = baseRepository.save(orderGroup);
            return Header.OK(response(newOrderGroup));
        }).orElseGet(() -> Header.ERROR("해당 id의 주문 그룹 내역 없음"));
    }

    @Override
    public Header delete(Long id) {
        Optional<OrderGroup> optional = baseRepository.findById(id);

        return optional.map(orderGroup -> {
            baseRepository.delete(orderGroup);
            return Header.OK();
        }).orElseGet(() -> Header.ERROR("해당 id의 주문 그룹 내역 없음"));
    }

    @Override
    public Header<List<OrderGroupApiResponse>> search(Pageable pageable) {
        Page<OrderGroup> orderGroups = baseRepository.findAll(pageable);

        List<OrderGroupApiResponse> orderGroupApiResponseList = orderGroups.stream()
                .map(orderGroup -> response(orderGroup)).collect(Collectors.toList());

        return Header.OK(orderGroupApiResponseList);
    }

    public OrderGroupApiResponse response(OrderGroup orderGroup) {
        OrderGroupApiResponse response = OrderGroupApiResponse.builder()
                .status(orderGroup.getStatus()).orderType(orderGroup.getOrderType()).totalQuantity(orderGroup.getTotalQuantity())
                .totalPrice(orderGroup.getTotalPrice()).paymentType(orderGroup.getPaymentType()).revName(orderGroup.getRevName())
                .revAddress(orderGroup.getRevAddress()).orderAt(orderGroup.getOrderAt()).arrivalDate(LocalDateTime.now().plusDays(2))
                .userId(orderGroup.getUser().getId()).id(orderGroup.getId()).build();

        return response;
    }
}
