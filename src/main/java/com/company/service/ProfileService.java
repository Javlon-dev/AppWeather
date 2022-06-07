package com.company.service;

import com.company.config.details.EntityDetails;
import com.company.dto.ProfileDTO;
import com.company.dto.request.ProfileBioDTO;
import com.company.dto.request.ProfileEmailDTO;
import com.company.dto.response.ProfileResponseDTO;
import com.company.entity.ProfileEntity;
import com.company.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileResponseDTO updateProfile(ProfileBioDTO dto) {
        ProfileEntity entity = EntityDetails.getProfile();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setUpdatedDate(LocalDateTime.now());

        profileRepository.save(entity);

        return toClientDTO(entity);
    }

    public ProfileResponseDTO updateEmail(ProfileEmailDTO dto) {
        ProfileEntity entity = EntityDetails.getProfile();


        profileRepository.save(entity);

        return toClientDTO(entity);
    }

    public ProfileResponseDTO updatePassword(ProfileBioDTO dto) {
        ProfileEntity entity = EntityDetails.getProfile();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setUpdatedDate(LocalDateTime.now());

        profileRepository.save(entity);

        return toClientDTO(entity);
    }

    public ProfileResponseDTO getProfileInfo() {
        return toClientDTO(EntityDetails.getProfile());
    }

    public ProfileResponseDTO toClientDTO(ProfileEntity entity) {
        ProfileResponseDTO dto = new ProfileResponseDTO();
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setEmail(entity.getEmail());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUpdatedDate(entity.getUpdatedDate());
        return dto;
    }

    public ProfileDTO toAdminDTO(ProfileEntity entity) {
        ProfileDTO dto = new ProfileDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setEmail(entity.getEmail());
        dto.setRole(entity.getRole());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUpdatedDate(entity.getUpdatedDate());
        dto.setDeletedDate(entity.getDeletedDate());
        return dto;
    }

}
