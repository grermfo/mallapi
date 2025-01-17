package org.hj.mallapi.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.IOException;
import org.springframework.http.HttpHeaders;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

@Component
@Log4j2
@RequiredArgsConstructor

public class CustomFileUtil {

    @Value("${org.hj.upload.path}")
    private String uploadPath;

    @PostConstruct
    public void init() {
        File tempFolder = new File(uploadPath);

        if(tempFolder.exists() == false) {
            tempFolder.mkdir();
        }
        uploadPath = tempFolder.getAbsolutePath();
        log.info("############## uploadPath ==" + uploadPath);

    }

    public List<String> saveFiles(List<MultipartFile> files) throws RuntimeException {

        if(files == null || files.size() == 0) {
            return List.of();
        }
        List<String> uploadNames = new ArrayList<>();
        for(MultipartFile mFile : files) {
            String saveName = UUID.randomUUID().toString() + "_" + mFile.getOriginalFilename();
            Path savePath = Paths.get(uploadPath, saveName);
            try {
                Files.copy(mFile.getInputStream(), savePath);
                String contentType = mFile.getContentType();
                if(null != contentType && contentType.startsWith("image") ) {
                    Path thumbnailPath = Paths.get(uploadPath, "s_" +saveName);

                    Thumbnails.of(savePath.toFile())
                                .size(200,200)
                                .toFile(thumbnailPath.toFile());
                }
                uploadNames.add(saveName);        
            }catch(IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        return uploadNames;
    }

    public ResponseEntity<Resource> getFile(String fileName) {

        Resource resource = new FileSystemResource(uploadPath + File.separator + fileName);
        if(!resource.isReadable()){
            resource = new FileSystemResource(uploadPath + File.separator + "defalut.jpg");
        }
        org.springframework.http.HttpHeaders headers = new HttpHeaders();
        try{
                headers.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));
        }catch(Exception e) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok().headers(headers).body(resource);
    }

    public void deleteFiles(List<String> fileNames) {
        if(null == fileNames || fileNames.size() ==0) {
            return;
        }

        fileNames.forEach(fileName -> {
            String thumbnailFileName = "s_" + fileName;
            Path thumbPath = Paths.get(uploadPath , thumbnailFileName);
            Path filePath = Paths.get(uploadPath, fileName);

            try{
                Files.deleteIfExists(filePath);
                Files.deleteIfExists(thumbPath);
                
            }catch(IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        });
    }

}
