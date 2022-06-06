package com.company.service.details;

import com.company.config.details.CustomProfileDetails;
import com.company.entity.ProfileEntity;
import com.company.enums.profile.ProfileStatus;
import com.company.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomProfileDetailsService implements UserDetailsService {

    private final ProfileRepository profileRepository;

    @Override
    public CustomProfileDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ProfileEntity entity = profileRepository
                .findByEmailAndDeletedDateIsNull(email)
                .orElseThrow(() -> {
                    log.warn("Profile Not Found email={}", email);
                    return new UsernameNotFoundException("Profile Not Found!");
                });

        if (entity.getStatus().equals(ProfileStatus.BLOCK)) {
            log.warn("Profile Blocked email={}", email);
            throw new UsernameNotFoundException("Profile Blocked!, please contact us -> https://t.me/Javlondev");
        }

        return new CustomProfileDetails(entity);
    }
}
