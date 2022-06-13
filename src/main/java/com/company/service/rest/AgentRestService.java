package com.company.service.rest;

import com.company.config.details.EntityDetails;
import com.company.dto.rest.AgentDTO;
import com.company.dto.rest.agent.AgentRegistrationDTO;
import com.company.entity.ProfileEntity;
import com.company.exception.AppBadRequestException;
import com.company.exception.ItemNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class AgentRestService {

    private final RestTemplate restTemplate;

    @Value("${message.agent.url}")
    private String url;


    public AgentDTO registration(AgentRegistrationDTO dto) {
        ProfileEntity entity = EntityDetails.getProfile();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth("ADMIN" + entity.getEmail(), entity.getPassword());

        log.info("Header prepare email={}", entity.getEmail());

        HttpEntity<AgentRegistrationDTO> httpEntity = new HttpEntity<>(dto, headers);

        ResponseEntity<AgentDTO> response;
        try {
            response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, AgentDTO.class);
        } catch (HttpClientErrorException e) {
            log.warn("Bad Request! url={}", url);
//            e.printStackTrace();
            throw new ItemNotFoundException("Bad Request! " + e.getMessage());
        }

        return response.getBody();
    }
}
