package edu.utec.pinfraPft.service;

import edu.utec.pinfraPft.dto.EmailDto;
import jakarta.mail.MessagingException;

public interface EmailService {
    void sendEmail(EmailDto email) throws MessagingException;
}
