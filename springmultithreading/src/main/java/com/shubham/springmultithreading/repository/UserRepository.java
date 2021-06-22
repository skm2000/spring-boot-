package com.shubham.springmultithreading.repository;

import com.shubham.springmultithreading.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    
}
