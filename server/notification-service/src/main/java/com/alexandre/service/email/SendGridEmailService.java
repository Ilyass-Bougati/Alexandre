package com.alexandre.service.email;

import com.alexandre.config.EmailTemplateLoader;
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
    private final EmailTemplateLoader emailTemplateLoader;
    private final Email fromEmail;

    public SendGridEmailService(SendGridProperties sendGridProperties, EmailTemplateLoader emailTemplateLoader) {
        this.sendGridProperties = sendGridProperties;
        this.fromEmail = new Email(sendGridProperties.fromEmail());
        this.emailTemplateLoader = emailTemplateLoader;
    }

    @Override
    public void send(String to, String subject, String templatePath) {
        String body;
        try {
            body = emailTemplateLoader.loadTemplate(templatePath);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return;
        }

        Email toEmail = new Email(to);
        Mail mail = new Mail(fromEmail, subject, toEmail, new Content("text/html", body));

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
