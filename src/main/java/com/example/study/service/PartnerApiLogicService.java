package com.example.study.service;

import com.example.study.model.entity.Partner;
import com.example.study.model.enumclass.UserStatus;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.PartnerApiRequest;
import com.example.study.model.network.response.PartnerApiResponse;
import com.example.study.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PartnerApiLogicService extends BasicService<PartnerApiRequest, PartnerApiResponse, Partner> {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Header<PartnerApiResponse> create(Header<PartnerApiRequest> request) {

        PartnerApiRequest body = request.getData();

        Partner partner = Partner.builder()
                .name(body.getName()).status(UserStatus.AWAITING).address(body.getAddress())
                .partnerNumber(body.getPartnerNumber()).businessNumber(body.getBusinessNumber()).ceoName(body.getCeoName())
                .callCenter(body.getCallCenter()).category(categoryRepository.getOne(body.getCategoryId()))
                .id(body.getId()).registeredAt(LocalDateTime.now()).build();

        Partner newPartner = baseRepository.save(partner);

        return Header.OK(response(newPartner));
    }

    @Override
    public Header<PartnerApiResponse> read(Long id) {
        Optional<Partner> optional = baseRepository.findById(id);

        return optional.map(partner -> response(partner)).map(partnerApiResponse -> Header.OK(partnerApiResponse))
                .orElseGet(() -> Header.ERROR("해당 id의 파트너가 존재하지 않음"));
    }

    @Override
    public Header<PartnerApiResponse> update(Header<PartnerApiRequest> request) {

        PartnerApiRequest body = request.getData();

        Optional<Partner> optional = baseRepository.findById(body.getId());

        return optional.map(partner -> {
            partner.setName(body.getName()).setStatus(body.getStatus()).setAddress(body.getAddress())
                    .setCallCenter(body.getCallCenter()).setBusinessNumber(body.getBusinessNumber()).setPartnerNumber(body.getPartnerNumber())
                    .setCeoName(body.getCeoName()).setCategory(categoryRepository.getOne(body.getCategoryId()));

            Partner newPartner = baseRepository.save(partner);

            return response(newPartner);
        }).map(partnerApiResponse -> Header.OK(partnerApiResponse))
                .orElseGet(() -> Header.ERROR("해당 id의 파트너가 존재하지 않음"));
    }

    @Override
    public Header delete(Long id) {
        Optional<Partner> optional = baseRepository.findById(id);

        return optional.map(partner -> {
            baseRepository.delete(partner);
            return Header.OK();
        }).orElseGet(() -> Header.ERROR("해당 id의 파트너가 존재하지 않음"));
    }

    @Override
    public Header<List<PartnerApiResponse>> search(Pageable pageable) {
        Page<Partner> partners = baseRepository.findAll(pageable);

        List<PartnerApiResponse> partnerList = partners.stream()
                .map(partner -> response(partner)).collect(Collectors.toList());

        return Header.OK(partnerList);
    }

    public PartnerApiResponse response(Partner partner) {
        PartnerApiResponse body = PartnerApiResponse.builder()
                .name(partner.getName()).status(partner.getStatus()).address(partner.getAddress())
                .partnerNumber(partner.getPartnerNumber()).businessNumber(partner.getBusinessNumber()).ceoName(partner.getCeoName())
                .callCenter(partner.getCallCenter()).categoryId(partner.getCategory().getId())
                .id(partner.getId()).registeredAt(LocalDateTime.now()).build();

        return body;
    }
}
