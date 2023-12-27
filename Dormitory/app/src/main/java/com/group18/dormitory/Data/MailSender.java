package com.group18.dormitory.Data;

import android.os.AsyncTask;
import android.os.StrictMode;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.group18.dormitory.Model.DAOs;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {
    private final String username = "group18dorm@gmail.com";
    private final String password = "uvmz rwmc viqq rqrx";

    public void send(String receiver, String subject, String text) {
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Properties prop = new Properties();
                prop.put("mail.smtp.host", "smtp.gmail.com");
                prop.put("mail.smtp.port", "587");
                prop.put("mail.smtp.auth", "true");
                prop.put("mail.smtp.starttls.enable", "true"); //TLS

                Session session = Session.getInstance(prop,
                        new javax.mail.Authenticator() {
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(username, password);
                            }
                        });

                try {

                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress("group18dorm@gmail.com"));
                    message.setRecipients(
                            Message.RecipientType.TO,
                            InternetAddress.parse(receiver)
                    );
                    message.setSubject(subject);
                    message.setText(text);

                    Transport.send(message);

                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }


    private static MailSender instance;
    private MailSender() {
    }
    public static MailSender getInstance() {
        if(instance == null) {
            instance = new MailSender();
        }
        return instance;
    }

}
