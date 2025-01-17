package org.hj.mallapi.controller;

import java.util.List;
import java.util.Map;
import org.springframework.core.io.Resource;
import org.hj.mallapi.dto.PageRequestDTO;
import org.hj.mallapi.dto.PageResponseDTO;
import org.hj.mallapi.dto.ProductDTO;
import org.hj.mallapi.service.ProductService;
import org.hj.mallapi.util.CustomFileUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/products")
public class ProductController {
    private final CustomFileUtil fileUtil;
    private final ProductService pService;

    @PostMapping("/")
    public Map<String, String> register(ProductDTO pDto) {
        //TODO: process POST request
        
        log.info("############## ProductController :: register  ==" + pDto);
        List<MultipartFile> files = pDto.getFiles();
        List<String> uploadFileNames = fileUtil.saveFiles(files);

        pDto.setUploadFileNames(uploadFileNames);
        log.info("################# uploadFileName");
        log.info("*** " + uploadFileNames);
        return Map.of("RESULT", "SUCCESS");
    }
    
    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewFileGet(@PathVariable String fileName) {
        return fileUtil.getFile(fileName);
    }

    @GetMapping("/list")
    public PageResponseDTO<ProductDTO> getList(PageRequestDTO reqDTO) {

        log.info("########### ProductController :: getList ############" + reqDTO);
        return pService.getList(reqDTO);
        
    }
    
    

}
