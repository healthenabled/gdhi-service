package it.gdhi.service;

import it.gdhi.model.Country;
import it.gdhi.utils.Constants.Mail;
import it.gdhi.utils.MailAddresses;
import it.gdhi.utils.Mailer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class MailerServiceTest {

    @InjectMocks
    MailerService mailerService;

    @Mock
    MailAddresses mailAddresses;

    @Mock
    Mailer mailer;

    @Test
    public void shouldVerifyEmailIsSent() {
        ReflectionTestUtils.setField(mailerService, "frontEndURL", "http://test");
        String email1 = "test1@test.com";
        String email2 = "test2@test.com";
        String name1 = "test1";
        String name2 = "test2";
        Country country = new Country("Ind", "India");
        Map<String, String> address =  new HashMap<String, String>();
        address.put(email1, name1);
        address.put(email2, name2);
        when(mailAddresses.getAddressMap()).thenReturn(address);

        mailerService.send(country);

        verify(mailer).send(email2, format(Mail.SUBJECT, country.getName()), constructBody(name2, country, "http://test"));
        verify(mailer).send(email1, format(Mail.SUBJECT, country.getName()), constructBody(name1, country, "http://test"));
    }
    private String constructBody(String name, Country country, String path) {
        return format(Mail.BODY, name, country.getName(), format(Mail.HEALTH_INDICATOR_PATH, path, country.getId()));
    }

}