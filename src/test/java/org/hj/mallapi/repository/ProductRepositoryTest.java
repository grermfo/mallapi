package org.hj.mallapi.repository;


import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import org.hj.mallapi.domain.Product;
import org.hj.mallapi.dto.PageRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2

public class ProductRepositoryTest {

    @Autowired
    private ProductRepository pRepository;

    @Test
    public void testInsert() {

        for(int i=0; i < 10;i++) {
            Product p = Product.builder()
                        .pName("상품_" + i)
                        .price(1000 * i)
                        .pDesc("상품설명_" + i)    
                        .build();

            p.addImageString(UUID.randomUUID().toString() + "_image01.jpg");
            p.addImageString(UUID.randomUUID().toString() + "_image02.jpg");

            pRepository.save(p);
        }
    }


    @Transactional
    @Test
    public void testRead() {
        Long pno = 1l;

        Optional<Product> result = pRepository.findById(pno);

        Product p = result.orElseThrow();

        log.info("########## testRead :: product ==" + p);
        log.info("############### prodct :: imageList == " + p.getImageList());
    }
   
    @Test
    public void testRead2() {
        Long pno = 1l;

        Optional<Product> result = pRepository.selectOne(pno);

        Product p = result.orElseThrow();

        log.info("########## testRead2 :: product ==" + p);
        log.info("############### prodct :: imageList == " + p.getImageList());
    }


    @Commit
    @Transactional
    @Test
    public void testDelete() {

        Long pno = 1l;

        pRepository.updateToDelete(pno, true);
    }

    @Test
    public void testUpdate() {
        Long pno = 2L;

        Product p = pRepository.selectOne(pno).get();

        p.changeName("상품2!!!");
        p.changeDesc("상품2 설명!!!!!!");
        p.changePricd(5000);

        p.clearList();

        p.addImageString(UUID.randomUUID().toString() + "_New_image01.jpg");
        p.addImageString(UUID.randomUUID().toString() + "_New_image02.jpg");

        pRepository.save(p);

    }

    @Test
    public void testSelectList() {
        Pageable pageable = PageRequest.of(0,10, Sort.by("pno").descending());
        Page<Object[]> result = pRepository.selectList(pageable);
        result.getContent().forEach(arr -> log.info("############## list :: " + Arrays.toString(arr)));
    }

    @Test
    public void testSearch(){
        PageRequestDTO reqDTO =PageRequestDTO.builder().build();
        pRepository.searchList(reqDTO);
    }
}
                      

