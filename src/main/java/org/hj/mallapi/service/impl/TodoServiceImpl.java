package org.hj.mallapi.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hj.mallapi.domain.Todo;
import org.hj.mallapi.dto.PageRequestDTO;
import org.hj.mallapi.dto.PageResponseDTO;
import org.hj.mallapi.dto.TodoDTO;
import org.hj.mallapi.repository.TodoRepository;
import org.hj.mallapi.service.TodoService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Service
@Log4j2
@RequiredArgsConstructor
public class TodoServiceImpl  implements TodoService{
    private final TodoRepository todoRepository;

    public TodoDTO get(Long tno) {
        // TODO Auto-generated method stub

        Optional<Todo> result = todoRepository.findById(tno);
        Todo todo = result.orElseThrow();
        return entityToDTO(todo);
    }

    @Override
    public Long register(TodoDTO tDto) {
        // TODO Auto-generated method stub
        
        Todo todo = dtoToEntity(tDto);
        Todo result = todoRepository.save(todo);
        return result.getTno();
    }

    @Override
    public void modify(TodoDTO tDto) {
        // TODO Auto-generated method stub

        Optional<Todo> result = todoRepository.findById(tDto.getTno());
        Todo todo = result.orElseThrow();

        todo.changeComplete(tDto.isComplete());
        todo.changeDueDate(tDto.getDueDate());
        todo.changeTitle(tDto.getTitle());

        todoRepository.save(todo);
    }

    @Override
    public void remove(Long tno) {
        // TODO Auto-generated method stub
        todoRepository.deleteById(tno);
    }

    @Override
    public PageResponseDTO<TodoDTO> getList(PageRequestDTO pageRequestDTO) {
        // TODO Auto-generated method stub
   
        Page <Todo> result = todoRepository.search1(pageRequestDTO);
        
        // Todo -> List<TodoDto>
        List<TodoDTO> dtoList = result
                                    .get()
                                    .map(todo -> entityToDTO(todo)).collect(Collectors.toList());
        
        
        PageResponseDTO<TodoDTO> responseDTO = PageResponseDTO.<TodoDTO>withAll()
                                                        .dtoList(dtoList)
                                                        .pageRequestDTO(pageRequestDTO)
                                                        .total(result.getTotalElements())
                                                        .build();


        return responseDTO;

    }

    
}
