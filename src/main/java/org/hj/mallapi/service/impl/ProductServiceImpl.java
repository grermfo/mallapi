package org.hj.mallapi.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.hj.mallapi.domain.Product;
import org.hj.mallapi.domain.ProductImage;
import org.hj.mallapi.dto.PageRequestDTO;
import org.hj.mallapi.dto.PageResponseDTO;
import org.hj.mallapi.dto.ProductDTO;
import org.hj.mallapi.repository.ProductRepository;
import org.hj.mallapi.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService{

    private final ProductRepository pRepository;

    @Override
    public PageResponseDTO<ProductDTO> getList(PageRequestDTO reqDTO) {

        Pageable pageable = PageRequest.of(reqDTO.getPage() -1, 
                                        reqDTO.getSize(),
                                        Sort.by("pno").descending());

        Page<Object[]> result = pRepository.selectList(pageable);

        List<ProductDTO> dtoList = result.get().map( arr -> {
            Product prd = (Product)arr[0];
            ProductImage prdImg = (ProductImage)arr[1];
            ProductDTO pDTO = ProductDTO.builder()
                                .pno(prd.getPno())
                                .pname(prd.getPName())
                                .pdesc(prd.getPDesc())
                                .price(prd.getPrice())
                                .build();

            String imgStr = prdImg.getFileName();
            pDTO.setUploadFileNames(List.of(imgStr));

            return pDTO;

        }).collect(Collectors.toList());

        long totalCount = result.getTotalElements();

        return PageResponseDTO.<ProductDTO>withAll()
                                .dtoList(dtoList)
                                .total(totalCount)
                                .pageRequestDTO(reqDTO)
        .build();
     }

}
