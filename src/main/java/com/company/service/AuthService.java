package com.company.service;

import com.company.dto.JwtDTO;
import com.company.dto.request.LoginDTO;
import com.company.dto.request.RegistrationDTO;
import com.company.dto.response.ProfileResponseDTO;
import com.company.entity.ProfileEntity;
import com.company.enums.EmailType;
import com.company.enums.profile.ProfileRole;
import com.company.enums.profile.ProfileStatus;
import com.company.exception.*;
import com.company.repository.ProfileRepository;
import com.company.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final ProfileRepository profileRepository;

    private final ProfileService profileService;

    private final EmailService emailService;

    private final PasswordEncoder passwordEncoder;

    @Value("${spring.mail.username}")
    private String sendEmail;


    public ProfileResponseDTO login(LoginDTO dto) {
        return toDTO(authorization(dto));
    }

    public ProfileEntity authorization(LoginDTO dto) {
        ProfileEntity entity = profileService.getByEmail(dto.getEmail());

        if (entity.getStatus().equals(ProfileStatus.BLOCK)) {
            log.warn("Profile Blocked email={}", dto.getEmail());
            throw new UsernameNotFoundException("Profile Blocked!, please contact us -> https://t.me/Javlondev");
        }

        if (!entity.getStatus().equals(ProfileStatus.ACTIVE)) {
            log.warn("No Access {}", dto);
            throw new AppForbiddenException("No Access!");
        }

        if (!passwordEncoder.matches(dto.getPassword(), entity.getPassword())) {
            log.warn("Invalid Password {}", dto);
            throw new BadCredentialsException("Invalid Password!");
        }

        return entity;
    }

    public String registration(RegistrationDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByEmailAndDeletedDateIsNull(dto.getEmail());
        ProfileEntity entity;

        if (optional.isPresent()) {
            entity = optional.get();

            if (!entity.getStatus().equals(ProfileStatus.INACTIVE)) {
                log.warn("Unique {}", dto.getEmail());
                throw new ItemAlreadyExistsException("This Email already used!");
            }

        } else {

            entity = new ProfileEntity();
            entity.setName(dto.getName());
            entity.setSurname(dto.getSurname());
            entity.setEmail(dto.getEmail());
            entity.setPassword(passwordEncoder.encode(dto.getPassword()));
            entity.setStatus(ProfileStatus.INACTIVE);
            entity.setRole(ProfileRole.ROLE_CLIENT);

            profileRepository.save(entity);
        }

        /**
         * Email send
         */
/*
        Thread thread = new Thread(() -> {
            try {
                emailService.preparationSend(entity, "api/v1/auth/verification/", EmailType.VERIFICATION);
            } catch (AppBadRequestException e) {
                profileRepository.updateDeletedDate(LocalDateTime.now(), entity.getEmail());
//                throw new AppBadRequestException("Mail not send!");
                e.printStackTrace();
            }
        });
        thread.start();

        return "Confirm your email address.\nYou'll receive by this email -> " + sendEmail + "\nCheck your email!";*/

        return emailService.show("api/v1/auth/verification/", entity);
    }

    public String verification(JwtDTO dto) {
        if (profileRepository.updateStatus(ProfileStatus.ACTIVE, dto.getEmail(), dto.getProfileId()) > 0) {
            log.info("Successfully verified email={}", dto.getEmail());
            return "Successfully verified";
        }
        log.warn("Unsuccessfully verified email={}", dto.getEmail());
        throw new AppNotAcceptableException("Unsuccessfully verified!");
    }

    public ProfileResponseDTO toDTO(ProfileEntity entity) {
        ProfileResponseDTO dto = new ProfileResponseDTO();
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setEmail(entity.getEmail());
        dto.setToken(JwtUtil.encodeProfile(entity.getEmail()));
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUpdatedDate(dto.getUpdatedDate());
        return dto;
    }

}
