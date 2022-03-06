package com.authorization.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.authorization.model.User;

public interface UserRepository extends JpaRepository<User, String> {
}
