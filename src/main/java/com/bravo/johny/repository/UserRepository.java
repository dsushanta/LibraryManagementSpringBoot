package com.bravo.johny.repository;

import com.bravo.johny.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByUserName(String UserName);

    UserEntity findFirstByUserName(String UserName);

    @Nullable
    List<UserEntity> findByLastName(String lastName, Pageable pageable);

    @Nullable
    List<UserEntity> findByLastName(String lastName);

    Optional<UserEntity> findByEmail(String email);

    @Transactional
    void deleteByUserName(String userName);
}