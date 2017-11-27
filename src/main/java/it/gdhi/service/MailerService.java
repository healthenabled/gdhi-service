package it.gdhi.service;

import it.gdhi.model.Country;
import it.gdhi.utils.MailAddresses;
import it.gdhi.utils.Mailer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static it.gdhi.utils.Constants.Mail.*;
import static java.lang.String.format;

@Service
public class MailerService {

    @Autowired
    private Mailer mailer;

    @Autowired
    MailAddresses mailAddresses;

    @Value("${frontEndURL}")
    private String frontEndURL;

    public void send(Country country) {
        mailAddresses.getAddressMap().entrySet().forEach((entry) -> {
            String email = entry.getKey();
            String name = entry.getValue();
            String message = constructBody(country, name);
            mailer.send(email, constructSubject(country), message);
        });
    }

    private String constructBody(Country country, String name) {
        return format(BODY, name, country.getName(), constructHealthIndicatorPath(country));
    }

    private String constructSubject(Country country) {
        return format(SUBJECT, country.getName());
    }

    private String constructHealthIndicatorPath(Country country) {
        return format(HEALTH_INDICATOR_PATH, frontEndURL, country.getId());
    }
}