package org.hj.mallapi.controller;

import java.util.Map;

import org.hj.mallapi.dto.PageRequestDTO;
import org.hj.mallapi.dto.PageResponseDTO;
import org.hj.mallapi.dto.TodoDTO;
import org.hj.mallapi.service.TodoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/todo")
public class TodoController {
    
    private final TodoService todoService;

    @GetMapping("/{tno}")
    public TodoDTO get(@PathVariable("tno") Long tno) {
        
        log.info("############## controller :: get == " + tno);
        return todoService.get(tno);

    }

    @GetMapping("/list")
    public PageResponseDTO<TodoDTO> list(PageRequestDTO reqDTO) {

        log.info("############### controller :: list :: reqDTO ==" + reqDTO); 
        
        return todoService.getList(reqDTO);

    }
   
    @PostMapping("/")
    public Map<String , Long> register(@RequestBody TodoDTO dto) {
        //TODO: process POST request
        

        log.info("######### register :: TodoDTO == " + dto);

        Long tno = todoService.register(dto);
        return Map.of("TNO", tno);
        
    }

    @PutMapping("/{tno}")
    public Map<String, String> modify(@PathVariable(name="tno") Long tno, @RequestBody TodoDTO todoDTO) {
        todoDTO.setTno(tno);
        log.info("############## ** modify :: " + todoDTO);
        todoService.modify(todoDTO);

        return Map.of("rstMsg" ,"SUCCESS");

    }
    
    @DeleteMapping("/{tno}")
    public Map<String, String> remove (@PathVariable(name="tno") Long tno) {
        log.info("################ remove :: tno = " + tno);
        todoService.remove(tno);
        return Map.of("rstMsg" , "SUCCESS");
    }
    
    
}
