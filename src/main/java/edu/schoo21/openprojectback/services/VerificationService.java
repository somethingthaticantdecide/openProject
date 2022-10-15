package edu.schoo21.openprojectback.services;

import edu.schoo21.openprojectback.models.ConfirmationToken;
import edu.schoo21.openprojectback.models.User;
import edu.schoo21.openprojectback.repository.ConfirmationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationService {
    @Value("${site.address}")
    public String APP_ADDRESS;
    private final EmailSenderService emailSenderService;
    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Async
    public void sendVerificationEmail(User user) {
        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        confirmationTokenRepository.save(confirmationToken);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getMail());
        mailMessage.setSubject("Restore password!");
        mailMessage.setText("To restore your account, please click here:" + APP_ADDRESS
                + "/confirm/" + confirmationToken.getConfirmationToken());
        emailSenderService.sendEmail(mailMessage);
    }
}
