package com.company.service;

import com.company.dto.EmailDTO;
import com.company.entity.EmailEntity;
import com.company.enums.EmailType;
import com.company.exception.AppBadRequestException;
import com.company.exception.ItemNotFoundException;
import com.company.repository.EmailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    private final EmailRepository emailRepository;


    public void send(String toEmail, String title, String content, EmailType type) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            message.setSubject(title);

            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(toEmail);
            helper.setText(content, true);

            log.info("Mail preparation for sending to email={}", toEmail);

            javaMailSender.send(message);

            log.info("Preparation for saving email={}", toEmail);

            saveEmail(toEmail, type);

        } catch (MessagingException | MailException e) {
            log.error("Mail not send email={}", toEmail);
            throw new AppBadRequestException(e.getMessage());
        }
    }

    public void saveEmail(String toEmail, EmailType type) {
        EmailEntity entity = new EmailEntity();
        entity.setToEmail(toEmail);
        switch (type) {
            case VERIFICATION: {
                entity.setType(EmailType.VERIFICATION);
                break;
            }
            case RESET: {
                entity.setType(EmailType.RESET);
                break;
            }
        }
        emailRepository.save(entity);
    }

    public PageImpl<EmailDTO> paginationList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));

        Page<EmailEntity> entityPage = emailRepository.findAll(pageable);

        List<EmailDTO> dtoList = new ArrayList<>();

        entityPage.forEach(entity -> {
            dtoList.add(toDTO(entity));
        });

        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }

    public Boolean delete(String id) {
        EmailEntity entity = emailRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Email Not found {}", id);
                    return new ItemNotFoundException("Email Not found!");
                });

        emailRepository.delete(entity);
        return true;
    }

    private EmailDTO toDTO(EmailEntity entity) {
        EmailDTO dto = new EmailDTO();
        dto.setId(entity.getId());
        dto.setToEmail(entity.getToEmail());
        dto.setType(entity.getType());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }
}
