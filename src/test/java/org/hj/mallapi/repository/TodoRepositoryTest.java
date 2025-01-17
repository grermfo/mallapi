package org.hj.mallapi.repository;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;

import org.hj.mallapi.domain.Todo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.*;

import java.util.Optional;

@SpringBootTest
@Log4j2
public class TodoRepositoryTest {
    @Autowired
    private TodoRepository todoRepository;

    @Test
    public void test1() {

        //Assertions.assertNotNull(todoRepository);
        log.info("###############################");
        log.info("todoRepository == " + todoRepository);
        log.info("todoRepository == " + todoRepository.getClass().getName());
        

    }

    @Test
    public void testInsert() {
        Todo todo = Todo.builder().title("title1")
                        .dueDate(LocalDate.of(2023,12,11))
                        .writer("write00")
                        .build();
            todoRepository.save(todo);
    }

    @Test
    public void testInsertBy100() {

        for(int i=1; i<=100;i++) {
            Todo todo = Todo.builder().title("title1")
                                    .dueDate(LocalDate.of(2023,12,11))
                                    .writer("write00")
                                    .build();
            todoRepository.save(todo);
        }
    }

    @Test
    public void testRead() {

        Long tno=33L;
        java.util.Optional<Todo> result = todoRepository.findById(tno);
        Todo todo = result.orElseThrow();
        log.info("########### " + todo);
    }    

    @Test
    public void testModify() {
        log.info("######### testModify start");
        
        Long tno=33L;
        Optional<Todo> result = todoRepository.findById(tno);
        Todo todo = result.orElseThrow();
        todo.changeTitle("title_" + tno);
        todo.changeComplete(true);
        todo.changeDueDate(LocalDate.of(2024,12,13));

        todoRepository.save(todo);
    }    

    @Test
    public void testDelete() {

        Long tno = 1L;

        todoRepository.deleteById(tno);
    }

    @Test
    public void testPaging() {

        Pageable pageable = PageRequest.of(0,10, Sort.by("tno").descending());
        Page<Todo> result = todoRepository.findAll(pageable);
        log.info("########## result Total ==" + result.getTotalElements());
        log.info("######### result Content== " + result.getContent()); 

    }

    @Test
    public void testSearch1() {
       // todoRepository.search1();
    }

}
