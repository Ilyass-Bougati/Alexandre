package com.alexandre.service.email;

import com.alexandre.records.SendGridProperties;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Slf4j
@Service
public class SendGridEmailService implements EmailService {

    private final SendGridProperties sendGridProperties;
    private final Email fromEmail;

    public SendGridEmailService(SendGridProperties sendGridProperties) {
        this.sendGridProperties = sendGridProperties;
        this.fromEmail = new Email(sendGridProperties.fromEmail());
    }

    @Override
    public void send(String to, String subject, String body) {
        Email toEmail = new Email(to);
        Mail mail = new Mail(fromEmail, subject, toEmail, new Content("text/plain", body));

        SendGrid sg = new SendGrid(sendGridProperties.apiKey());
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
        } catch (IOException ex) {
            log.error("Error sending email : {}", ex.getMessage());
        }
    }
}
