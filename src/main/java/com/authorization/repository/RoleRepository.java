package com.authorization.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.authorization.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
