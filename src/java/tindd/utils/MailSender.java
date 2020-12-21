/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

/**
 *
 * @author Tin
 */
public class MailSender {

    private static final Logger LOGGER = Logger.getLogger(MailSender.class);

    private static final String HOST_NAME = "smtp.gmail.com";
    private static final int TSL_PORT = 587;
    private static final String APP_EMAIL = "theonlysocialnetwork@gmail.com";
    private static final String APP_PASSWORD = "xtimvujkkjuudawy";

    private static Authenticator getAuth() {
        //Get session
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(APP_EMAIL, APP_PASSWORD);
            }
        };

        return auth;
    }

    public static boolean sendActivationLink(String emailSendTo, String activationLink) {
        boolean result = false;

        //Set properties object
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", HOST_NAME);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", TSL_PORT);

        //Get session for sending email
        Session session = Session.getDefaultInstance(props, getAuth());

        //Compose email
        try {
            MimeMessage message = new MimeMessage(session);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailSendTo));
            message.setSubject("Confirm Order - HOTEL BOOKING SYSTEM");
            String activationHTML = "<p>Thank you for using our services!</p>"
                    + "<p>Below is your activation link.</p>"
                    + "<a href=" + activationLink + ">Click here to confirm order</a>"
                    + "<p>Best Regards,</p>"
                    + "<p>HOTEL BOOKING SYSTEM.</p>";
            message.setContent(activationHTML, "text/xml");

            Transport.send(message);

            result = true;
        } catch (MessagingException ex) {
            LOGGER.error("MessagingException: " + ex.getMessage());
        }

        return result;
    }
}
