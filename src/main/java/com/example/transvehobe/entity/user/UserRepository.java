package com.example.transvehobe.entity.user;

import com.example.transvehobe.common.enums.UserStatus;
import com.example.transvehobe.entity.role.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameOrEmail(String username, String email);

    List<User> findAllByRole(UserRole role);

    List<User> findAllByRoleAndUserStatus(UserRole role, UserStatus userStatus);

    void deleteByUsername(String username);
}
