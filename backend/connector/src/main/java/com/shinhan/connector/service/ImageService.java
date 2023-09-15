package com.shinhan.connector.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class ImageService {
    private final String IMAGE_PATH = "/image";

    public String uploadImage(MultipartFile image) throws IOException {
        log.info("[이미징 업로드] 이미지 업로드 요청. image : {}", image);

        String uploadPath = IMAGE_PATH;

        File uploadDir = new File(uploadPath);

        // 폴더 없으면 폴더 생성
        if(!uploadDir.exists())
            uploadDir.mkdirs();

        // 파일 이름을 유니크한 값으로 생성(UUID)
        String newFileName = UUID.randomUUID() + "_" + image.getOriginalFilename();

        image.transferTo(new File(uploadPath, newFileName));

        log.info("[이미지 업로드] 이미지 업로드 => imageName : {}", newFileName);
        log.info("[이미징 업로드] 이미지 업로드 완료");

        return newFileName;
    }

    public Map<String, Object> getImage(String imageName){
        log.info("[이미지 가져오기] 이미지 가져오기 요청");

        String uploadPath = IMAGE_PATH;
        String imagePath = uploadPath + File.separator + imageName;
        Resource resource = new FileSystemResource(imagePath);

        if(!resource.exists()){
            log.error("[이미지 가져오기] 이미지 찾기 실패");
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "이미지를 찾지 못했습니다.");
        }

        log.info("[이미지 가져오기] 이미지 찾기 성공");

        HttpHeaders header = new HttpHeaders();
        Path filePath = null;
        try {
            filePath = Paths.get(imagePath);
            header.add("Content-Type", Files.probeContentType(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("header", header);
        returnMap.put("resource", resource);

        log.info("[이미지 가져오기] 이미지 가져오기 완료");
        return returnMap;
    }

}