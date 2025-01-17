package org.hj.mallapi.service;

import java.time.LocalDate;
import org.hj.mallapi.domain.Todo;
import org.hj.mallapi.dto.PageRequestDTO;
import org.hj.mallapi.dto.TodoDTO;
import org.hj.mallapi.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class TodoServiceTest {

    @Autowired
    TodoService todoService;

    @Autowired
    private TodoRepository todoRepository;

    @Test
    public void testGet(){
        Long tno =33L;
        log.info("######### test result ==" + todoService.get(tno));
    }

    @Test
    public void testRegister() {
        TodoDTO dto = TodoDTO.builder()
                            .dueDate(LocalDate.of(2024, 12, 14))
                            .title("title::Test")
                            .writer("test001")
                            .complete(false)        
                    .build();
    
        log.info("################ test::tno == " + todoService.register(dto));
    }

    @Test
    public void testModify() {

        TodoDTO dto = TodoDTO.builder()
        .dueDate(LocalDate.of(2024, 12, 14))
        .title("title::Modify")
        .writer("test001")
        .complete(true)   
        .tno(20L)     
        .build();

        log.info("####### test Modify == ");
        todoService.modify(dto);

        java.util.Optional<Todo> result = todoRepository.findById(20L);

        log.info("############ " + result.orElseThrow().getTitle());
        
    }


    @Test
    public void testGetList() {
        PageRequestDTO requestDTO = PageRequestDTO.builder().build();

        log.info("###### page : " +requestDTO.getPage() + "  size : " + requestDTO.getSize());
        log.info("#### result == " + todoService.getList(requestDTO));


    }
}
