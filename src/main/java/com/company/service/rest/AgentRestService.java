package com.company.service.rest;

import com.company.dto.rest.AgentDTO;
import com.company.dto.rest.agent.AgentLoginDTO;
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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AgentRestService {

    private final RestTemplate restTemplate;

    @Value("${agent.url.registration}")
    private String registrationUrl;

    @Value("${agent.url.login}")
    private String loginUrl;


    public AgentDTO registration(AgentRegistrationDTO dto, ProfileEntity entity, String jwt) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(jwt);

        log.info("Registration: Header prepare to send email={}", entity.getEmail());

        HttpEntity<AgentRegistrationDTO> httpEntity = new HttpEntity<>(dto, headers);

        ResponseEntity<AgentDTO> response;
        try {
            response = restTemplate.exchange(registrationUrl, HttpMethod.POST, httpEntity, AgentDTO.class);
        } catch (HttpClientErrorException e) {
            log.warn("Registration: Bad Request! url={}", registrationUrl);
//            e.printStackTrace();
            throw new ItemNotFoundException("Bad Request! " + e.getMessage());
        }

        return response.getBody();
    }

    public String login(ProfileEntity entity) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        log.info("Login: Prepare to send email={}", entity.getEmail());

        HttpEntity<AgentLoginDTO> httpEntity = new HttpEntity<>(
                new AgentLoginDTO("ADMIN" + entity.getEmail(), entity.getPassword()),
                headers);

        ResponseEntity<AgentDTO> response;
        try {
            response = restTemplate.exchange(loginUrl, HttpMethod.POST, httpEntity, AgentDTO.class);
        } catch (RestClientException e) {
            log.warn("Login: Bad Request url={}", loginUrl);
            throw new AppBadRequestException("Bad Request! " + e.getMessage());
        }

        if (!Optional.ofNullable(response.getBody()).isPresent()) {
            log.warn("Login: Token Not Found! url={}", loginUrl);
            throw new ItemNotFoundException("Token Not Found!");
        }

        return response.getBody().getToken();
    }
}
