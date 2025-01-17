package org.hj.mallapi.repository.search;

import java.util.List;

import org.hj.mallapi.domain.QTodo;
import org.hj.mallapi.domain.Todo;
import org.hj.mallapi.dto.PageRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.querydsl.jpa.JPQLQuery;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class TodoSearchImpl extends QuerydslRepositorySupport implements TodoSearch{

    public TodoSearchImpl() {
        super(Todo.class);
    }

    @Override
    public Page<Todo> search1(PageRequestDTO pageRequestDTO) {
        log.info("########## start TodoSearchImpl::search1");

        QTodo todo = QTodo.todo;
        JPQLQuery<Todo> query = from(todo);
        
        Pageable pageable = PageRequest.of(
                            pageRequestDTO.getPage() -1,
                            pageRequestDTO.getSize(), 
                            Sort.by("tno").descending());
        this.getQuerydsl().applyPagination(pageable, query);

        List <Todo> list = query.fetch(); //목록데이터
        long total =query.fetchCount(); 

        log.info("############ count == " + query.fetchCount());


            return new PageImpl<>(list, pageable, total);
    }
}
