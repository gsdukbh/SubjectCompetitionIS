package werls.scis.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * 邮件
 *
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.service
 * @Description: TODO
 * @date Date : 2020年03月05日 21:42
 */
@Service
@Async
public class EmailServiceImpl {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public void sendTextEmail(String to, String subject, String content) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(to);

        simpleMailMessage.setSubject(subject);

        simpleMailMessage.setText(content);
        try {
            mailSender.send(simpleMailMessage);
            logger.info("邮箱发送给:" + to);

        }catch (Exception e){
            logger.error("发送邮箱给:"+to+"失败");
            e.printStackTrace();

        }
    }

    public void sendHtmlEmail(String to, String subject, String content) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper;
        try {
            messageHelper = new MimeMessageHelper(message, true);

            messageHelper.setFrom(from);

            messageHelper.setTo(to);

            messageHelper.setSubject(subject);

            messageHelper.setText(content,true);

            mailSender.send(message);
            logger.info("邮箱发送给:" + to);

        } catch (MessagingException e) {
            logger.error("发送邮箱给:" + to + "失败");
            e.printStackTrace();

        }
    }

    public void sandFileEmail() {


    }
}
