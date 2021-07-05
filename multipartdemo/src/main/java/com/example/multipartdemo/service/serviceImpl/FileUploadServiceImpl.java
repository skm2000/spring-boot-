package com.example.multipartdemo.service.serviceImpl;

import com.example.multipartdemo.model.FileUploader;
import com.example.multipartdemo.repository.FileUploadRepository;
import com.example.multipartdemo.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    private String uploadFilePath = "/users/shubham/Desktop/Downloads/uploaded_";

    @Autowired
    private FileUploadRepository fileUploadRepository;

    @Override
    public void uploadToLocal(MultipartFile files) {
        try {
            byte[] data = files.getBytes();
            Path path = Paths.get(uploadFilePath + files.getOriginalFilename());
            Files.write(path,data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public FileUploader uploadToDb(MultipartFile files) {
        FileUploader fileUploader = new FileUploader();
        try {
            fileUploader.setFileData(files.getBytes());
            fileUploader.setFileName(files.getOriginalFilename());
            fileUploader.setFileType(files.getContentType());
            FileUploader uploadedFile =  fileUploadRepository.save(fileUploader);
            return uploadedFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Optional<FileUploader> downloadFile(String fileId) {
        Optional<FileUploader> fileUploader = fileUploadRepository.findById(fileId);
        return fileUploader;
    }
}
