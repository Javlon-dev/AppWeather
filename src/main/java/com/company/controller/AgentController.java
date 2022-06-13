package com.company.controller;

import com.company.dto.rest.AgentDTO;
import com.company.dto.rest.agent.AgentRegistrationDTO;
import com.company.service.AgentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1/agent")
@RequiredArgsConstructor
@Api(tags = "Agent")
public class AgentController {

    private final AgentService agentService;

    @ApiOperation(value = "Agent Registration", notes = "Method used for registration agent (for ADMIN)")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/registration")
    public ResponseEntity<AgentDTO> agentRegistration(@RequestBody @Valid AgentRegistrationDTO dto) {
        log.info("Agent Registration {}", dto);
        return ResponseEntity.ok(agentService.registration(dto));
    }
}
