package com.example.study.service;

import com.example.study.model.entity.Item;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.ItemApiRequest;
import com.example.study.model.network.response.ItemApiResponse;
import com.example.study.repository.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemApiLogicService extends BasicService<ItemApiRequest, ItemApiResponse, Item> {

    @Autowired
    private PartnerRepository partnerRepository;

    @Override
    public Header<ItemApiResponse> create(Header<ItemApiRequest> request) {
        ItemApiRequest body = request.getData();

        Item item = Item.builder()
                .status(body.getStatus())
                .name(body.getName())
                .content(body.getContent())
                .price(body.getPrice())
                .brandName(body.getBrandName())
                .partner(partnerRepository.getOne(body.getPartnerId()))
                .title(body.getTitle())
                .registeredAt(LocalDateTime.now()).build();

        Item newItem = baseRepository.save(item);

        return Header.OK(response(newItem));
    }

    @Override
    public Header<ItemApiResponse> read(Long id) {
        Optional<Item> targetItem = baseRepository.findById(id);

        return targetItem.map(item -> Header.OK(response(item))).orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<ItemApiResponse> update(Header<ItemApiRequest> request) {
        ItemApiRequest itemApiRequest = request.getData();

        Optional<Item> targetItem = baseRepository.findById(itemApiRequest.getId());

        return targetItem.map(item -> {
            item.setName(itemApiRequest.getName()).setStatus(itemApiRequest.getStatus()).setTitle(itemApiRequest.getTitle())
                    .setContent(itemApiRequest.getContent()).setPrice(itemApiRequest.getPrice()).setBrandName(itemApiRequest.getBrandName())
                    .setRegisteredAt(itemApiRequest.getRegisteredAt()).setUnregisteredAt(itemApiRequest.getRegisteredAt());

            baseRepository.save(item);

            return item;
        }).map(newItem-> Header.OK(response(newItem)))
                .orElseGet(() -> Header.ERROR("해당 id 값의 아이템이 존재하지 않음"));
    }

    @Override
    public Header delete(Long id) {
        Optional<Item> targetItem = baseRepository.findById(id);

        return targetItem.map(item -> {
            baseRepository.delete(item);
            return Header.OK();
        }).orElseGet(()-> Header.ERROR("해당 id 값의 아이템이 존재하지 않음"));
    }

    @Override
    public Header<List<ItemApiResponse>> search(Pageable pageable) {
        Page<Item> items = baseRepository.findAll(pageable);

        List<ItemApiResponse> itemApiResponseList = items.stream().map(item -> response(item))
                .collect(Collectors.toList());

        return Header.OK(itemApiResponseList);
    }

    private ItemApiResponse response(Item item) {
            ItemApiResponse itemApiResponse = ItemApiResponse.builder()
                    .id(item.getId()).name(item.getName()).status(item.getStatus()).content(item.getContent()).price(item.getPrice())
                    .brandName(item.getBrandName()).title(item.getTitle()).registeredAt(item.getRegisteredAt())
                    .unregisteredAt(item.getUnregisteredAt()).partnerId(item.getPartner().getId()).build();

            return itemApiResponse;
    }
}
