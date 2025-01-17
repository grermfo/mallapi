package org.hj.mallapi.dto;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.Builder;
//import lombok.Builder;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Data
@Log4j2
public class PageResponseDTO<E> {

    private List<E> dtoList;

    private List<Integer> pageNumList;

    private PageRequestDTO pageRequestDTO;

    private boolean prev, next;

    private int totalCount, prevPage, nextPage, totalPage, current;

    @Builder(builderMethodName = "withAll")
    public PageResponseDTO(List<E> dtoList, PageRequestDTO pageRequestDTO, long total) {

        this.dtoList = dtoList;
        this.pageRequestDTO = pageRequestDTO;
        this.totalCount = (int)total;

        log.info("#################### PageResponseDTO ::  page ==" + pageRequestDTO.getPage());
        log.info("#################### end :: result 1 == " + (pageRequestDTO.getPage() /10.0));
        log.info("#################### end :: result 2 == " + (Math.ceil(pageRequestDTO.getPage() /10.0)));
        log.info("#################### end :: result 3 == " + (Math.ceil(pageRequestDTO.getPage() /10.0)) * 10);

        int end = (int)(Math.ceil(pageRequestDTO.getPage() /10.0)) * 10;
        int start = end - 9;

        //페이지의 마지막값    
        int last = (int)(Math.ceil(totalCount / (double)pageRequestDTO.getSize()));
        
        log.info("#################### last :: last == " + last);
        
        end = end > last ? last : end;
        log.info("#################### end :: result final== " + end);


        this.prev = start > 1;
        this.next = totalCount > end * pageRequestDTO.getSize();

        log.info("############# PageResponseDTO :: start == " + start);
        log.info("#############  PageResponseDTO :: end == " + end);

        this.pageNumList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());

        this.prevPage = prev ? start - 1 : 0;
        this.nextPage = next ? end + 1 : 0;

        log.info("############# prevPage == " + prevPage);
        log.info("############# nextPage == " + nextPage);
        

        this.totalPage = this.pageNumList.size();
        this.current = pageRequestDTO.getPage();

    }

}
