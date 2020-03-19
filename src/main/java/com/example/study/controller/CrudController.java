package com.example.study.controller;

import com.example.study.ifs.CrudInterface;
import com.example.study.model.network.Header;
import com.example.study.service.BasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Component
public abstract class CrudController<Request, Response, Entity> implements CrudInterface<Request, Response> {

    @Autowired
    protected BasicService<Request, Response, Entity> baseService;

    @Override
    @PostMapping("")
    public Header<Response> create(@RequestBody Header<Request> t) {
        return baseService.create(t);
    }

    @Override
    @GetMapping("{id}")
    public Header<Response> read(@PathVariable Long id) {
        return baseService.read(id);
    }

    @Override
    @GetMapping("")
    public Header<List<Response>> search(@PageableDefault(size = 15, sort = "id", direction = Sort.Direction.ASC)Pageable pageable) { return baseService.search(pageable); }

    @Override
    @PutMapping("")
    public Header<Response> update(@RequestBody Header<Request> t) {
        return baseService.update(t);
    }

    @Override
    @DeleteMapping("{id}")
    public Header delete(@PathVariable Long id) {
        return baseService.delete(id);
    }

}
