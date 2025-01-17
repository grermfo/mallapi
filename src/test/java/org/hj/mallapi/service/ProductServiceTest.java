package org.hj.mallapi.service;

import org.hj.mallapi.dto.PageRequestDTO;
import org.hj.mallapi.dto.PageResponseDTO;
import org.hj.mallapi.dto.ProductDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class ProductServiceTest {

    @Autowired
    private ProductService pService;

    @Test
    public void testList() {
        PageRequestDTO reqDTO = PageRequestDTO.builder().build();

        PageResponseDTO<ProductDTO> result = pService.getList(reqDTO);
        log.info("###### ProductServiceTest :: testList ######");
        result.getDtoList().forEach(dto -> log.info(dto));
    }


}
