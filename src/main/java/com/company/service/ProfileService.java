package com.company.service;

import com.company.config.details.EntityDetails;
import com.company.dto.JwtDTO;
import com.company.dto.ProfileDTO;
import com.company.dto.request.*;
import com.company.dto.response.ProfileResponseDTO;
import com.company.entity.ProfileEntity;
import com.company.enums.profile.ProfileRole;
import com.company.enums.profile.ProfileStatus;
import com.company.mapper.ProfileInfoMapper;
import com.company.repository.ProfileRepository;
import com.company.repository.custom.ProfileCustomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

    private final ProfileCustomRepository customRepository;

    private final EmailService emailService;

    private final PasswordEncoder passwordEncoder;

    /**
     * CLIENT
     */


    public ProfileResponseDTO updateProfile(ProfileBioDTO dto) {
        ProfileEntity entity = EntityDetails.getProfile();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setUpdatedDate(LocalDateTime.now());

        profileRepository.save(entity);

        return toClientDTO(entity);
    }

    public String updateEmail(ProfileEmailDTO dto) {
        ProfileEntity entity = EntityDetails.getProfile();
        entity.setEmail(dto.getEmail());

        return emailService.show("api/v1/profile/", entity);
    }

    public Boolean putNewEmail(JwtDTO dto) {
        return profileRepository.updateEmail(dto.getEmail(), dto.getProfileId()) > 0;
    }

    public Boolean updatePassword(ProfilePasswordDTO dto) {
        ProfileEntity entity = EntityDetails.getProfile();

        if (!passwordEncoder.matches(dto.getOldPassword(), entity.getPassword())) {
            log.warn("Invalid Password {}", dto);
            throw new BadCredentialsException("Invalid Password!");
        }

        return profileRepository.updatePassword(passwordEncoder.encode(dto.getPassword()), entity.getEmail(), entity.getId()) > 0;
    }

    public Boolean deleteProfile() {
        ProfileEntity entity = EntityDetails.getProfile();
        return profileRepository.updateDeletedDate(LocalDateTime.now(), entity.getEmail()) > 0;
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

    public ProfileEntity getByEmail(String email) {
        return profileRepository
                .findByEmailAndDeletedDateIsNull(email)
                .orElseThrow(() -> {
                    log.warn("Profile Not Found email={}", email);
                    return new UsernameNotFoundException("Profile Not Found!");
                });
    }

    public ProfileEntity getById(String profileId) {
        return profileRepository
                .findByIdAndDeletedDateIsNull(profileId)
                .orElseThrow(() -> {
                    log.warn("Profile Not Found profileId={}", profileId);
                    return new UsernameNotFoundException("Profile Not Found!");
                });
    }

    /**
     * ADMIN
     */

    public Boolean updateStatus(String profileId) {
        ProfileEntity entity = getById(profileId);

        switch (entity.getStatus()) {
            case ACTIVE: {
                profileRepository.updateStatusAdmin(ProfileStatus.BLOCK, profileId);
                break;
            }
            case BLOCK: {
                profileRepository.updateStatusAdmin(ProfileStatus.ACTIVE, profileId);
                break;
            }
        }

        return true;
    }

    public Boolean updateRole(String profileId) {
        ProfileEntity entity = getById(profileId);

        switch (entity.getRole()) {
            case ROLE_ADMIN: {
                profileRepository.updateRole(ProfileRole.ROLE_ADMIN, profileId);
                break;
            }
            case ROLE_CLIENT: {
                profileRepository.updateRole(ProfileRole.ROLE_CLIENT, profileId);
                break;
            }
        }

        return true;
    }

    public Boolean deleteProfile(String profileId) {
        ProfileEntity entity = getById(profileId);
        return profileRepository.updateDeletedDate(LocalDateTime.now(), entity.getEmail()) > 0;
    }

    public List<ProfileDTO> filterList(ProfileFilterDTO dto) {
        return customRepository
                .filter(dto)
                .stream()
                .map(this::toAdminDTO)
                .collect(Collectors.toList());
    }

    public PageImpl<ProfileDTO> profilePagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));

        Page<ProfileEntity> entityPage = profileRepository.findAllByDeletedDateIsNull(pageable);

        List<ProfileDTO> dtoList = new ArrayList<>();

        entityPage.forEach(entity -> {
            dtoList.add(toAdminDTO(entity));
        });

        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }

    public ProfileDTO toAdminDTO(ProfileInfoMapper mapper) {
        ProfileDTO dto = new ProfileDTO();
        dto.setId(mapper.getId());
        dto.setName(mapper.getName());
        dto.setSurname(mapper.getSurname());
        dto.setEmail(mapper.getEmail());
        dto.setRole(mapper.getRole());
        dto.setStatus(mapper.getStatus());
        dto.setCreatedDate(mapper.getCreatedDate());
        dto.setUpdatedDate(mapper.getUpdatedDate());
        dto.setDeletedDate(mapper.getDeletedDate());
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
