package com.example.springcourse.repository;

import com.example.springcourse.entity.role.ERole;
import com.example.springcourse.entity.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
//    @Query("select role from Role role join fetch role.personRoles where role.personRoles = :name")
    Optional<Role> findByName(String name);
}
