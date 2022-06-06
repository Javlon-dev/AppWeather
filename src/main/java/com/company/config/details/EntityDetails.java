package com.company.config.details;

import com.company.entity.ProfileEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class EntityDetails {

    public static ProfileEntity getProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomProfileDetails details = (CustomProfileDetails) authentication.getPrincipal();
        return details.getProfile();
    }

}
