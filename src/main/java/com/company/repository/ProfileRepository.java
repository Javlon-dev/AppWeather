package com.company.repository;

import com.company.entity.ProfileEntity;
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

    Page<ProfileEntity> findAllByStatus(ProfileStatus status, Pageable pageable);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set deletedDate = :date where email = :email and deletedDate is null ")
    int updateDeletedDate(@Param("date") LocalDateTime date, @Param("email") String email);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set status = :status where email = :email and deletedDate is null ")
    int updateStatus(@Param("status") ProfileStatus status, @Param("email") String email);

}