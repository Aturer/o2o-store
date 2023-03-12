package com.xinke.edu.o2ostore.util;

import com.sun.mail.util.MailSSLSocketFactory;
import lombok.var;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Properties;

/**
 * @author Administrator
 */
public class MailHelper extends Authenticator {

    String username;
    String verifyCode;

    Session session;

    public MailHelper(
            String host,
            String username,
            String verifyCode) throws GeneralSecurityException {
        this.username = username;
        this.verifyCode = verifyCode;

        var props = new Properties();
        var sf = new MailSSLSocketFactory();
        this.session = Session.getDefaultInstance(props, this);

        sf.setTrustAllHosts(true);
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.socketFactory", sf);
    }




    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, verifyCode);
    }

    public void sendMail(String dest, String title, String content) throws MessagingException {
        var msg = new MimeMessage(session);
        //发件人
        msg.setFrom(new InternetAddress(username));
        //收件人
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(dest));
        //主题
        msg.setSubject(title, "utf-8");
        //内容
        msg.setContent(content, "text/plain;charset=utf-8");
        Transport.send(msg);
    }

}
