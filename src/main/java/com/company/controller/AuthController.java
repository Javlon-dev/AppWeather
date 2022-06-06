package com.company.controller;

import com.company.dto.request.LoginDTO;
import com.company.dto.request.RegistrationDTO;
import com.company.dto.response.ProfileResponseDTO;
import com.company.service.AuthService;
import com.company.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Api(tags = "Authorization")
public class AuthController {

    private final AuthService authService;


    @ApiOperation(value = "Login", notes = "Method used for login and getting token")
    @PostMapping("/login")
    public ResponseEntity<ProfileResponseDTO> authProfile(@RequestBody @Valid LoginDTO dto) {
        log.info("Authorization {}", dto);
        return ResponseEntity.ok(authService.login(dto));
    }

    @ApiOperation(value = "Registration", notes = "Method used for registration")
    @PostMapping("/registration")
    public ResponseEntity<String> registration(@RequestBody @Valid RegistrationDTO dto) {
        log.info("Registration {}", dto);
        return ResponseEntity.ok(authService.registration(dto));
    }

    @ApiOperation(value = "Email Verification", notes = "Method used for email verifier")
    @GetMapping("/verification/{token}")
    public ResponseEntity<String> verification(@PathVariable("token") String token) {
        log.info("Verification {}", token);
        return ResponseEntity.ok(authService.verification(JwtUtil.decode(token).getEmail()));
    }

}
