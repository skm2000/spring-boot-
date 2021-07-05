package com.example.multipartdemo.repository;

import com.example.multipartdemo.model.FileUploader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileUploadRepository extends JpaRepository<FileUploader, String> {
}
