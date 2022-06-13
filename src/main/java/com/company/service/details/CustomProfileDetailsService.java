package com.company.service.details;

import com.company.config.details.CustomProfileDetails;
import com.company.entity.ProfileEntity;
import com.company.exception.AppForbiddenException;
import com.company.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomProfileDetailsService implements UserDetailsService {

    private final ProfileRepository profileRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ProfileEntity entity = profileRepository
                .findByEmailAndDeletedDateIsNull(email)
                .orElseThrow(() -> {
                    log.warn("Profile Not Found email={}", email);
                    return new UsernameNotFoundException("Profile Not Found!");
                });

        switch (entity.getStatus()) {
            case BLOCK: {
                log.warn("Profile Blocked email={}", email);
                throw new BadCredentialsException("Profile Blocked!, please contact us -> https://t.me/Javlondev");
            }
            case INACTIVE: {
                log.warn("No Access email={}", email);
                throw new AppForbiddenException("No Access!");
            }
        }

        return new CustomProfileDetails(entity);
    }
}
