package com.example.multipartdemo.service;

import com.example.multipartdemo.model.FileUploader;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;


public interface FileUploadService {

    public void uploadToLocal(MultipartFile multipartFile);

    public FileUploader uploadToDb(MultipartFile multipartFile);

    public Optional<FileUploader> downloadFile(String fileId);

}
