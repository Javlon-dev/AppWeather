package com.company.repository;

import com.company.entity.ProfileEntity;
import com.company.enums.profile.ProfileRole;
import com.company.enums.profile.ProfileStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity, String> {

    Optional<ProfileEntity> findByEmailAndDeletedDateIsNull(String email);

    Optional<ProfileEntity> findByIdAndDeletedDateIsNull(String profileId);

    Page<ProfileEntity> findAllByDeletedDateIsNull(Pageable pageable);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set deletedDate = :date where email = :email and deletedDate is null ")
    int updateDeletedDate(@Param("date") LocalDateTime date, @Param("email") String email);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set status = :status where email = :email and id = :id and deletedDate is null ")
    int updateStatus(@Param("status") ProfileStatus status, @Param("email") String email, @Param("id") String id);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set status = :status where id = :id and deletedDate is null ")
    int updateStatusAdmin(@Param("status") ProfileStatus status, @Param("id") String id);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set role = :role where id = :id and deletedDate is null ")
    int updateRole(@Param("role") ProfileRole role, @Param("id") String id);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set email = :email where id = :id and deletedDate is null ")
    int updateEmail(@Param("email") String email, @Param("id") String id);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set password = :password where email = :email and id = :id and deletedDate is null ")
    int updatePassword(@Param("password") String password, @Param("email") String email, @Param("id") String id);

}