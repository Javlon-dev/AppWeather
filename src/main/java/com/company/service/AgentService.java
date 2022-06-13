package com.company.service;

import com.company.dto.rest.AgentDTO;
import com.company.dto.rest.agent.AgentRegistrationDTO;
import com.company.service.rest.AgentRestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AgentService {

    private final AgentRestService agentRestService;


    public AgentDTO registration(AgentRegistrationDTO dto) {
        return agentRestService.registration(dto);
    }

}
