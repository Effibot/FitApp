package logic.emailutil;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.logging.Logger;

public class EmailSender {
    private static EmailSender instance = null;
    private static final String SENDER = "fitappispw@gmail.com";
    private static final String PASSWORD ="ispw20192020";
    private static final String HOST = "mail.smtp.host";
    private static final String PORT = "mail.smtp.port";
    private static final String AUTH = "mail.smtp.auth";
    private String userNm;
    private String event;
    //Receiver Email
    private String receiver;
    //Host name
    //Message
    private String object;
    //Subject
    private String subjectTo;
    private Properties props;
    private String senderEmail;
    protected EmailSender(String sub, String msg, String emailSender, String userName, String event, String managerEmail){
        this.props = new Properties();
        props.put(HOST, "smtp.gmail.com");
        props.put(PORT, "465");
        props.put(AUTH, "true");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.ssl.checkserveridentity", true); // Compliant
        this.subjectTo = sub;
        this.object = msg;
        this.receiver = managerEmail;
        this.userNm = userName;
        this.event = event;
        this.senderEmail = emailSender;
    }

    public void sendEmails() throws MessagingException {
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(SENDER, PASSWORD);
                    }
                });
        // Used to debug SMTP issues
        session.setDebug(true);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(SENDER));

            // Set To: header field of the header.
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(receiver)
            );
            // Set Subject: header field
            message.setSubject(subjectTo);

            // Now set the actual message
            message.setText("Message from fitApp from user:"+userNm+"to reply write to:"+ senderEmail+" for event: "+event+".\nObject:\n'"+object+"'");

            // Send message
            Transport.send(message);
            
            

        } catch (MessagingException mex) {
            Logger.getLogger("Msg Exception"+mex);
        }



    }


    public static synchronized EmailSender getSingletonInstance(String subject, String msg, String receiver, String userName, String event, String managerEmail) {
        if (EmailSender.instance == null)
            EmailSender.instance = new EmailSender(subject,msg, receiver,userName,event,managerEmail);
        return instance;
    }

}
