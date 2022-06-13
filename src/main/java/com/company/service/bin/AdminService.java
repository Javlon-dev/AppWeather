package com.company.service.bin;

import com.company.entity.ProfileEntity;
import com.company.enums.profile.ProfileRole;
import com.company.enums.profile.ProfileStatus;
import com.company.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminService {

    private final ProfileRepository profileRepository;

    private final PasswordEncoder passwordEncoder;

    @Value("${message.admin.email}")
    private String adminEmail;

    @Value("${message.admin.password}")
    private String adminPassword;

    public void createAdmin() {
        if (profileRepository.findByEmailAndDeletedDateIsNull(adminEmail).isPresent()) {
            return;
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setName("A");
        entity.setSurname("B");
        entity.setEmail(adminEmail);
        entity.setPassword(adminPassword);
        entity.setRole(ProfileRole.ROLE_ADMIN);
        entity.setStatus(ProfileStatus.ACTIVE);

        profileRepository.save(entity);
    }
}
