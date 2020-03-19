package com.example.study.service;

import com.example.study.model.entity.Category;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.CategoryApiRequest;
import com.example.study.model.network.response.CategoryApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryApiLogicService extends BasicService<CategoryApiRequest, CategoryApiResponse, Category> {

    @Override
    public Header<CategoryApiResponse> create(Header<CategoryApiRequest> request) {
        CategoryApiRequest body = request.getData();

        Category category = Category.builder()
                .title(body.getTitle()).type(body.getType()).build();

        Category newCategory = baseRepository.save(category);

        return Header.OK(response(newCategory));
    }

    @Override
    public Header<CategoryApiResponse> read(Long id) {
        Optional<Category> optional = baseRepository.findById(id);

        return optional.map(category -> Header.OK(response(category)))
                .orElseGet(() -> Header.ERROR("해당 id의 카테고리가 없음"));
    }

    @Override
    public Header<CategoryApiResponse> update(Header<CategoryApiRequest> request) {

        CategoryApiRequest body = request.getData();
        Optional<Category> optional = baseRepository.findById(body.getId());

        return optional.map(category -> {
            category.setType(body.getType()).setTitle(body.getTitle());

            Category newCategory = baseRepository.save(category);

            return Header.OK(response(newCategory));
        }).orElseGet(() -> Header.ERROR("해당 id의 카테고리가 없음"));
    }

    @Override
    public Header delete(Long id) {

        Optional<Category> targetCategory = baseRepository.findById(id);

        return targetCategory.map(category -> {
            baseRepository.delete(category);
            return Header.OK();
        }).orElseGet(() -> Header.ERROR("해당 id의 카테고리가 없음"));
    }

    @Override
    public Header<List<CategoryApiResponse>> search(Pageable pageable) {
        Page<Category> categories = baseRepository.findAll(pageable);

        List<CategoryApiResponse> categoryApiResponseList = categories.stream().map(category -> response(category))
                .collect(Collectors.toList());

        return Header.OK(categoryApiResponseList);
    }

    public CategoryApiResponse response(Category category) {
        CategoryApiResponse body = CategoryApiResponse.builder()
                .type(category.getType()).title(category.getTitle()).id(category.getId()).build();

        return body;
    }
}
