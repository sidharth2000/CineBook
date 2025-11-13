package com.cinebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cinebook.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	Role findByRoleName(String roleName);
}
