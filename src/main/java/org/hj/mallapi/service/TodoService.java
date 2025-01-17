package org.hj.mallapi.service;

import org.hj.mallapi.domain.Todo;
import org.hj.mallapi.dto.PageRequestDTO;
import org.hj.mallapi.dto.PageResponseDTO;
import org.hj.mallapi.dto.TodoDTO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TodoService {

    TodoDTO get(Long tno);

    Long register(TodoDTO tDto);
    
    void modify(TodoDTO tDto);
    
    void remove(Long tno);

    PageResponseDTO<TodoDTO> getList(PageRequestDTO pageRequestDTO); 
    
    default TodoDTO entityToDTO(Todo todo) {
        TodoDTO todoDTO= TodoDTO.builder()
                                .tno(todo.getTno())
                                .title(todo.getTitle())
                                .dueDate(todo.getDueDate())
                                .writer(todo.getWriter())
                                .complete(todo.isComplete())
                                .build();

        return todoDTO;               
    }

    default Todo dtoToEntity(TodoDTO todoDTO) {
        Todo todo= Todo.builder()
                                .tno(todoDTO.getTno())
                                .title(todoDTO.getTitle())
                                .dueDate(todoDTO.getDueDate())
                                .writer(todoDTO.getWriter())
                                .complete(todoDTO.isComplete())
                                .build();

        return todo;               
    }
}
