package it.gdhi.service;

import it.gdhi.model.Country;
import it.gdhi.utils.MailAddresses;
import it.gdhi.utils.Mailer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

import static java.lang.String.format;

@Service
public class MailerService {

    @Autowired
    private Mailer mailer;

    @Autowired
    MailAddresses mailAddresses;

    @Value("${frontEndURL}")
    private String frontEndURL;

    private static final String SUBJECT = "Test Mail for ";

    private String body = "Hi %s,\n" +
            "Test Body for %s with %s\n";

    public void send(Country country) {
        for (Map.Entry<String, String> stringStringEntry : mailAddresses.getAddressMap().entrySet()) {
            String messageBody = format(body,
                    stringStringEntry.getKey(),
                    country.getName(), frontEndURL + "/edit/" + country.getId());
            mailer.send(stringStringEntry.getValue(), SUBJECT + country.getName(), messageBody);
        }
    }
}