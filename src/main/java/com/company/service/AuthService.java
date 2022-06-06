package com.company.service;

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

    private final EmailService emailService;

    private final PasswordEncoder passwordEncoder;

    @Value("${server.domain.name}")
    private String domainName;


    public ProfileResponseDTO login(LoginDTO dto) {
        return toDTO(authorization(dto));
    }

    public ProfileEntity authorization(LoginDTO dto) {
        ProfileEntity entity = getByEmail(dto.getEmail());

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

        Thread thread = new Thread(() -> {
            try {
                sendEmail(entity, "api/v1/auth/verification/", EmailType.VERIFICATION);
            } catch (AppBadRequestException e) {
                profileRepository.updateDeletedDate(LocalDateTime.now(), entity.getEmail());
//                throw new AppBadRequestException("Mail not send!");
                e.printStackTrace();
            }
        });
        thread.start();

        return "Confirm your email address.\nYou'll receive by this email -> unidevs.info@gmail.com\nCheck your email!";
    }

    public String verification(String email) {
        if (profileRepository.updateStatus(ProfileStatus.ACTIVE, email) > 0) {
            log.info("Successfully verified email={}", email);
            return "Successfully verified";
        }
        log.warn("Unsuccessfully verified email={}", email);
        throw new AppNotAcceptableException("Unsuccessfully verified!");
    }

    public void sendEmail(ProfileEntity entity, String domainPath, EmailType type) {
        StringBuilder builder = new StringBuilder();
        builder.append("<h2>Hellomaleykum ").append(entity.getName()).append(" ").append(entity.getSurname()).append("!</h2>");
        builder.append("<br><p><b>To verify your registration click to next link -> ");
        builder.append("<a href=\"" + domainName + domainPath);
        builder.append(JwtUtil.encodeEmail(entity.getEmail()));
        builder.append("\">This Link</a></b></p></br>");
        emailService.send(entity.getEmail(), "Active Your Email", builder.toString(), type);
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

    public ProfileEntity getByEmail(String email) {
        return profileRepository
                .findByEmailAndDeletedDateIsNull(email)
                .orElseThrow(() -> {
                    log.warn("Profile Not Found email={}", email);
                    return new UsernameNotFoundException("Profile Not Found!");
                });
    }
}
