package edu.utec.pinfraPft.controller;

import edu.utec.pinfraPft.dto.EmailDto;
import edu.utec.pinfraPft.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping
    public void sendEmail(EmailDto email) throws MessagingException {
        emailService.sendEmail(email);
    }

}
