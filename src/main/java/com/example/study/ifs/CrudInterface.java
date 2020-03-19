package com.example.study.ifs;

import com.example.study.model.network.Header;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import java.util.List;

// <Request, Response>
public interface CrudInterface<T, U> {

    Header<U> create(Header<T> t);    // todo request object 추가

    Header<U> read(Long id);

    Header<U> update(Header<T> t);

    Header delete(Long id);

    Header<List<U>> search(@PageableDefault Pageable pageable);
}
