package org.hj.mallapi.repository.search;

import org.hj.mallapi.domain.Todo;
import org.hj.mallapi.dto.PageRequestDTO;
import org.springframework.data.domain.Page;

public interface TodoSearch {

    Page<Todo> search1(PageRequestDTO pageRequestDTO);
}