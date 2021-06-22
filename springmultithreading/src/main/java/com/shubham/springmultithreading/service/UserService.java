package com.shubham.springmultithreading.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.shubham.springmultithreading.entity.User;
import com.shubham.springmultithreading.repository.UserRepository;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    Object target;
    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Async
    public CompletableFuture<List<User>> saveUsers(MultipartFile file) throws Exception{
        long start = System.currentTimeMillis();
        List<User>users = parseCSV(file);
        logger.info("saving users of size " + users.size() + "thread name " + Thread.currentThread().getName());
        users = userRepository.saveAll(users);
        long end = System.currentTimeMillis();
        logger.info("Total time " + (end - start) + "s");
        return CompletableFuture.completedFuture(users);
    }

    @Async
    public CompletableFuture<List<User>> findAllUsers() {
        logger.info("Finding all users " + Thread.currentThread().getName());
        List<User>users = userRepository.findAll();
        return CompletableFuture.completedFuture(users);
    }

    private List<User> parseCSV(final MultipartFile file) throws Exception{
        final List<User>users = new ArrayList<User>();
        try {
            try(final BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null){
                    final String data[] = line.split(",");
                    final User user = new User();
                    user.setName(data[0]);
                    user.setEmail(data[1]);
                    user.setGender(data[2]);
                    users.add(user);
                }
                return users;
            }
        } catch (Exception e) {
            logger.error("Failed to parse CSV file {}", e);
            throw new Exception("Failed to parse CSV file {}", e);
        }
    }

}
