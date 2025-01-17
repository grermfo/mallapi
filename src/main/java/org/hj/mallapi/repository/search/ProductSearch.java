package org.hj.mallapi.repository.search;

import org.hj.mallapi.dto.PageRequestDTO;
import org.hj.mallapi.dto.PageResponseDTO;
import org.hj.mallapi.dto.ProductDTO;

public interface ProductSearch  {

    PageResponseDTO<ProductDTO> searchList (PageRequestDTO reqDTO);

}
