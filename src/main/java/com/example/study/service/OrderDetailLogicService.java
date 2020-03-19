package com.example.study.service;

import com.example.study.model.entity.OrderDetail;
import com.example.study.model.enumclass.OrderStatus;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.OrderDetailApiRequest;
import com.example.study.model.network.response.OrderDetailApiResponse;
import com.example.study.repository.ItemRepository;
import com.example.study.repository.OrderGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderDetailLogicService extends BasicService<OrderDetailApiRequest, OrderDetailApiResponse, OrderDetail> {

    @Autowired
    private OrderGroupRepository orderGroupRepository;
    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Header<OrderDetailApiResponse> create(Header<OrderDetailApiRequest> request) {
        OrderDetailApiRequest body = request.getData();

        OrderDetail orderDetail = OrderDetail.builder()
                .status(OrderStatus.DEPOSIT_WAITING).quantity(body.getQuantity()).totalPrice(body.getTotalPrice())
                .arrivalDate(LocalDate.now().plusDays(2)).orderGroup(orderGroupRepository.getOne(body.getOrderGroupId()))
                .item(itemRepository.getOne(body.getItemId())).build();

        OrderDetail newOrderDetail = baseRepository.save(orderDetail);

        return Header.OK(response(newOrderDetail));
    }

    @Override
    public Header<OrderDetailApiResponse> read(Long id) {
        Optional<OrderDetail> optional = baseRepository.findById(id);

        return optional.map(orderDetail -> Header.OK(response(orderDetail)))
                .orElseGet(() -> Header.ERROR("해당 id의 주문 상세 내역이 존재하지 않음"));
    }

    @Override
    public Header<OrderDetailApiResponse> update(Header<OrderDetailApiRequest> request) {

        OrderDetailApiRequest body = request.getData();

        Optional<OrderDetail> optional = baseRepository.findById(body.getId());

        return optional.map(orderDetail -> {
            orderDetail.setStatus(body.getStatus()).setQuantity(body.getQuantity()).setTotalPrice(body.getTotalPrice())
                    .setOrderGroup(orderGroupRepository.getOne(body.getOrderGroupId()))
                    .setItem(itemRepository.getOne(body.getItemId())).setArrivalDate(LocalDate.now().plusDays(3));

            OrderDetail newOrderDetail = baseRepository.save(orderDetail);

            return Header.OK(response(newOrderDetail));
        }).orElseGet(() -> Header.ERROR("해당 id의 주문 상세 내역이 존재하지 않음"));
    }

    @Override
    public Header delete(Long id) {
        Optional<OrderDetail> optional = baseRepository.findById(id);

        return optional.map(orderDetail -> {
            baseRepository.delete(orderDetail);
            return Header.OK();
        }).orElseGet(() -> Header.ERROR("해당 id의 주문 상세 내역이 존재하지 않음"));
    }

    @Override
    public Header<List<OrderDetailApiResponse>> search(Pageable pageable) {
        Page<OrderDetail> orderDetails = baseRepository.findAll(pageable);

        List<OrderDetailApiResponse> orderDetailApiResponses = orderDetails.stream()
                .map(orderDetail -> response(orderDetail)).collect(Collectors.toList());

        return Header.OK(orderDetailApiResponses);
    }

    public OrderDetailApiResponse response(OrderDetail orderDetail) {
        OrderDetailApiResponse body = OrderDetailApiResponse.builder()
                .id(orderDetail.getId()).status(orderDetail.getStatus()).quantity(orderDetail.getQuantity())
                .totalPrice(orderDetail.getTotalPrice()).arrivalDate(orderDetail.getArrivalDate()).itemId(orderDetail.getItem().getId())
                .orderGroupId(orderDetail.getOrderGroup().getId()).build();

        return body;
    }
}
