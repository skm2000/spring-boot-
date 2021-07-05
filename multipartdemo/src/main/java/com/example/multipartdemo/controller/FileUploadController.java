package com.example.multipartdemo.controller;


import com.example.multipartdemo.model.FileUploader;
import com.example.multipartdemo.response.FileResponse;
import com.example.multipartdemo.service.FileUploadService;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class FileUploadController {

    private final FileUploadService fileUploadService;

    public FileUploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @PostMapping("/upload/local")
    public void uploadToLocal(@RequestParam("file")MultipartFile multipartFile){
        fileUploadService.uploadToLocal(multipartFile);
    }

    @PostMapping("/upload/db")
    public FileResponse  uploadToDb(@RequestParam("file")MultipartFile multipartFile){
        FileUploader fileUploader =  fileUploadService.uploadToDb(multipartFile);
        FileResponse fileResponse = new FileResponse();
        if(fileUploader != null){
            String downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/v1/download/")
                    .path(fileUploader.getFileId())
                    .toUriString();
            fileResponse.setDownloadUri(downloadUri);
            fileResponse.setFileId(fileUploader.getFileId());
            fileResponse.setFileType(fileUploader.getFileType());
            fileResponse.setUploadStatus(true);
            fileResponse.setMessage("File Uploaded Successfully");
            return fileResponse;
        }
        fileResponse.setMessage("OOPs!! Something went Wrong");
        return fileResponse;
    }

    @RequestMapping("/download/{fileId}")
    public Optional<FileUploader> downloadFiles(@PathVariable String fileId){
       return fileUploadService.downloadFile(fileId);
    }

}
