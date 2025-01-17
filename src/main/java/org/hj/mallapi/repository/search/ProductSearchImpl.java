package org.hj.mallapi.repository.search;

import java.util.List;

import org.hj.mallapi.domain.Product;
import org.hj.mallapi.domain.QProduct;
import org.hj.mallapi.domain.QProductImage;
import org.hj.mallapi.dto.PageRequestDTO;
import org.hj.mallapi.dto.PageResponseDTO;
import org.hj.mallapi.dto.ProductDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ProductSearchImpl extends QuerydslRepositorySupport implements ProductSearch {
    public ProductSearchImpl() {
        super(Product.class);
    }

    @SuppressWarnings("null")
    @Override
    public PageResponseDTO<ProductDTO> searchList(PageRequestDTO reqDTO) {
        // TODO Auto-generated method stub

        log.info("################# ProductSearchImpl ::  searchList ################");

        Pageable pageable = PageRequest.of(reqDTO.getPage()-1,
                                        reqDTO.getSize(),
                                        Sort.by("pno").descending());
                                            
        QProduct prd = QProduct.product;
        QProductImage prdImg = QProductImage.productImage;

        JPQLQuery<Product> query = from(prd);        
        query.leftJoin(prd.imageList, prdImg); //중요 elements Collection 에서 사용할 때 이렇게 사용해야 함.
        query.where(prdImg.ord.eq(0));

        getQuerydsl().applyPagination(pageable, query);

        List<Tuple> prdList = query.select(prd, prdImg).fetch();
        long count = query.fetchCount();

        log.info("############### result == " + prdList);
        return null;
       
    }

}

