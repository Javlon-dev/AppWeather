package com.company.controller;

import com.company.dto.ProfileDTO;
import com.company.dto.request.ProfileBioDTO;
import com.company.dto.request.ProfileEmailDTO;
import com.company.dto.request.ProfileFilterDTO;
import com.company.dto.request.ProfilePasswordDTO;
import com.company.dto.response.ProfileResponseDTO;
import com.company.service.ProfileService;
import com.company.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
@Api(tags = "Profile")
public class ProfileController {

    private final ProfileService profileService;

    /**
     * CLIENT
     */

    @ApiOperation(value = "Profile Info", notes = "Method used to show profile information",
            authorizations = @Authorization(value = "JWT token"))
    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("")
    public ResponseEntity<ProfileResponseDTO> getProfileInfo() {
        log.info("Profile Info");
        return ResponseEntity.ok(profileService.getProfileInfo());
    }

    @ApiOperation(value = "Update Profile Bio", notes = "Method used for update profile bio",
            authorizations = @Authorization(value = "JWT token"))
    @PreAuthorize("hasRole('CLIENT')")
    @PutMapping("/bio")
    public ResponseEntity<ProfileResponseDTO> updateProfile(@RequestBody ProfileBioDTO dto) {
        log.info("Update Profile Bio {}", dto);
        return ResponseEntity.ok(profileService.updateProfile(dto));
    }

    @ApiOperation(value = "Update Profile Email", notes = "Method used for update profile email",
            authorizations = @Authorization(value = "JWT token"))
    @PreAuthorize("hasRole('CLIENT')")
    @PutMapping("/email")
    public ResponseEntity<String> updateEmail(@RequestBody ProfileEmailDTO dto) {
        log.info("Update Profile Email {}", dto);
        return ResponseEntity.ok(profileService.updateEmail(dto));
    }

    @ApiOperation(value = "Delete Profile", notes = "Method used for delete profile",
            authorizations = @Authorization(value = "JWT token"))
    @PreAuthorize("hasRole('CLIENT')")
    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> deleteProfile() {
        log.info("Delete Profile");
        return ResponseEntity.ok(profileService.deleteProfile());
    }

    /**
     * ANY
     */

    @ApiOperation(value = "Update Profile Password", notes = "Method used for update profile password",
            authorizations = @Authorization(value = "JWT token"))
    @PreAuthorize("hasAnyRole('CLIENT','ADMIN')")
    @PutMapping("/password")
    public ResponseEntity<Boolean> updatePassword(@RequestBody ProfilePasswordDTO dto) {
        log.info("Update Profile Password {}", dto);
        return ResponseEntity.ok(profileService.updatePassword(dto));
    }

    @ApiOperation(value = "Put Profile Email", notes = "Method used for update profile email",
            authorizations = @Authorization(value = "JWT token"))
    @PreAuthorize("hasAnyRole('CLIENT','ADMIN')")
    @GetMapping("/{token}")
    public ResponseEntity<Boolean> putNewEmail(@PathVariable("token") String token) {
        log.info("Put Profile Email token={}", token);
        return ResponseEntity.ok(profileService.putNewEmail(JwtUtil.decode(token)));
    }

    /**
     * ADMIN
     */

    @ApiOperation(value = "Update Profile Role", notes = "Method used for update profile role (for ADMIN)",
            authorizations = @Authorization(value = "JWT token"))
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/role/{profileId}")
    public ResponseEntity<Boolean> updateRole(@PathVariable("profileId") String profileId) {
        log.info("Update Profile Role profileId={}", profileId);
        return ResponseEntity.ok(profileService.updateRole(profileId));
    }

    @ApiOperation(value = "Update Profile Status", notes = "Method used for update profile status (for ADMIN)",
            authorizations = @Authorization(value = "JWT token"))
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/status/{profileId}")
    public ResponseEntity<Boolean> updateStatus(@PathVariable("profileId") String profileId) {
        log.info("Update Profile Status profileId={}", profileId);
        return ResponseEntity.ok(profileService.updateStatus(profileId));
    }

    @ApiOperation(value = "Delete Profile", notes = "Method used for delete profile (for ADMIN)",
            authorizations = @Authorization(value = "JWT token"))
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{profileId}")
    public ResponseEntity<Boolean> deleteProfile(@PathVariable("profileId") String profileId) {
        log.info("Delete Profile profileId={}", profileId);
        return ResponseEntity.ok(profileService.deleteProfile(profileId));
    }

    @ApiOperation(value = "Profile List", notes = "Method used for get profile list by filter (for ADMIN)",
            authorizations = @Authorization(value = "JWT token"))
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<List<ProfileDTO>> filterList(@RequestBody ProfileFilterDTO dto) {
        log.info("Profile List filter={}", dto);
        return ResponseEntity.ok(profileService.filterList(dto));
    }

    @ApiOperation(value = "Profile Pagination", notes = "Method used for get profile pagination (for ADMIN)",
            authorizations = @Authorization(value = "JWT token"))
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/page")
    public ResponseEntity<PageImpl<ProfileDTO>> profilePagination(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                  @RequestParam(value = "size", defaultValue = "10") int size) {
        log.info("Profile Pagination page={} size={}", page, size);
        return ResponseEntity.ok(profileService.profilePagination(page, size));
    }

}
